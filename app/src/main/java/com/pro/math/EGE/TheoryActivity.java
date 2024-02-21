package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TheoryActivity extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_activity);
        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button StartTest = findViewById(R.id.start_test);
        final TextView TheoryTitle = findViewById(R.id.title);
        final TextView List = findViewById(R.id.list);

        int Chapter = 0;
        String ChapterName = (String) getIntent().getSerializableExtra("ChapterName");
        try {
            Chapter = (int) getIntent().getSerializableExtra("Chapter");
        } catch (NullPointerException e) {
            Toast.makeText(getBaseContext(),R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(TheoryActivity.this,Theory.class));
        }
        List.setMovementMethod(new ScrollingMovementMethod());
        List.setText(getResources().getStringArray(R.array.Theory)[Chapter]);

        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu,StartTest});
        TheoryTitle.setText(ChapterName);
    }
}

