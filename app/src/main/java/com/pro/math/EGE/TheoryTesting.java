package com.pro.math.EGE;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class TheoryTesting extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        setContentView(R.layout.theory_testing);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        final Button Next = findViewById(R.id.next);

        final Button Task = findViewById(R.id.task);

        final Button Answer1 = findViewById(R.id.answer1);
        final Button Answer2 = findViewById(R.id.answer2);
        final Button Answer3 = findViewById(R.id.answer3);
        final Button Answer4 = findViewById(R.id.answer4);
        final Button Answer5 = findViewById(R.id.answer5);
        final Button Answer6 = findViewById(R.id.answer6);
        Button[] Answers = new Button[] {Answer1,Answer2,Answer3,Answer4,Answer5,Answer6};

        String[] NewTask = BasicFormulas.CreateTask();

        int Chapter;
        int RightAnswer;
        try {
            Chapter = (int) getIntent().getSerializableExtra("Chapter");
            RightAnswer = Integer.parseInt(NewTask[7]);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        Task.setText(getResources().getString(R.string.question)+" "+NewTask[0]+" ?");
        for (int i = 0;i < Answers.length;i++) {
            Answers[i].setText(NewTask[i+1]);
        }

        final double[] Reward = {1};
        final boolean[] AnswerIsGiven = {false};

        for (int i = 0;i < Answers.length;i++) {
            final int k = i+1;
            Answers[i].setOnClickListener(v -> {
                if (k == RightAnswer) {
                    Answers[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green));
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
                    Answers[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
                    if (Reward[0] != 0) {
                        Reward[0] -= 0.5d;
                    }
                }
            });
        }

        Title.setText(getResources().getStringArray(R.array.TopicsTest)[Chapter]);
        super.BackToMainMenu(MainMenu);
        Next.setOnClickListener(v -> startActivity(new Intent(this, TheoryTesting.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Chapter",Chapter)));
        super.SetSizes(new Button[]{MainMenu,Next,Task,Answer1,Answer2,Answer3,Answer4,Answer5,Answer6},Title);
    }
}
