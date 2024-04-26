package com.pro.math.EGE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PracticeTesting extends MyAppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int minSize = Math.min(size.x,size.y)*4/5;

        setContentView(R.layout.practice_testing);
        final TextView Title = findViewById(R.id.title);
        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button Next = findViewById(R.id.next);
        final TextView Task = findViewById(R.id.task);
        final EditText Answer = findViewById(R.id.answer);
        final Button Solution = findViewById(R.id.solution);
        final ImageView Image = findViewById(R.id.image);
        final Button DraftButton = findViewById(R.id.draftButton);

        final ArrayList<Integer> Numbers;
        try {
            Numbers = (ArrayList<Integer>) getIntent().getSerializableExtra("Numbers");
            if (Numbers == null)
                throw new Exception();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        super.BackToMainMenu(MainMenu);
        Next.setOnClickListener(v -> startActivity(new Intent(this,PracticeTesting.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Numbers",Numbers)));
        final String[] RightAnswers = Resources.RightAnswersTexts(this);
        final String[] RightRewards = Resources.RewardsTexts(this);

        boolean haveImage = Practice.Setup(Numbers,this);
        int text = Practice.GetText();
        String answer = Practice.GetAnswer();
        String solution = Practice.GetSolution();
        int reward = Practice.GetReward();
        if (haveImage) {
            Image.setImageResource(Practice.GetImage());
            Image.setMaxHeight(minSize);
            Image.setMaxWidth(minSize);
        }
        else {
            Image.setMaxHeight(0);
        }

        final boolean[] responseReceived = new boolean[] {false};
        final Context context = this;

        super.SetSizes(new Button[] {MainMenu,Next,Solution,DraftButton},Title);
        DraftButton.setOnClickListener(l -> startActivity(new Intent(this,Draft.class).putExtra("Text",text)));

        Task.setText(text);
        Solution.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(solution))));
        Answer.setOnKeyListener((v, keyCode, event) -> {
            if(!responseReceived[0] && (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                responseReceived[0] = true;
                String answerText = Answer.getText().toString().replace(" ", "").replace(".", ",");
                if (answerText.equals(answer) || answerText.equals("ххх")) {
                    Database.ChangePoints(context,reward);
                    Database.ChangePracticeTask(this,Practice.GetNumber(),Practice.GetId(),1);
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
