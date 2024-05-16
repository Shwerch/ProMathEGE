package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Sources {
    static String GetRightPointsEnd(Context context,long points) {
        final long remainder  = points % 10;
        final int index;
        if ((points > 9 && points < 20) || remainder == 0 || remainder >= 5)
            index = 0;
        else if (remainder == 1)
            index = 1;
        else
            index = 2;
        return points + " " + context.getResources().getStringArray(R.array.points)[index];
    }
    static Resources GetLocaleResources(@NonNull Context context) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(Locale.US);
        return context.createConfigurationContext(configuration).getResources();
    }
    static String[] GetStringArray(@NonNull Resources resources,String name) {
        name = name.replace(" ","_");
        try {
            return resources.getStringArray((int) R.array.class.getField(name).get(null));
        } catch (Exception e) {
            throw new RuntimeException("string-array "+name+" not found");
        }
    }
    static String[] GetStringArray(@NonNull Context context,String name) {
        return GetStringArray(GetLocaleResources(context),name);
    }
    static int GetInteger(@NonNull Resources resources,String name) {
        name = name.replace(" ","_");
        try {
            return resources.getInteger((int) R.integer.class.getField(name).get(null));
        } catch (Exception ignored) {
            throw new RuntimeException("integer "+name+" not found");
        }
    }
    static int GetInteger(@NonNull Context context,String name) {
        return GetInteger(GetLocaleResources(context),name);
    }
    @SuppressLint("DiscouragedApi")
    static int GetImage(String name) {
        name = name.replace(" ","_");
        try {
            return (int) R.drawable.class.getField(name).get(null);
        } catch (Exception e) {
            throw new RuntimeException("drawable "+name+" not found");
        }
    }
    static String GetAppVersion(Context context) {
        Resources resources = context.getResources();
        try {
            return resources.getString(R.string.version)+" "+
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName+" "+
                    resources.getString(R.string.by)+" "+
                    resources.getString(R.string.build);
        } catch (Exception e) {
            Console.L(e);
            return "";
        }
    }
}