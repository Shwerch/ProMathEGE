package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Points extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);
    }
}
