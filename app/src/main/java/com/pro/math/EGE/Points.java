package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        super.SetSizes(new Button[]{MainMenu,Points},Title);

        PointsButton = Points;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"aboba", Toast.LENGTH_LONG).show();
        long points = super.GetPoints();
        if (points == -1) {
            PointsButton.setText(getString(R.string.undefined_points));
        } else {
            PointsButton.setText(super.GetRightPointsEnd(points));
        }

    }
}
