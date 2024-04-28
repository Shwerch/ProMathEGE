package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TheoryChoosing extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_choosing);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);

        String[] RealTopics = new String[Sources.TopicsAttributes.length];
        for (int i = 0;i < Sources.TopicsAttributes.length;i++)
            RealTopics[i] = Sources.TopicsAttributes[i * 2];

        ListView List = findViewById(R.id.list);

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, RealTopics);
        List.setAdapter(Adapter);

        List.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(this, TheoryReading.class).putExtra("Topic",position)));
    }
}

