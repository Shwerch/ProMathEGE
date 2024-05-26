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

        String AppVersion = Sources.GetAppVersion(this);

        Button AddPoints = null;
        if (AppVersion.contains("dev")) {
            setContentView(R.layout.settings_dev);
            AddPoints = findViewById(R.id.add_points);
        } else
            setContentView(R.layout.settings);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        final TextView Version = findViewById(R.id.app_version);

        final Button Points = findViewById(R.id.points);
        final Button ResetProgress = findViewById(R.id.reset_progress);
        final Button Github = findViewById(R.id.github);
        final Button YouTuber = findViewById(R.id.youtuber);
        final Button Release = findViewById(R.id.release);

        Version.setText(AppVersion);
        ResetProgress.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));

        super.ChangePoints(Points);
        super.BackToMainMenu(MainMenu);
        final Button[] Buttons;
        if (AddPoints == null)
            Buttons = new Button[] {MainMenu,ResetProgress,Points,Github,YouTuber,Release};
        else {
            Buttons = new Button[]{MainMenu, ResetProgress, AddPoints, Points, Github, YouTuber, Release};
            AddPoints.setOnClickListener(v -> {
                Database.ChangePoints(1000L);
                super.ChangePoints(Points);
            });
        }
        super.SetSizes(Buttons,Title);
        ResetProgress.setOnClickListener(v -> {
            Database.ResetDataBases(this);
            super.ChangePoints(Points);
        });
        Release.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.github)+"/releases/download/stable/ProMathEGE.apk"))));
        Github.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.github)))));
        YouTuber.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.bookege)))));
    }
}
