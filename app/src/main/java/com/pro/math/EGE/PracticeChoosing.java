package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeChoosing extends MyAppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_choosing);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final GridLayout GridLayout = findViewById(R.id.gridlayout);
        final Button[] Tasks = new Button[] {
                findViewById(R.id.task_1),
                findViewById(R.id.task_2),
                findViewById(R.id.task_3),
                findViewById(R.id.task_4),
                findViewById(R.id.task_5),
                findViewById(R.id.task_6),
                findViewById(R.id.task_7),
                findViewById(R.id.task_8),
                findViewById(R.id.task_9),
                findViewById(R.id.task_10),
                findViewById(R.id.task_11),
                findViewById(R.id.task_12),
        };
        final TextView Title = findViewById(R.id.title);

        GridLayout.setColumnCount((int)(size.x/TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,65,getResources().getDisplayMetrics())));

        for (int i = 0; i < Tasks.length;i++) {
            final int I = i+1;
            Tasks[i].setText(String.valueOf(I));
            Tasks[i].setOnClickListener(v -> startActivity(new Intent(this,PracticeTesting.class).putExtra("Number",I)));
        }

        Button[] buttons = new Button[Tasks.length+1];
        buttons[0] = MainMenu;
        System.arraycopy(Tasks, 0, buttons, 1, Tasks.length);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(buttons,Title);
    }
}
