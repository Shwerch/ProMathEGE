package com.pro.math.EGE;

public class TheoryTopicBasicFormulas {
    private static final String[][] AbbreviatedMultiplications = {
            {"(a + b)²", "a² + 2ab + b²"},
            {"(a - b)²", "a² - 2ab + b²"},
            {"a² - b²", "(a - b)(a + b)"},
            {"(a + b)³", "a³ + 3a²b + 3ab² + b³"},
            {"(a - b)³", "a³ - 3a²b + 3ab² - b³"},
            {"a³ + b³", "(a + b)(a² - ab + b²)"},
            {"a³ - b³", "(a - b)(a² + ab + b²)"},
    };
    private static final String[][] Degrees = {
            {"a⁰", "1"},
            {"a¹", "a"},
            {"aⁿ * aᵐ", "aⁿ⁺ᵐ"},
            {"(aⁿ)ᵐ", "aⁿᵐ"},
            {"aⁿbⁿ", "(ab)ⁿ"},
            {"a⁻ⁿ", "1/aⁿ"},
            {"aⁿ/aᵐ", "aⁿ⁻ᵐ"},
    };
    private static final String[][] ArithmeticProgressions = {
            {"aₙ", "a₁ + (n - 1)d", "aₙ₋₁ + d", "(aₙ₊₁ + aₙ₋₁)/2"},
            {"d", "aₙ - aₙ₋₁"},
            {"Sₙ", "((a₁ + aₙ) * n)/2", "n(2a₁ + (n - 1)d)/2"},
    };
    private static final String[][] GeometricProgressions = {
            {"bₙ","b₁ * qⁿ⁻¹","bₙ₋₁ * q"},
            {"q","bₙ / bₙ₋₁"},
            {"Sₙ","(b₁ - bₙ₊₁)/(1 - q)","b₁ * (1 - qⁿ)/(1 - q)"},
    };
    public static final int ChaptersCount = 4;
    public static final long Rewards = 20;
    public static String[][] GetFormulas(final int Chapter) {
        switch (Chapter) {
            case 1:
                return AbbreviatedMultiplications;
            case 2:
                return ArithmeticProgressions;
            case 3:
                return Degrees;
            default:
                return GeometricProgressions;
        }
    }
    public static String GetSubTopic(final int Chapter) {
        switch (Chapter) {
            case 1:
                return "Формулы сокращенного умножения";
            case 2:
                return "Арифметическая прогрессия";
            case 3:
                return "Формулы степеней";
            default:
                return "Геометрическая прогрессия";
        }
    }
}