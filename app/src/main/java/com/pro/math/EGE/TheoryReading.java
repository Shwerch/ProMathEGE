package com.pro.math.EGE;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class TheoryReading extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int Topic;
        try {
            //noinspection DataFlowIssue
            Topic = (int) getIntent().getSerializableExtra("Topic");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }

        boolean startTest = true;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (size.x > size.y + 300) {
            if (Objects.equals(getResources().getStringArray(R.array.TestAvailable)[Topic],"true")) {
                setContentView(R.layout.theory_reading_landscape);
            } else {
                setContentView(R.layout.theory_reading_landscape_without_start_test);
                startTest = false;
            }
        }
        else {
            if (Objects.equals(getResources().getStringArray(R.array.TestAvailable)[Topic],"true")) {
                setContentView(R.layout.theory_reading_portrait);
            } else {
                setContentView(R.layout.theory_reading_portrait_without_start_test);
                startTest = false;
            }
        }

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView TheoryTitle = findViewById(R.id.title);
        final TextView List = findViewById(R.id.list);

        List.setMovementMethod(new ScrollingMovementMethod());
        List.setText(getResources().getStringArray(R.array.Theory)[Topic]);

        if (startTest) {
            final Button StartTest = findViewById(R.id.start_test);
            StartTest.setOnClickListener(v -> startActivity(new Intent(this, TheoryTesting.class).putExtra("Topic", Topic)));
            super.SetSizes(new Button[]{MainMenu, StartTest}, TheoryTitle);
        } else {
            super.SetSizes(new Button[]{MainMenu}, TheoryTitle);
        }
        super.BackToMainMenu(MainMenu);

        TheoryTitle.setText(Sources.TopicsNames(this)[Topic]);

           }
}

