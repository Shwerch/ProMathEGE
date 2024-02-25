package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.core.content.ContextCompat;

public class TheoryTesting extends MyAppCompatActivity {
    private static int PreviousQuestion = -1;
    private static int PreviousChapter = -1;
    private static int PreviousTopic = -1;

    private Integer[] GetRandomArrayList(int start,int length) {
        Integer[] Array = new Integer[length];
        for (int i = 0;i < length;i++) {
            Array[i] = i+start;
        }
        List<Integer> IntegerArray = Arrays.asList(Array);
        Collections.shuffle(IntegerArray);
        Array = IntegerArray.toArray(new Integer[0]);
        return Array;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int LENGHT = 7;

        setContentView(R.layout.theory_testing);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        final Button Next = findViewById(R.id.next);

        final TextView Task = findViewById(R.id.task);
        final TextView TopicText = findViewById(R.id.topic);

        final Button Answer1 = findViewById(R.id.answer1);
        final Button Answer2 = findViewById(R.id.answer2);
        final Button Answer3 = findViewById(R.id.answer3);
        final Button Answer4 = findViewById(R.id.answer4);
        final Button Answer5 = findViewById(R.id.answer5);
        final Button Answer6 = findViewById(R.id.answer6);
        Button[] AnswersButtons = new Button[] {Answer1,Answer2,Answer3,Answer4,Answer5,Answer6};

        int Topic;
        try {
            Topic = (int) getIntent().getSerializableExtra("Topic");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        final int Chapter;
        final long Reward;
        String SubTopic;
        String[][] Formulas;
        switch (Topic) {
            default:
                Chapter = 1;//(int)(Math.random()*BasicFormulas.ChaptersCount);
                Reward = BasicFormulas.Rewards;
                SubTopic = BasicFormulas.GetSubTopic(Chapter);
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

        String[] QuestionAndAnswers = new String[LENGHT];

        Integer[] QuestionAndAnswersDirection = GetRandomArrayList(0,Formulas[RandomQuestion].length);
        Integer[] SelectedList = GetRandomArrayList(1,6);

        int CorrectAnswersCount = 1+(int)(Math.random()*(Formulas[RandomQuestion].length-1));
        int[] CorrectAnswers = new int[CorrectAnswersCount];

        QuestionAndAnswers[0] = Formulas[RandomQuestion][QuestionAndAnswersDirection[0]];
        for (int i = 0;i < CorrectAnswersCount;i++) {
            QuestionAndAnswers[SelectedList[i]] = Formulas[RandomQuestion][QuestionAndAnswersDirection[i+1]];
            CorrectAnswers[i] = SelectedList[i];
        }
        Collections.shuffle(Arrays.asList(Formulas));

        for (int select = 1;select < LENGHT;select++) {
            if (QuestionAndAnswers[select] != null) {
                continue;
            }
            String formula = null;
            for (String[] formulas : Formulas) {
                boolean Break = false;
                Integer[] randomDirection = GetRandomArrayList(0,formulas.length);
                for (int formulaDirection : randomDirection) {
                    boolean contains = false;
                    for (int selectSearch = 0; selectSearch < LENGHT; selectSearch++) {
                        if (Objects.equals(QuestionAndAnswers[selectSearch],formulas[formulaDirection])) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        formula = formulas[formulaDirection];
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
                    QuestionAndAnswers[select1] = "";
                }
                break;
            } else {
                QuestionAndAnswers[select] = formula;
            }
        }

        Formulas = null;
        System.gc();

        Task.setText(getResources().getString(R.string.question)+" "+QuestionAndAnswers[0]+" ?");
        TopicText.setText(SubTopic);
        for (int i = 0;i < AnswersButtons.length;i++) {
            AnswersButtons[i].setText(QuestionAndAnswers[i + 1]);
        }

        final double[] RewardMultiplier = {1};
        final int[] AnswersCountIsGiven = {0};

        for (int i = 0;i < AnswersButtons.length;i++) {
            final int k = i+1;
            AnswersButtons[i].setOnClickListener(v -> {
                boolean correct = false;
                for (int correctAnswer : CorrectAnswers) {
                    if (k == correctAnswer) {
                        correct = true;
                        break;
                    }
                }
                if (correct) {
                    AnswersButtons[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
                    if (RewardMultiplier[0] != 0) {
                        long reward = (long)(Reward*RewardMultiplier[0]/CorrectAnswersCount);
                        super.SetPoints(super.GetPoints()+reward);
                        Toast.makeText(this,getResources().getStringArray(R.array.right)[(int)(Math.random()*getResources().getStringArray(R.array.right).length)]+
                                " "+getResources().getStringArray(R.array.rightReward)[(int)(Math.random()*getResources().getStringArray(R.array.right).length)]+
                                " "+super.GetRightPointsEnd(reward),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,getResources().getStringArray(R.array.right)[(int)(Math.random()*getResources().getStringArray(R.array.right).length)],Toast.LENGTH_SHORT).show();
                    }
                    AnswersCountIsGiven[0] += 1;
                } else if (AnswersCountIsGiven[0] < CorrectAnswersCount) {
                    AnswersButtons[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
                    if (RewardMultiplier[0] != 0) {
                        RewardMultiplier[0] -= 0.5d;
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