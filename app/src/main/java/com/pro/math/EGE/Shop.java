package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Shop extends MyAppCompatActivity {
    private static ArrayList<String> ShopSubTopicsList = new ArrayList<>();
    private static ArrayList<Long> ShopIDsList = new ArrayList<>();
    private void BuyEnded() {
        startActivity(new Intent(this,Shop.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    public static void AddToShop(String newData,long newID) {
        ShopSubTopicsList.add(newData);
        ShopIDsList.add(newID);
    }
    public static void RemoveFromShop(long newID) {
        int index = ShopIDsList.indexOf(newID);
        ShopSubTopicsList.remove(index);
        ShopIDsList.remove(index);
    }
    public static void ResetShop() {
        ShopSubTopicsList = new ArrayList<>();
        ShopIDsList = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);

        ListView List = findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ShopSubTopicsList);
        List.setAdapter(arrayAdapter);
        List.setOnItemClickListener((parent, view, position, id) -> {
            Object listItem = List.getItemAtPosition(position);
            if (Shop.super.BuySubTopic(ShopIDsList.get(position))) {
                Toast.makeText(getBaseContext(),"Успешно куплено "+(listItem),Toast.LENGTH_SHORT).show();
                BuyEnded();
            } else {
                Toast.makeText(getBaseContext(),"Недостаточно баллов для покупки "+(listItem),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

/*class dataListAdapter extends BaseAdapter {
    String[] SubTopicsName;
    long[] SubTopicsID;
    @Override
    public int getCount() {
        return SubTopicsName.length;
    }

    @Override
    public Object getItem(int position) {
        return SubTopicsName[position];
    }

    @Override
    public long getItemId(int position) {
        return SubTopicsID[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}*/