package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TheoryReading extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_activity);
        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button StartTest = findViewById(R.id.start_test);
        final TextView TheoryTitle = findViewById(R.id.title);
        final TextView List = findViewById(R.id.list);

        int Chapter = 0;
        String ChapterName = "";
        try {
            Chapter = (int) getIntent().getSerializableExtra("Chapter");
            ChapterName =  getResources().getStringArray(R.array.Topics)[Chapter];
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(TheoryReading.this, TheoryChoosing.class));
        }
        List.setMovementMethod(new ScrollingMovementMethod());
        List.setText(getResources().getStringArray(R.array.Theory)[Chapter]);

        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu,StartTest},TheoryTitle);
        TheoryTitle.setText(ChapterName);

        switch (Chapter) {
            case 0:
                for (int i = 0;i < 5;i++) {
                    Log.d("MYLOG",String.join("\n",BasicFormulas.CreateTask()));
                }

                break;
        }
    }
}

