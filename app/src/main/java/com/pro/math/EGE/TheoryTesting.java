package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TheoryTesting extends MyAppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.theory_testing);
            final Button MainMenu = findViewById(R.id.mainmenu);
            final TextView Title = findViewById(R.id.title);
            final Button Next = findViewById(R.id.next);
            super.BackToMainMenu(MainMenu);
            Next.setOnClickListener(v -> {
                startActivity(new Intent(this, TheoryTesting.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            });
            super.SetSizes(new Button[]{MainMenu,Next},Title);

        }
}
