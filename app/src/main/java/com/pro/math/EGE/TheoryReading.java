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
        setContentView(R.layout.theory_reading);
        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button StartTest = findViewById(R.id.start_test);
        final TextView TheoryTitle = findViewById(R.id.title);
        final TextView List = findViewById(R.id.list);

        int Chapter;
        try {
            Chapter = (int) getIntent().getSerializableExtra("Chapter");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }
        String ChapterName = getResources().getStringArray(R.array.Topics)[Chapter];

        List.setMovementMethod(new ScrollingMovementMethod());
        List.setText(getResources().getStringArray(R.array.Theory)[Chapter]);

        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu, StartTest}, TheoryTitle);
        TheoryTitle.setText(ChapterName);

        StartTest.setOnClickListener(v -> startActivity(new Intent(this,TheoryTesting.class).putExtra("Chapter",Chapter)));
    }
}

