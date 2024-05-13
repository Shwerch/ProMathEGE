package com.pro.math.EGE;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
        final TextView Version = findViewById(R.id.app_version);

        final Button Points = findViewById(R.id.points);
        final Button ResetProgress = findViewById(R.id.reset_progress);
        final Button AddPoints = findViewById(R.id.add_points);
        final Button Github = findViewById(R.id.github);
        final Button YouTuber = findViewById(R.id.youtuber);
        final Button Release = findViewById(R.id.release);

        Version.setText(Sources.GetAppVersion(this));
        ResetProgress.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));

        super.ChangePoints(Points);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu,ResetProgress,AddPoints,Points,Github,YouTuber,Release},Title);
        ResetProgress.setOnClickListener(v -> {
            Database.ResetDataBases(this);
            super.ChangePoints(Points);
        });
        AddPoints.setOnClickListener(v -> {
            Database.ChangePoints(1000L);
            super.ChangePoints(Points);
        });
        Release.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.github)+"/releases/latest"))));
        Github.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.github)))));
        YouTuber.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.trushin)))));
    }
}
