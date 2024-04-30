package com.pro.math.EGE;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);

        final Button Points = findViewById(R.id.points);
        final Button ResetProgress = findViewById(R.id.reset_progress);
        final Button AddPoints = findViewById(R.id.add_points);
        final Button Github = findViewById(R.id.github);

        ResetProgress.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        super.ChangePoints(Points);

        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu,ResetProgress,AddPoints,Points,Github},Title);
        ResetProgress.setOnClickListener(v -> {
            Database.ResetDataBases(this);
            super.ChangePoints(Points);
        });
        AddPoints.setOnClickListener(v -> {
            Database.ChangePoints(1000L);
            super.ChangePoints(Points);
        });
        Github.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.github)))));
    }
}
