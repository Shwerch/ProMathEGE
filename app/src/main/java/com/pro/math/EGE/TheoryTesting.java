package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TheoryTesting extends MyAppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.theory_testing);
            final Button MainMenu = findViewById(R.id.mainmenu);
            final TextView Title = findViewById(R.id.title);
            final Button Next = findViewById(R.id.next);

            final Button Task = findViewById(R.id.task);
            final Button Check = findViewById(R.id.check);


            final Button Answer1 = findViewById(R.id.answer1);
            final Button Answer2 = findViewById(R.id.answer2);
            final Button Answer3 = findViewById(R.id.answer3);
            final Button Answer4 = findViewById(R.id.answer4);
            final Button Answer5 = findViewById(R.id.answer5);

            int Chapter;
            try {
                Chapter = (int) getIntent().getSerializableExtra("Chapter");
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return;
            }

            Title.setText(getResources().getStringArray(R.array.TopicsTest)[Chapter]);
            super.BackToMainMenu(MainMenu);
            Next.setOnClickListener(v -> startActivity(new Intent(this, TheoryTesting.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Chapter",Chapter)));
            super.SetSizes(new Button[]{MainMenu,Next,Check,Task,Answer1,Answer2,Answer3,Answer4,Answer5},Title);
        }
}
