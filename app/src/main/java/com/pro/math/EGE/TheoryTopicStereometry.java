package com.pro.math.EGE;

public class TheoryTopicStereometry {
    private static final String[][] RectangularParallelepiped = {
            {"V","abc"},
            {"S","2(ab + bc + ca)"},
            {"Sбок","2c(a + b)"},
            {"d","√(a² + b² + c²)"},
    };
    private static final String[][] Cube = {
            {"a","b","c"},
            {"V","a³"},
            {"S","6a²"},
            {"Sбок","4a²"},
            {"d","√(3a²)"},
    };
    public static final int ChaptersCount = 2;
    public static final long Rewards = 40;
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return Cube;
            default:
                return RectangularParallelepiped;
        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Куб";
            default:
                return "Прямоугольный параллелепипед";
        }
    }
}
