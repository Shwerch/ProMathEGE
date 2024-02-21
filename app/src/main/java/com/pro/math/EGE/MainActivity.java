package com.pro.math.EGE;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends MyAppCompatActivity {
    private void Close() {
        this.finishAffinity();
    }

    private String GetCorrectFormOfPoints(long points) {
        long remainder  = points % 10;
        String text;
        if ((points > 9 && points < 20) || remainder == 0 || remainder >= 5) {
            text = points + " " + getResources().getString(R.string.points1);
        } else if (remainder == 1) {
            text = points + " " + getResources().getString(R.string.points2);
        } else {
            text = points + " " + getResources().getString(R.string.points3);
        }
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Toast.makeText(this,size.x+"x"+size.y, Toast.LENGTH_SHORT).show();
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
        super.SetSizes(new Button[]{Points,Settings,Theory,Practice,Shop,Exit,About});

        Points.setText(GetCorrectFormOfPoints((long)(Math.random()*5000)));

        Exit.setOnClickListener(v -> Close());
        About.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,AboutApp.class)));
        Theory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Theory.class)));
        Settings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Settings.class)));
        Points.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Points.class)));
        Practice.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Practice.class)));
        Shop.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,Shop.class)));
    }
}