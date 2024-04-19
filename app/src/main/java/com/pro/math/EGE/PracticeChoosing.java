package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("ResourceAsColor")
public class PracticeChoosing extends MyAppCompatActivity {
    private void ChooseButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.MiniAssents)));
    }
    private void UnChooseButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.void0)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_choosing);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final Button Start = findViewById(R.id.start);
        final Button SelectAll = findViewById(R.id.select_all);
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
        GridLayout.setColumnCount((int)(size.x/TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60,getResources().getDisplayMetrics())));

        final ArrayList<Integer>[] Numbers = new ArrayList[] {new ArrayList<>()};

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
