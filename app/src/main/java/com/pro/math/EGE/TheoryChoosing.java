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
        ListView List = findViewById(R.id.list);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);

        String[] TopicsAttributes = getResources().getStringArray(R.array.TopicsAttributes);
        String[] TopicsAttributesEnglish = Sources.GetLocaleResources(this).getStringArray(R.array.TopicsAttributes);
        String[] RealTopics = new String[TopicsAttributes.length / 2];
        int[] TopicsIds = new int[RealTopics.length];
        for (int i = 0;i < TopicsAttributes.length;i += 2) {
            RealTopics[i / 2] = TopicsAttributes[i];
            TopicsIds[i / 2] = Database.theoryTopics.Get(TopicsAttributesEnglish[i]);
        }

        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, RealTopics);
        List.setAdapter(Adapter);
        List.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(this, TheoryReading.class).putExtra("Topic",TopicsIds[position])));
    }
}

