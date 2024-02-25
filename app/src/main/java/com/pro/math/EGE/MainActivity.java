package com.pro.math.EGE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends MyAppCompatActivity {
    private Button PointsButton;
    private void Close() {
        this.finishAffinity();
    }

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

        Exit.setOnClickListener(v -> Close());
        About.setOnClickListener(v -> startActivity(new Intent(this,AboutApp.class)));
        Theory.setOnClickListener(v -> startActivity(new Intent(this, TheoryChoosing.class)));
        Settings.setOnClickListener(v -> startActivity(new Intent(this,Settings.class)));
        Points.setOnClickListener(v -> startActivity(new Intent(this,Points.class)));
        Practice.setOnClickListener(v -> startActivity(new Intent(this,Practice.class)));
        Shop.setOnClickListener(v -> startActivity(new Intent(this,Shop.class)));
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("PointStorage", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY AUTOINCREMENT, points LONG)");
            Cursor query = db.rawQuery("SELECT * FROM data;",null);
            db.execSQL("INSERT OR IGNORE INTO data VALUES (1,0);");
            if (query.moveToFirst()){
                long points = query.getLong(0);
                PointsButton.setText(super.GetRightPointsEnd(points));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            Log.d("MYLOG",String.valueOf(e));
            PointsButton.setText(super.GetRightPointsEnd(-1));
        }
    }
}