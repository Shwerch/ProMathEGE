package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PracticeChoosing extends MyAppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);
        final Button MainMenu = findViewById(R.id.mainmenu);
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

        for (int i = 0; i < Tasks.length;i++) {
            Tasks[i].setText(getResources().getString(R.string.Task)+" "+(i+1));
        }

        Button[] buttons = new Button[Tasks.length+1];
        buttons[0] = MainMenu;
        System.arraycopy(Tasks, 0, buttons, 1, Tasks.length);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(buttons,Title);
    }
}
