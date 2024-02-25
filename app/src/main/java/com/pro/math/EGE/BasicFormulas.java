package com.pro.math.EGE;

public class BasicFormulas {
    private static final String[][] AbbreviatedMultiplicationFormulas = {
            {"(a + b)²", "a² + 2ab + b²"},
            {"(a - b)²", "a² - 2ab + b²"},
            {"a² - b²", "(a - b)(a + b)"},
            {"(a + b)³", "a³ + 3a²b + 3ab² + b³"},
            {"(a - b)³", "a³ - 3a²b + 3ab² - b³"},
            {"a³ + b³", "(a + b)(a² - ab + b²)"},
            {"a³ - b³", "(a - b)(a² + ab + b²)"},
    };
    private static final String[][] DegreeFormulas = {
            {"a⁰", "1"},
            {"a¹", "a"},
            {"aⁿ * aᵐ", "aⁿ⁺ᵐ"},
            {"(aⁿ)ᵐ", "aⁿᵐ"},
            {"aⁿbⁿ", "(ab)ⁿ"},
            {"a⁻ⁿ", "1/aⁿ"},
            {"aⁿ/aᵐ", "aⁿ⁻ᵐ"},
    };
    private static final String[][] ArithmeticProgressionFormulas = {
            {"aₙ", "a₁ + (n - 1)d", "aₙ₋₁ + d", "(aₙ₊₁ + aₙ₋₁)/2"},
            {"d", "aₙ - aₙ₋₁"},
            {"Sₙ", "((a₁ + aₙ) * n)/2", "n(2a₁ + (n - 1)d)/2"},
    };
    private static final String[][] GeometricProgressionFormulas = {
            {"bₙ","b₁ * qⁿ⁻¹","bₙ₋₁ * q"},
            {"q","bₙ / bₙ₋₁"},
            {"Sₙ","(b₁ - bₙ₊₁)/(1 - q)","b₁ * (1 - qⁿ)/(1 - q)"},
    };
    private static final String[] SubTopics = {
        "Формулы сокращенного умножения",
        "Арифметическая прогрессия",
        "Формулы степеней",
        "Геометрическая прогрессия",
    };
    public static final int ChaptersCount = 4;
    public static final long Rewards = 20;
    public static String[][] GetFormulas(final int Chapter) {
        if (Chapter == 0) {
            return AbbreviatedMultiplicationFormulas.clone();
        }else if (Chapter == 1) {
            return ArithmeticProgressionFormulas.clone();
        }else if (Chapter == 2) {
            return DegreeFormulas.clone();
        }else {
            return GeometricProgressionFormulas.clone();
        }
    }
    public static String GetSubTopic(final int Chapter) {
        return SubTopics[Chapter];
    }
}