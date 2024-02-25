package com.pro.math.EGE;

public class Planimetry {
    private static final String[][] TriangleFormulas = {
            {"S","0.5*h*a","0.5*b*c*sin(α)","√(p(p - a)(p - b)(p - c))","p*r"},
            {"R","(a*b*c)/4S"},
            {"2R","a/sin(a)","b/sin(b)","c/sin(c)"},
            {"c²","b² + c² - 2ab*cos(α)"},
    };
    private static final String[] SubTopics = {
            "Треугольник",
    };
    public static final int ChaptersCount = 1;
    public static final long Rewards = 40;
    public static String[][] GetFormulas(final int Chapter) {
        return TriangleFormulas.clone();
    }
    public static String GetSubTopic(final int Chapter) {
        return SubTopics[Chapter];
    }
}
