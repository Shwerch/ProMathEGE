package com.pro.math.EGE;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Shop extends MyAppCompatActivity {
    private static ArrayList<String> ShopSubTopicsList = new ArrayList<>();
    private static ArrayList<Long> ShopIDsList = new ArrayList<>();
    private static ArrayAdapter<String> arrayAdapter = null;
    public static void AddToShop(String newData,long newID) {
        ShopSubTopicsList.add(newData);
        ShopIDsList.add(newID);
    }
    public static void RemoveFromShop(long newID) {
        int index = ShopIDsList.indexOf(newID);
        try {
            arrayAdapter.remove(ShopSubTopicsList.get(index));
            arrayAdapter.notifyDataSetInvalidated();
        }catch (Exception ignored) {}
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
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ShopSubTopicsList);
        List.setAdapter(arrayAdapter);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = List.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), (CharSequence)listItem, Toast.LENGTH_SHORT).show();
                Shop.super.BuySubTopic(ShopIDsList.get(position));
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