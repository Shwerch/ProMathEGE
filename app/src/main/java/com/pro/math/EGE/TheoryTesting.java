package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class TheoryTesting extends MyAppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (size.x > size.y + 300) {
            setContentView(R.layout.theory_testing_landscape);
        }
        else {
            setContentView(R.layout.theory_testing_portrait);
        }

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

        final String[] RightAnswers = Sources.RightAnswersTexts(this);
        final String[] RightRewards = Sources.RewardsTexts(this);
        
        int Topic;
        try {
            //noinspection DataFlowIssue
            Topic = (int) getIntent().getSerializableExtra("Topic");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        Theory.Setup(this,Sources.Topics[Topic]);
        final String[] QuestionAndAnswers = Theory.GetQuestionAndAnswers();
        final int[] CorrectAnswers = Theory.GetCorrectAnswers();
        final long Reward = Theory.GetReward();
        final int CorrectAnswersCount = Theory.GetCorrectAnswersCount();
        Task.setText(getResources().getString(R.string.question)+" "+QuestionAndAnswers[0]+" ?");
        TopicText.setText(Theory.GetSubTopic(this)+" ("+Reward+")");
        for (int i = 0;i < AnswersButtons.length;i++) {
            AnswersButtons[i].setText(QuestionAndAnswers[i+1]);
        }

        final float[] RewardMultiplier = {1f};
        final byte[] AnswersCountIsGiven = {0};
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
                        Database.ChangePoints(this,reward);
                        Toast.makeText(this,RightAnswers[(int)(Math.random()*RightAnswers.length)]+
                                " "+RightRewards[(int)(Math.random()*RightAnswers.length)]+
                                " "+ Sources.GetRightPointsEnd(this,reward),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,RightAnswers[(int)(Math.random()*RightAnswers.length)],Toast.LENGTH_SHORT).show();
                    }
                    AnswersCountIsGiven[0] += 1;
                } else if (AnswersCountIsGiven[0] < CorrectAnswersCount) {
                    AnswersButtons[k-1].setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
                    if (RewardMultiplier[0] != 0) {
                        RewardMultiplier[0] -= 0.5f;
                    }
                }
            });
        }
        Title.setText(Sources.TopicsAttributes[Topic * 2 + 1]);
        super.BackToMainMenu(MainMenu);
        Next.setOnClickListener(v -> startActivity(new Intent(this, TheoryTesting.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Topic",Topic)));
        super.SetSizes(new Button[]{MainMenu,Next,Answer1,Answer2,Answer3,Answer4,Answer5,Answer6},Title);
    }
}