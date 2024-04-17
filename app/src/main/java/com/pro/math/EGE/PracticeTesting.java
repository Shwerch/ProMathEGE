package com.pro.math.EGE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeTesting extends MyAppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (size.x > size.y + 300) {
            setContentView(R.layout.practice_testing_landscape);
        }
        else {
            setContentView(R.layout.practice_testing_portrait);
        }*/
        setContentView(R.layout.practice_testing_landscape);
        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button Next = findViewById(R.id.next);
        final TextView Task = findViewById(R.id.task);
        final EditText Answer = findViewById(R.id.answer);
        final Button Solution = findViewById(R.id.solution);
        final ImageView Image = findViewById(R.id.image);

        final int Number;
        try {
            //noinspection DataFlowIssue
            Number = (int) getIntent().getSerializableExtra("Number");
        } catch (NullPointerException e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        super.BackToMainMenu(MainMenu);
        Next.setOnClickListener(v -> startActivity(new Intent(this,PracticeTesting.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Number",Number)));
        final String[] RightAnswers = Resources.RightAnswersTexts(this);
        final String[] RightRewards = Resources.RewardsTexts(this);

        boolean haveImage = Practice.Setup(Number);
        int text = Practice.GetText();
        String answer = Practice.GetAnswer();
        String solution = Practice.GetSolution();
        int reward = Practice.GetReward();
        if (haveImage)
            Image.setImageResource(Practice.GetImage());
        else {
            Image.setMaxHeight(0);
        }

        final boolean[] responseReceived = new boolean[] {false};
        final Context context = this;

        Task.setText(text);
        Solution.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(solution))));
        Answer.setOnKeyListener((v, keyCode, event) -> {
            if(!responseReceived[0] && (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                responseReceived[0] = true;
                if (Answer.getText().toString().replace(" ", "").replace(".", ",").equals(answer)) {
                    Database.ChangePoints(context,reward);
                    Toast.makeText(context,RightAnswers[(int)(Math.random()*RightAnswers.length)]+
                            " "+RightRewards[(int)(Math.random()*RightAnswers.length)]+
                            " "+Resources.GetRightPointsEnd(context,reward),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,answer,Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }
}
