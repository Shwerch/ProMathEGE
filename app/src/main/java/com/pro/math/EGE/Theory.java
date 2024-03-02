package com.pro.math.EGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Theory {
    private static final int LENGTH = 7;
    private static int PreviousQuestion = -1;
    private static int PreviousChapter = -1;
    private static int PreviousTopic = -1;

    public static ArrayList<Integer> AvailableChapters;
    private static long Reward;
    private static String SubTopic;

    private static String[] QuestionAndAnswers;
    private static int[] CorrectAnswers;
    private static int CorrectAnswersCount;
    public static void Setup(int Topic) {
        Reward = TheoryStorage.Rewards[Topic];
        AvailableChapters = new ArrayList<>();
        for (int i = 0; i < TheoryStorage.AllFormulas[Topic].length; i++) {
            if (TheoryStorage.FormulasAvailability[Topic][i] == 1) {
                AvailableChapters.add(i);
            }
        }
        int Chapter = AvailableChapters.get((int)(Math.random()*AvailableChapters.size()));
        SubTopic = TheoryStorage.SubTopics[Topic][Chapter];
        String[][] formulas1 = TheoryStorage.AllFormulas[Topic][Chapter];

        int RandomQuestion = (int)(Math.random()* formulas1.length);
        if (RandomQuestion == PreviousQuestion && Chapter == PreviousChapter && Topic == PreviousTopic) {
            if (RandomQuestion == 0) {
                RandomQuestion = 1;
            } else if (RandomQuestion == (formulas1.length-1)) {
                RandomQuestion -= 1;
            } else if ((int)(Math.random()*2) == 0) {
                RandomQuestion += 1;
            } else {
                RandomQuestion -= 1;
            }
        }
        PreviousQuestion = RandomQuestion;
        PreviousChapter = Chapter;
        PreviousTopic = Topic;

        QuestionAndAnswers = new String[LENGTH];

        Integer[] QuestionAndAnswersDirection = GetRandomArrayList(0, formulas1[RandomQuestion].length);
        Integer[] SelectedList = GetRandomArrayList(1,6);

        CorrectAnswersCount = 1+(int)(Math.random()*(formulas1[RandomQuestion].length-1));
        CorrectAnswers = new int[CorrectAnswersCount];

        QuestionAndAnswers[0] = formulas1[RandomQuestion][QuestionAndAnswersDirection[0]];
        for (int i = 0;i < CorrectAnswersCount;i++) {
            QuestionAndAnswers[SelectedList[i]] = formulas1[RandomQuestion][QuestionAndAnswersDirection[i+1]];
            CorrectAnswers[i] = SelectedList[i];
        }

        for (int select = 1; select < LENGTH; select++) {
            if (QuestionAndAnswers[select] != null) {
                continue;
            }
            String formula = null;
            for (int i = 0; i < formulas1.length; i++) {
                if (RandomQuestion == i) {
                    continue;
                }
                String[] formulas = formulas1[i];
                boolean Break = false;
                Integer[] randomDirection = GetRandomArrayList(0,formulas.length);
                for (int formulaDirection : randomDirection) {
                    boolean contains = false;
                    for (int selectSearch = 0; selectSearch < LENGTH; selectSearch++) {
                        if (Objects.equals(QuestionAndAnswers[selectSearch],formulas[formulaDirection])) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        formula = formulas[formulaDirection];
                        Break = true;
                        break;
                    }
                }
                if (Break) {
                    break;
                }
            }
            if (formula == null) {
                QuestionAndAnswers[select] = "";
            } else {
                QuestionAndAnswers[select] = formula;
            }
        }
    }
    public static String[] GetQuestionAndAnswers() {
        return QuestionAndAnswers;
    }
    public static String GetSubTopic() {
        return SubTopic;
    }
    public static int[] GetCorrectAnswers() {
        return CorrectAnswers;
    }
    public static long GetReward() {
        return Reward;
    }
    public static int GetCorrectAnswersCount() {
        return CorrectAnswersCount;
    }
    private static Integer[] GetRandomArrayList(int start, int length) {
        Integer[] Array = new Integer[length];
        for (int i = 0;i < length;i++) {
            Array[i] = i+start;
        }
        List<Integer> IntegerArray = Arrays.asList(Array);
        Collections.shuffle(IntegerArray);
        Collections.shuffle(IntegerArray);
        return IntegerArray.toArray(new Integer[] {});
    }
}

class TheoryStorage {
    public static final String[][][][] AllFormulas = {
            {
                    {
                            {"(a + b)²", "a² + 2ab + b²"},
                            {"(a - b)²", "a² - 2ab + b²"},
                            {"a² - b²", "(a - b)(a + b)"},
                            {"(a + b)³", "a³ + 3a²b + 3ab² + b³"},
                            {"(a - b)³", "a³ - 3a²b + 3ab² - b³"},
                            {"a³ + b³", "(a + b)(a² - ab + b²)"},
                            {"a³ - b³", "(a - b)(a² + ab + b²)"},
                    },
                    {
                            {"a⁰", "1"},
                            {"a¹", "a"},
                            {"aⁿ * aᵐ", "aⁿ⁺ᵐ"},
                            {"(aⁿ)ᵐ", "aⁿᵐ"},
                            {"aⁿbⁿ", "(ab)ⁿ"},
                            {"a⁻ⁿ", "1/aⁿ"},
                            {"aⁿ/aᵐ", "aⁿ⁻ᵐ"},
                    },
                    {
                            {"aₙ", "a₁ + (n - 1)d", "aₙ₋₁ + d", "(aₙ₊₁ + aₙ₋₁)/2"},
                            {"d", "aₙ - aₙ₋₁"},
                            {"Sₙ", "((a₁ + aₙ) * n)/2", "n(2a₁ + (n - 1)d)/2"},
                    },
                    {
                            {"bₙ", "b₁ * qⁿ⁻¹", "bₙ₋₁ * q"},
                            {"q", "bₙ / bₙ₋₁"},
                            {"Sₙ", "(b₁ - bₙ₊₁)/(1 - q)", "b₁ * (1 - qⁿ)/(1 - q)"},
                    },
            },
            {
                    {
                            {"S", "0.5*h*a", "0.5*b*c*sin(α)", "√(p(p - a)(p - b)(p - c))", "p*r"},
                            {"R", "(a*b*c)/4S"},
                            {"2R", "a/sin(a)", "b/sin(b)", "c/sin(c)"},
                            {"c²", "b² + c² - 2ab*cos(α)"},
                    },
                    {
                            {"S", "0.5*a*b", "0.5*c*h"},
                            {"c²", "b² + c²"},
                            {"r", "(a + b - c)/2"},
                            {"R", "c/2"},
                    },
                    {
                            {"S", "a*hₒ", "a² * sin a", "0.5*d1*d2",},
                            {"r", "h/2", "S/2a",},
                            {"a", "S/h", "S/2r", "P/4",},
                            {"d1", "2S/d2",},
                            {"d2", "2S/d1",},
                    },
            },
            {},
            {
                    {
                            {"V", "abc"},
                            {"S", "2(ab + bc + ca)"},
                            {"Sбок", "2c(a + b)"},
                            {"d", "√(a² + b² + c²)"},
                    },
                    {
                            {"a", "b", "c"},
                            {"V", "a³"},
                            {"S", "6a²"},
                            {"Sбок", "4a²"},
                            {"d", "√(3a²)"},
                    },
            },
            {},
            {
                    {
                            {"с`", "0"},
                            {"(u * c)`", "c * u`"},
                            {"(u + y)`", "u` + y`"},
                            {"(u/y)`", "(u`*y - u*y`)/y²"},
                            {"(xⁿ)`", "n * xⁿ⁻¹"},
                            {"(aˣ)`", "aˣ * ln a"}
                    },
                    {
                            {"(sin x)`", "cos x"},
                            {"(cos x)`", "-sin x"},
                            {"(tg x)`", "1/(cos² x)"},
                            {"(ctg x)`", "-1/(cos² x)"},
                            {"(arcsin x)`", "1/√(1-x²)"},
                            {"(arccos x)`", "-1/√(1-x²)"},
                            {"(arctg x)`", "1/(1+x²)"},
                            {"(arcctg x)`", "-1/(1+x²)"},
                    },
            },
            {
                    {
                            {"lg m","log₁₀m"},
                            {"ln m","logₑm"},
                            {"n^(logₙm)","m"},
                            {"logₙ1","0"},
                            {"logₙn","1"},
                    },
                    {
                            {"logₙ(m * k)","logₙm + logₙk"},
                            {"logₙ(m / k)","logₙm - logₙk"},
                            {"logₙmᵃ","a*logₙm"},
                            {"logₙᵃm","(1/a)*logₙm"},
                            {"logₙm","(logₖm)/(logₖn)"},
                            {"logₙm","1/(logₘn)"},
                    },
            },
    };
    public static String[][] SubTopics = new String[][] {
            {
                    "Формулы сокращенного умножения",
                    "Формулы степеней",
                    "Арифметическая прогрессия",
                    "Геометрическая прогрессия",
            },
            {
                    "Треугольник",
                    "Прямоугольный параллелепипед",
                    "Ромб",
            },
            {},
            {
                    "Прямоугольный параллелепипед",
                    "Куб",
            },
            {},
            {
                    "Производные функций",
                    "Производные тригонометрических функций",
            },
            {
                    "Логарифмы",
                    "Преобразование логарифмических выражений",
            },
    };
    public static int[][] FormulasAvailability = new int[][] {
            {1, 1, 0, 0},
            {1, 0, 0},
            {},
            {1, 0},
            {},
            {1, 0},
            {1, 0},
    };
    public static final int[][] FormulasAvailabilityDefault = new int[][] {
            {1, 1, 0, 0},
            {1, 0, 0},
            {},
            {1, 0},
            {},
            {1, 0},
            {1, 0},
    };
    public static final long[] Rewards = new long[] {
            20,
            40,
            0,
            40,
            0,
            40,
            40,
    };
}