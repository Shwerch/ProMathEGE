package com.pro.math.EGE;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import androidx.core.content.ContextCompat;

public class TheoryTesting extends MyAppCompatActivity {
    private static int PreviousQuestion = -1;
    private static int PreviousChapter = -1;
    private static int PreviousTopic = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int LENGHT = 7;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        setContentView(R.layout.theory_testing);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        final Button Next = findViewById(R.id.next);

        final TextView Task = findViewById(R.id.task);

        final Button Answer1 = findViewById(R.id.answer1);
        final Button Answer2 = findViewById(R.id.answer2);
        final Button Answer3 = findViewById(R.id.answer3);
        final Button Answer4 = findViewById(R.id.answer4);
        final Button Answer5 = findViewById(R.id.answer5);
        final Button Answer6 = findViewById(R.id.answer6);
        Button[] AnswersButtons = new Button[] {Answer1,Answer2,Answer3,Answer4,Answer5,Answer6};

        int Topic;
        String[][] Formulas;
        try {
            Topic = (int) getIntent().getSerializableExtra("Topic");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        int Chapter = (int)(Math.random()*2);
        switch (Topic) {
            default:
                Formulas = BasicFormulas.GetFormulas(Chapter);
                break;
        }

        int RandomQuestion = (int)(Math.random()*Formulas.length);
        if (RandomQuestion == PreviousQuestion && Chapter == PreviousChapter && Topic == PreviousTopic) {
            if (RandomQuestion == 0) {
                RandomQuestion = 1;
            } else if (RandomQuestion == (Formulas.length-1)) {
                RandomQuestion -= 1;
            } else if ((int)(Math.random()*2) == 0) {
                RandomQuestion += 1;
            } else {
                RandomQuestion -= 1;
            }
        }
        PreviousQuestion = RandomQuestion;
        PreviousChapter = Chapter;
        PreviousTopic = Topic;

        String[] Selected = new String[LENGHT];

        int QuestionDirection = (int)(Math.random()*2);
        Selected[0] = Formulas[RandomQuestion][QuestionDirection];
        if (QuestionDirection == 0) {
            QuestionDirection = 1;
        } else {
            QuestionDirection = 0;
        }

        int RightAnswer = 1+((int)(Math.random()*(LENGHT-1)));
        Selected[RightAnswer] = Formulas[RandomQuestion][QuestionDirection];
        Collections.shuffle(Arrays.asList(Formulas));

        for (int select = 1;select < LENGHT;select++) {
            if (Selected[select] != null) {
                continue;
            }
            String formula = null;
            for (String[] formulas : Formulas) {
                boolean Break = false;
                for (int formulaDirestion = 0; formulaDirestion < 2; formulaDirestion++) {
                    boolean contains = false;
                    for (int selectSearch = 0; selectSearch < LENGHT; selectSearch++) {
                        if (Objects.equals(Selected[selectSearch], formulas[formulaDirestion])) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        formula = formulas[formulaDirestion];
                        Break = true;
                        break;
                    }
                }
                if (Break) {
                    break;
                }
            }
            if (formula == null) {
                for (int select1 = select;select1 < LENGHT;select1++) {
                    Selected[select1] = "";
                }
                break;
            } else {
                Selected[select] = formula;
            }
        }

        Formulas = null;
        System.gc();

        Task.setText(getResources().getString(R.string.question)+" "+Selected[0]+" ?");
        for (int i = 0;i < AnswersButtons.length;i++) {
            AnswersButtons[i].setText(Selected[i+1]);
        }

        final double[] Reward = {1};
        final boolean[] AnswerIsGiven = {false};

        for (int i = 0;i < AnswersButtons.length;i++) {
            final int k = i+1;
            AnswersButtons[i].setOnClickListener(v -> {
                if (k == RightAnswer) {
                    AnswersButtons[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    if (Reward[0] != 0) {
                        Toast.makeText(this,getResources().getStringArray(R.array.right)[(int)(Math.random()*getResources().getStringArray(R.array.right).length)]
                                +" "+getResources().getStringArray(R.array.rightReward)[(int)(Math.random()*getResources().getStringArray(R.array.right).length)]
                                +" "+super.GetRightPointsEnd((long)(20L*Reward[0]))+".",Toast.LENGTH_SHORT).show();
                        Reward[0] = 0;
                    } else {
                        Toast.makeText(this,getResources().getStringArray(R.array.right)[(int)(Math.random()*getResources().getStringArray(R.array.right).length)],Toast.LENGTH_SHORT).show();
                    }
                    AnswerIsGiven[0] = true;
                } else if (!AnswerIsGiven[0]) {
                    AnswersButtons[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
                    if (Reward[0] != 0) {
                        Reward[0] -= 0.5d;
                    }
                }
            });
        }

        Title.setText(getResources().getStringArray(R.array.TopicsTest)[Topic]);
        super.BackToMainMenu(MainMenu);
        Next.setOnClickListener(v -> startActivity(new Intent(this, TheoryTesting.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Topic",Topic)));
        super.SetSizes(new Button[]{MainMenu,Next,Answer1,Answer2,Answer3,Answer4,Answer5,Answer6},Title);
    }
}
