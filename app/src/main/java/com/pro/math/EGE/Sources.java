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
        try {
            return resources.getStringArray((int) R.array.class.getField(name.replace(" ","_")).get(null));
        } catch (Exception e) {
            throw new RuntimeException("string-array "+name+" not found");
        }
    }
    static String[] GetStringArray(@NonNull Context context,String name) {
        return GetStringArray(GetLocaleResources(context),name);
    }
    static int GetInteger(@NonNull Resources resources,String name) {
        try {
            return resources.getInteger((int) R.integer.class.getField(name.replace(" ","_")).get(null));
        } catch (Exception ignored) {
            throw new RuntimeException("integer "+name+" not found");
        }
    }
    static int GetInteger(@NonNull Context context,String name) {
        return GetInteger(GetLocaleResources(context),name);
    }
    @SuppressLint("DiscouragedApi")
    static int GetImage(String name) {
        try {
            return (int) R.drawable.class.getField(name.replace(" ","_")).get(null);
        } catch (Exception e) {
            throw new RuntimeException("drawable "+name+" not found");
        }
        //return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}

    /*static String[][] SubTopics(Resources resources) {
        return new String[][] {
                resources.getStringArray(R.array.BasicFormulas),
                resources.getStringArray(R.array.Planimetry),
                resources.getStringArray(R.array.Vectors),
                resources.getStringArray(R.array.Stereometry),
                resources.getStringArray(R.array.ProbabilityTheory),
                resources.getStringArray(R.array.Derivative),
                resources.getStringArray(R.array.Logarithms),
                resources.getStringArray(R.array.Trigonometry),
                resources.getStringArray(R.array.ComplexNumbers),
        };
    }
    static final int[] SubTopicsResources = new int[] {
                R.array.BasicFormulas,
                R.array.Planimetry,
                R.array.Vectors,
                R.array.Stereometry,
                R.array.ProbabilityTheory,
                R.array.Derivative,
                R.array.Logarithms,
                R.array.Trigonometry,
                R.array.ComplexNumbers,
    };*/

    /*public static String[] TopicsAttributes;
    public static String[][] SubTopicsNames(Context context) {
        android.content.res.Resources resources = context.getResources();
        return new String[][] {
                {resources.getString(R.string.ShortMultiplicationFormulas),resources.getString(R.string.DegreesFormulas),resources.getString(R.string.ArithmeticProgression),resources.getString(R.string.GeometricProgression)},
                {resources.getString(R.string.Triangle),resources.getString(R.string.RectangularTriangle),resources.getString(R.string.Rhomb)},
                {},
                {resources.getString(R.string.RectangularParallelepiped),resources.getString(R.string.Cube)},
                {},
                {resources.getString(R.string.DerivativesOfFunctions),resources.getString(R.string.DerivativesOfTrigonometricFunctions)},
                {resources.getString(R.string.LogarithmsExpressions),resources.getString(R.string.LogarithmsConversion)},
                {resources.getString(R.string.BasicTrigonometricFunctions),resources.getString(R.string.InverseTrigonometricFunctions),resources.getString(R.string.TabularValuesOfTrigonometricFunctions)},
                {},
        };
    }
    public static String[] RightAnswersTexts(Context context) {
        android.content.res.Resources resources = context.getResources();
        return new String[] {
                resources.getString(R.string.rightAnswer1),
                resources.getString(R.string.rightAnswer2),
                resources.getString(R.string.rightAnswer3),
                resources.getString(R.string.rightAnswer4),
        };
    }
    public static String[] RewardsTexts(Context context) {
        android.content.res.Resources resources = context.getResources();
        return new String[] {
                resources.getString(R.string.rightReward1),
                resources.getString(R.string.rightReward2),
                resources.getString(R.string.rightReward3),
                resources.getString(R.string.rightReward4),
        };
    }
    public static String[] Topics;
    public static String[][] SubTopics;
    public static int GetTopic(String Topic) {
        for (int i = 0;i < Topics.length;i++) {
            if (Objects.equals(Topics[i], Topic))
                return i;
        }
        return 0;
    }
    private static boolean Setup = false;
    public static void Setup(Context context) {
        if (Setup)
            return;
        Setup = true;
        android.content.res.Resources resources = context.getResources();
        Topics = resources.getStringArray(R.array.Topics);
        SubTopics = new String[][] {
                resources.getStringArray(R.array.BasicFormulas),
                resources.getStringArray(R.array.Planimetry),
                resources.getStringArray(R.array.Vectors),
                resources.getStringArray(R.array.Stereometry),
                resources.getStringArray(R.array.ProbabilityTheory),
                resources.getStringArray(R.array.Derivative),
                resources.getStringArray(R.array.Logarithms),
                resources.getStringArray(R.array.Trigonometry),
                resources.getStringArray(R.array.ComplexNumbers),
        };
        TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
    }
    public static int[] GetSubTopic(String SubTopic) {
        for (int i = 0;i < Topics.length;i++) {
            for (int i1 = 0;i1 < SubTopics[i].length;i1++) {
                if (Objects.equals(SubTopics[i][i1], SubTopic))
                    return new int[] {i,i1};
            }
        }
        Console.L("GetSubTopic: can't find sub topic "+SubTopic);
        return new int[] {0, 0};
    }
    public static String[] GetSubTopic(int Topic,int SubTopic) {
        return new String[] {Topics[Topic],SubTopics[Topic][SubTopic]};
    }*/