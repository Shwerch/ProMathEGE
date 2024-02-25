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
            {"aₙ", "a₁ + (n - 1)d", "aₙ₋₁ + d"},
            {"d", "aₙ - aₙ₋₁"},
            {"Sₙ", "((a₁ + aₙ) * n)/2", "n(2a₁ + (n - 1)d)/2"},
    };

    public static final int ChaptersCount = 3;
    public static final long Rewards = 20;
    public static String[][] GetFormulas(final int Chapter) {
        if (Chapter == 0) {
            return AbbreviatedMultiplicationFormulas.clone();
        }else if (Chapter == 1) {
            return ArithmeticProgressionFormulas.clone();
        }else {
            return DegreeFormulas.clone();
        }
    }
    public static String[][][] GetAllFormulas() {
        return new String[][][] {AbbreviatedMultiplicationFormulas,DegreeFormulas};
    }
}
    /*public static String[] CreateTask(final int LENGHT) {

        int Chapter = (int)(Math.random()*2);
        String[][] Formulas;
        String[] Selected = new String[LENGHT+1];

        if (Chapter == 0) {
            Formulas = AbbreviatedMultiplicationFormulas.clone();
        } else {
            Formulas = DegreeFormulas.clone();
        }

        int RandomQuestion = (int)(Math.random()*Formulas.length);
        if (RandomQuestion == PreviousQuestion && Chapter == PreviousChapter) {
            if (RandomQuestion == 0) {
                RandomQuestion = 1;
            } else if (RandomQuestion == (Formulas.length-1)) {
                RandomQuestion -= 1;
            } else if ((int)(Math.random()*2) == 0) {
                RandomQuestion += 1;
            } else {
                RandomQuestion -= 1;
            }
        }
        PreviousQuestion = RandomQuestion;
        PreviousChapter = Chapter;

        int QuestionDirection = (int)(Math.random()*2);
        Selected[0] = Formulas[RandomQuestion][QuestionDirection];
        if (QuestionDirection == 0) {
            QuestionDirection = 1;
        } else {
            QuestionDirection = 0;
        }

        int RightAnswer = 1+((int)(Math.random()*(LENGHT-1)));
        Selected[RightAnswer] = Formulas[RandomQuestion][QuestionDirection];
        Selected[LENGHT] = String.valueOf(RightAnswer);
        Collections.shuffle(Arrays.asList(Formulas));

        for (int select = 1;select < LENGHT;select++) {
            if (Selected[select] != null) {
                continue;
            }
            String formula = null;
            for (String[] formulas : Formulas) {
                boolean Break = false;
                for (int formulaDirestion = 0; formulaDirestion < 2; formulaDirestion++) {
                    boolean contains = false;
                    for (int selectSearch = 0; selectSearch < LENGHT; selectSearch++) {
                        if (Objects.equals(Selected[selectSearch], formulas[formulaDirestion])) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        formula = formulas[formulaDirestion];
                        Break = true;
                        break;
                    }
                }
                if (Break) {
                    break;
                }
            }
            if (formula == null) {
                for (int select1 = select;select1 < LENGHT;select1++) {
                    Selected[select1] = "";
                }
                break;
            } else {
                Selected[select] = formula;
            }
        }

        Formulas = null;
        System.gc();

        return Selected;
    }*/
