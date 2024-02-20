package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

        int Chapter = 0;
        String ChapterName = (String) getIntent().getSerializableExtra("ChapterName");
        try {
            Chapter = (int) getIntent().getSerializableExtra("Chapter");
        } catch (NullPointerException e) {
            Toast.makeText(getBaseContext(),R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(TheoryActivity.this,Theory.class));
        }

        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu,StartTest});
        TheoryTitle.setText(ChapterName);
        /*ListView list = findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(TheoryActivity.this,Theory.class));
            }
        });*/
    }
}

