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

import com.pro.math.EGE.Tasks.Task;

import java.util.ArrayList;

public class PracticeTesting extends MyAppCompatActivity{
    private static final Task task = new Task();
    private static ArrayList<Integer> Numbers;
    private static boolean[] responseReceived;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int minSize = Math.min(size.x,size.y) * 4 / 5;

        setContentView(R.layout.practice_testing);
        final TextView Title = findViewById(R.id.title);
        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button Next = findViewById(R.id.next);
        final TextView Task = findViewById(R.id.task);
        final EditText Answer = findViewById(R.id.answer);
        final Button Solution = findViewById(R.id.solution);
        final ImageView Image = findViewById(R.id.image);
        final Button DraftButton = findViewById(R.id.draftButton);

        try {
            final ArrayList<Integer> newNumbers = (ArrayList<Integer>) getIntent().getSerializableExtra("Numbers");
            if (newNumbers == null)
                throw new Exception();
            else if (newNumbers != Numbers) {
                Practice.GetTask(newNumbers.get((int) (newNumbers.size() * Math.random())), task);
                Numbers = newNumbers;
                responseReceived = new boolean[] {false};
            }
        } catch (Exception e) {
            Console.L(e);
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }
        super.BackToMainMenu(MainMenu);
        Next.setOnClickListener(v -> startActivity(new Intent(this, PracticeTesting.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Numbers", Numbers)));
        String[] RightAnswers = getResources().getStringArray(R.array.rightAnswers);
        String[] RightRewards = getResources().getStringArray(R.array.rightRewards);

        if (task.Image != null) {
            Image.setImageResource(task.Image);
            Image.setMaxHeight(minSize);
            Image.setMaxWidth(minSize);
        } else
            Image.setMaxHeight(0);
        final long reward = 60;
        final Context context = this;

        super.SetSizes(new Button[] {MainMenu,Next,Solution,DraftButton},Title);
        DraftButton.setOnClickListener(l ->
            startActivity(new Intent(this, Draft.class).putExtra("Text", task.Text).addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP))
        );

        Task.setText(task.Text);
        Solution.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(task.Solution))));
        Answer.setOnKeyListener((v, keyCode, event) -> {
            final boolean action = (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER);
            if (!action)
                return false;
            if(!responseReceived[0]) {
                responseReceived[0] = true;
                String answerText = Answer.getText().toString().replace(" ", "").replace(".", ",");
                if (answerText.equals(task.Answer)) {
                    Database.ChangePoints(reward);
                    Toast.makeText(context,RightAnswers[(int)(Math.random()*RightAnswers.length)]+
                            " "+RightRewards[(int)(Math.random()*RightAnswers.length)]+
                            " "+ Sources.GetRightPointsEnd(context,reward),Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context,getString(R.string.rightAnswer)+": "+task.Answer,Toast.LENGTH_SHORT).show();
                return true;
            } else
                Toast.makeText(context,R.string.answer_already_given,Toast.LENGTH_SHORT).show();
            return false;
        });
    }
}