/*package com.pro.math.EGE;

import android.content.Context;

import java.util.ArrayList;
import java.util.Objects;

public class ShopDataBase {
    public static ArrayList<String> ShopTopicsList = new ArrayList<>();
    public static ArrayList<String> ShopSubTopicsList = new ArrayList<>();
    public static ArrayList<String> ShopVisibleText = new ArrayList<>();
    public static void AddToShop(Context context, String topic, String subTopic) {
        for (int i = 0;i < ShopSubTopicsList.size();i++) {
            if (Objects.equals(ShopTopicsList.get(i),topic) && Objects.equals(ShopSubTopicsList.get(i),subTopic)) {
                return;
            }
        }
        ShopTopicsList.add(topic);
        ShopSubTopicsList.add(subTopic);
        ShopVisibleText.add(Sources.SubTopicsNames(context)[Sources.GetTopic(topic)][Sources.GetSubTopic(subTopic)[1]]+" - "+ Sources.GetRightPointsEnd(context,100)+" ("+ Sources.TopicsNames(context)[Sources.GetTopic(topic)]+")");
    }
    public static void RemoveFromShop(String topic,String subTopic) {
        for (int i = 0;i < ShopSubTopicsList.size();i++) {
            if (Objects.equals(ShopTopicsList.get(i), topic) && Objects.equals(ShopSubTopicsList.get(i),subTopic)) {
                ShopTopicsList.remove(i);
                ShopSubTopicsList.remove(i);
                ShopVisibleText.remove(i);
                return;
            }
        }
    }
    public static void ResetShop() {
        ShopTopicsList = new ArrayList<>();
        ShopSubTopicsList = new ArrayList<>();
        ShopVisibleText = new ArrayList<>();
    }

}*/
