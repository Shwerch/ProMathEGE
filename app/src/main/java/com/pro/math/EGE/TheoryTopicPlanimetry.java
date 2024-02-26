package com.pro.math.EGE;

public class TheoryTopicPlanimetry {
    private static final String[][] TriangleFormulas = {
            {"S","0.5*h*a","0.5*b*c*sin(α)","√(p(p - a)(p - b)(p - c))","p*r"},
            {"R","(a*b*c)/4S"},
            {"2R","a/sin(a)","b/sin(b)","c/sin(c)"},
            {"c²","b² + c² - 2ab*cos(α)"},
    };
    private static final String[][] RectangularTriangleFormulas = {
            {"S","0.5*a*b","0.5*c*h"},
            {"c²","b² + c²"},
            {"r","(a + b - c)/2"},
            {"R","c/2"},
    };
    public static final int ChaptersCount = 2;
    public static final long Rewards = 40;
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return RectangularTriangleFormulas;
            default:
                return TriangleFormulas;

        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Прямоугольный треугольник";
            default:
                return "Треугольник";
        }
    }
}
