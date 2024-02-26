package com.pro.math.EGE;

public class TheoryTopicDerivatives {
    public static final int ChaptersCount = 2;
    public static final long Rewards = 40;
    private static final String[][] ClassicalDerivatives = {
            {"с`","0"},
            {"(u * c)`","c * u`"},
            {"(u + y)`","u` + y`"},
            {"(u/y)`","(u`*y - u*y`)/y²"},
            {"(xⁿ)`","n * xⁿ⁻¹"},
            {"(aˣ)`","aˣ * ln a"}
    };
    private static final String[][] TrigonometricDerivatives = {
            {"(sin x)`","cos x"},
            {"(cos x)`","-sin x"},
            {"(tg x)`","1/(cos² x)"},
            {"(ctg x)`","-1/(cos² x)"},
            {"(arcsin x)`","1/√(1-x²)"},
            {"(arccos x)`","-1/√(1-x²)"},
            {"(arctg x)`","1/(1+x²)"},
            {"(arcctg x)`","-1/(1+x²)"},
    };
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return TrigonometricDerivatives;
            default:
                return ClassicalDerivatives;
        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Производные тригонометрических функций";
            default:
                return "Производные функций";
        }
    }
}
