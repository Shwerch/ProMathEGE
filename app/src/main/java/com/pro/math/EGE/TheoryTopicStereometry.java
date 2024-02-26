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
    private static final String[][] Rhombus = {
            {"S","a*hₒ","a² * sin a","0.5*d1*d2",},
            {"r","h/2","S/2a",},
            {"a","S/h","S/2r","P/4",},
            {"d1","2S/d2",},
            {"d2","2S/d1",},
    };
    public static final int ChaptersCount = 2;
    public static final long Rewards = 40;
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return Cube;
            case 2:
                return Rhombus;
            default:
                return RectangularParallelepiped;
        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Куб";
            case 2:
                return "Ромб";
            default:
                return "Прямоугольный параллелепипед";
        }
    }
}
