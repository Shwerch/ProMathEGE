package com.pro.math.EGE;

import android.content.Context;

import java.util.Objects;

public class Resources {
    public static final String[] Topics = {
            "BasicFormulas",
            "Planimetry",
            "Vectors",
            "Stereometry",
            "ProbabilityTheory",
            "Derivative",
            "Logarithms",
            "ComplexNumbers",
    };
    public static final String[][] SubTopics = {
            {"ShortMultiplicationFormulas","DegreesFormulas","ArithmeticProgression","GeometricProgression"},
            {"Triangle","RectangularTriangle","Rhomb"},
            {},
            {"RectangularParallelepiped","Cube"},
            {},
            {"DerivativesOfFunctions","DerivativesOfTrigonometricFunctions"},
            {"LogarithmsExpressions","LogarithmsConversion"},
            {},
    };
    public static String[] TopicsTest(Context context) {
        android.content.res.Resources resources = context.getResources();
        return new String[] {
                resources.getString(R.string.BasicFormulasTest),
                resources.getString(R.string.PlanimetryTest),
                resources.getString(R.string.VectorsTest),
                resources.getString(R.string.StereometryTest),
                resources.getString(R.string.ProbabilityTheoryTest),
                resources.getString(R.string.DerivativeTest),
                resources.getString(R.string.LogarithmsTest),
                resources.getString(R.string.ComplexNumbersTest),
        };
    }
    public static String[] TopicsNames(Context context) {
        android.content.res.Resources resources = context.getResources();
        return new String[] {
                resources.getString(R.string.BasicFormulas),
                resources.getString(R.string.Planimetry),
                resources.getString(R.string.Vectors),
                resources.getString(R.string.Stereometry),
                resources.getString(R.string.ProbabilityTheory),
                resources.getString(R.string.Derivative),
                resources.getString(R.string.Logarithms),
                resources.getString(R.string.ComplexNumbers),
        };
    }
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
                {},
        };
    };
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
    public static int GetTopic(String Topic) {
        for (int i = 0;i < Topics.length;i++) {
            if (Objects.equals(Topics[i], Topic))
                return i;
        }
        return 0;
    }
    public static String GetTopic(int Topic) {
        return Topics[Topic];
    }
    public static int[] GetSubTopic(String SubTopic) {
        for (int i = 0;i < Topics.length;i++) {
            for (int i1 = 0;i1 < SubTopics[i].length;i1++) {
                if (Objects.equals(SubTopics[i][i1], SubTopic))
                    return new int[] {i,i1};
            }
        }
        Logcat.Log("GetSubTopic: can't find sub topic "+SubTopic,"Resources");
        return new int[] {0, 0};
    }
    public static String[] GetSubTopic(int Topic,int SubTopic) {
        return new String[] {Topics[Topic],SubTopics[Topic][SubTopic]};
    }
}
