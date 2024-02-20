package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;

public class Practice extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);

        final Button MainMenu = findViewById(R.id.mainmenu);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu});
    }
}
