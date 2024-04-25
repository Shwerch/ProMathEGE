package com.pro.math.EGE;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends MyAppCompatActivity {
    private Button PointsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (size.x > size.y + 300) {
            setContentView(R.layout.main_menu_landscape);
        }
        else {
            setContentView(R.layout.main_menu_portrait);
        }
        final Button Points = findViewById(R.id.points);
        final Button Settings = findViewById(R.id.settings);

        final Button Theory = findViewById(R.id.theory);
        final Button Practice = findViewById(R.id.practice);
        final Button Shop = findViewById(R.id.shop);

        final Button Exit = findViewById(R.id.exit);
        final Button About = findViewById(R.id.about);

        final TextView Title = findViewById(R.id.title);

        super.SetSizes(new Button[]{Points,Settings,Theory,Practice,Shop,Exit,About},Title);

        PointsButton = Points;
        Resources.Setup(this);

        Database.DefineDataBases(this);
        startActivity(new Intent(this,Draft.class).putExtra("Text",R.string.Pi));
        Music.Play(this);
        Exit.setOnClickListener(v -> Close());
        About.setOnClickListener(v -> startActivity(new Intent(this,AboutApp.class)));
        Theory.setOnClickListener(v -> startActivity(new Intent(this, TheoryChoosing.class)));
        Settings.setOnClickListener(v -> startActivity(new Intent(this,Settings.class)));
        Points.setOnClickListener(v -> startActivity(new Intent(this,Points.class)));
        Practice.setOnClickListener(v -> startActivity(new Intent(this, PracticeChoosing.class)));
        Shop.setOnClickListener(v -> startActivity(new Intent(this,Shop.class)));
    }
    @Override
    protected void onResume() {
        super.onResume();
        super.ChangePoints(PointsButton);
    }
}