package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Points extends MyAppCompatActivity {
    private Button PointsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        final Button Points = findViewById(R.id.points);

        super.BackToMainMenu(MainMenu);
        MainMenu.setOnClickListener(v -> finish());
        super.SetSizes(new Button[]{MainMenu,Points},Title);

        PointsButton = Points;
    }
    @Override
    protected void onResume() {
        super.onResume();
        super.ChangePoints(PointsButton);
    }
}