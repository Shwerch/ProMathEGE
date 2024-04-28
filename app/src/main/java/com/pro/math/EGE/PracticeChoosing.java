package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("ResourceAsColor")
public class PracticeChoosing extends MyAppCompatActivity {
    private void ChooseButton(Button button) {
        button.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.Assents)));
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.TranslucentAssents)));
    }
    private void UnChooseButton(Button button) {
        button.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.TextColor)));
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.MainButtonColor)));
    }
    private void ChangeStartButton() {
        if (Numbers[0].isEmpty())
            StartButton.setAlpha(0.55f);
        else
            StartButton.setAlpha(1);
    }
    private final ArrayList<Integer>[] Numbers = new ArrayList[] {new ArrayList<>()};
    private Button StartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_choosing);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button Start = findViewById(R.id.start);
        final Button SelectAll = findViewById(R.id.select_all);
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

        StartButton = Start;
        ChangeStartButton();
        for (Button button : Tasks) {
            UnChooseButton(button);
        }
        for (int i = 0; i < Tasks.length;i++) {
            final Integer[] I = new Integer[] {i,i+1};
            Tasks[i].setText(String.valueOf(I[1]));
            Tasks[i].setOnClickListener(v -> {
                if (!Numbers[0].contains(I[1])) {
                    ChooseButton(Tasks[I[0]]);
                    Numbers[0].add(I[1]);
                } else {
                    UnChooseButton(Tasks[I[0]]);
                    Numbers[0].remove(I[1]);
                }
                if (Numbers[0].isEmpty())
                    SelectAll.setText(R.string.SelectAll);
                else
                    SelectAll.setText(R.string.DropAll);
                ChangeStartButton();
            });
        }
        Start.setOnClickListener(v -> {
            if (!Numbers[0].isEmpty()) {
                startActivity(new Intent(this,PracticeTesting.class).putExtra("Numbers", Numbers[0]));
            }
        });
        SelectAll.setOnClickListener(v -> {
            if (Numbers[0].isEmpty()) {
                Numbers[0].ensureCapacity(Tasks.length);
                for (int i = 0;i < Tasks.length;i++) {
                    Numbers[0].add(i,i + 1);
                    ChooseButton(Tasks[i]);
                }
                SelectAll.setText(R.string.DropAll);
            } else {
                Numbers[0] = new ArrayList<>();
                for (Button task : Tasks) {
                    UnChooseButton(task);
                }
                SelectAll.setText(R.string.SelectAll);
            }
            ChangeStartButton();
        });

        Button[] buttons = new Button[Tasks.length+3];
        buttons[0] = MainMenu;
        buttons[1] = Start;
        buttons[2] = SelectAll;
        System.arraycopy(Tasks, 0, buttons, 3, Tasks.length);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(buttons,Title);
    }
}
