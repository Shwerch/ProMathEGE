package com.pro.math.EGE;

public class TheoryTopicPlanimetry {
    private static final String[][] Triangle = {
            {"S","0.5*h*a","0.5*b*c*sin(α)","√(p(p - a)(p - b)(p - c))","p*r"},
            {"R","(a*b*c)/4S"},
            {"2R","a/sin(a)","b/sin(b)","c/sin(c)"},
            {"c²","b² + c² - 2ab*cos(α)"},
    };
    private static final String[][] RectangularTriangle = {
            {"S","0.5*a*b","0.5*c*h"},
            {"c²","b² + c²"},
            {"r","(a + b - c)/2"},
            {"R","c/2"},
    };
    private static final String[][] Rhombus = {
            {"S","a*hₒ","a² * sin a","0.5*d1*d2",},
            {"r","h/2","S/2a",},
            {"a","S/h","S/2r","P/4",},
            {"d1","2S/d2",},
            {"d2","2S/d1",},
    };
    public static final int ChaptersCount = 3;
    public static final long Rewards = 40;
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return RectangularTriangle;
            case 2:
                return Rhombus;
            default:
                return Triangle;

        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Прямоугольный треугольник";
            case 2:
                return "Ромб";
            default:
                return "Треугольник";
        }
    }
}
