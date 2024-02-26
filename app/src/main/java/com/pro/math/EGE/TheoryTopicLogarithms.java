package com.pro.math.EGE;

public class TheoryTopicLogarithms {
    public static final int ChaptersCount = 2;
    public static final long Rewards = 40;
    private static final String[][] Logarithms = {
            {"lg m","log₁₀m"},
            {"ln m","logₑm"},
            {"n^(logₙm)","m"},
            {"logₙ1","0"},
            {"logₙn","1"},
    };
    private static final String[][] ConversionFormulasLogarithms = {
            {"logₙ(m * k)","logₙm + logₙk"},
            {"logₙ(m / k)","logₙm - logₙk"},
            {"logₙmᵃ","a*logₙm"},
            {"logₙᵃm","(1/a)*logₙm"},
            {"logₙm","(logₖm)/(logₖn)"},
            {"logₙm","1/(logₘn)"},
    };
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return ConversionFormulasLogarithms;
            default:
                return Logarithms;
        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Преобразование логарифмических выражений";
            default:
                return "Логарифмы";
        }
    }
}
