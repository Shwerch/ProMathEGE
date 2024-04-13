package com.pro.math.EGE;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

public class Theory {
    private static final int LENGTH = 7;
    private static int PreviousQuestion;
    private static String PreviousSubTopic;
    private static String PreviousTopic;
    private static int Reward;
    private static String SubTopic;
    private static String[] QuestionAndAnswers;
    private static int[] CorrectAnswers;
    private static int CorrectAnswersCount;
    public static TreeMap<String,TreeMap<String,String[][]>> Formulas = new TreeMap<>();
    public static TreeMap<String,TreeMap<String,Boolean>> FormulasAvailability = new TreeMap<>();
    public static TreeMap<String,TreeMap<String,Integer>> FormulasRewards = new TreeMap<>();
    public static TreeSet<String> FormulasTopics = new TreeSet<>();
    public static TreeMap<String,TreeSet<String>> FormulasSubTopics = new TreeMap<>();
    private static void AddFormulas(String topic, String subTopic, String[][] formulas, boolean availability, int reward) {
        TreeMap<String,String[][]> treeMap = Formulas.get(topic);
        if (treeMap == null)
            treeMap = new TreeMap<>();
        treeMap.put(subTopic,formulas);
        Formulas.put(topic,treeMap);

        TreeMap<String,Boolean> treeMapAvailability = FormulasAvailability.get(topic);
        if (treeMapAvailability == null)
            treeMapAvailability = new TreeMap<>();
        treeMapAvailability.put(subTopic,availability);
        FormulasAvailability.put(topic,treeMapAvailability);

        TreeMap<String,Integer> treeMapReward = FormulasRewards.get(topic);
        if (treeMapReward == null)
            treeMapReward = new TreeMap<>();
        treeMapReward.put(subTopic,reward);
        FormulasRewards.put(topic,treeMapReward);

        FormulasTopics.add(topic);

        TreeSet<String> treeMapSubTopic = FormulasSubTopics.get(topic);
        if (treeMapSubTopic == null)
            treeMapSubTopic = new TreeSet<>();
        treeMapSubTopic.add(subTopic);
        FormulasSubTopics.put(topic,treeMapSubTopic);
    }
    static  {
        String[][] ShortMultiplicationFormulas = new String[][] {
                {"(a + b)²", "a² + 2ab + b²"},
                {"(a - b)²", "a² - 2ab + b²"},
                {"a² - b²", "(a - b)(a + b)"},
                {"(a + b)³", "a³ + 3a²b + 3ab² + b³"},
                {"(a - b)³", "a³ - 3a²b + 3ab² - b³"},
                {"a³ + b³", "(a + b)(a² - ab + b²)"},
                {"a³ - b³", "(a - b)(a² + ab + b²)"},
        };
        String[][] Degrees = new String[][] {
                {"a⁰", "1"},
                {"a¹", "a"},
                {"aⁿ * aᵐ", "aⁿ⁺ᵐ"},
                {"(aⁿ)ᵐ", "aⁿᵐ"},
                {"aⁿbⁿ", "(ab)ⁿ"},
                {"a⁻ⁿ", "1/aⁿ"},
                {"aⁿ/aᵐ", "aⁿ⁻ᵐ"},
        };
        String[][] ArithmeticProgression = new String[][] {
                {"aₙ", "a₁ + (n - 1)d", "aₙ₋₁ + d", "(aₙ₊₁ + aₙ₋₁)/2"},
                {"d", "aₙ - aₙ₋₁"},
                {"Sₙ", "((a₁ + aₙ) * n)/2", "n(2a₁ + (n - 1)d)/2"},
        };
        String[][] GeometricProgression = new String[][] {
                {"bₙ", "b₁ * qⁿ⁻¹", "bₙ₋₁ * q"},
                {"q", "bₙ / bₙ₋₁"},
                {"Sₙ", "(b₁ - bₙ₊₁)/(1 - q)", "b₁ * (1 - qⁿ)/(1 - q)"},
        };
        AddFormulas("BasicFormulas","ShortMultiplicationFormulas",ShortMultiplicationFormulas,true,20);
        AddFormulas("BasicFormulas","DegreesFormulas",Degrees,true,20);
        AddFormulas("BasicFormulas","ArithmeticProgression",ArithmeticProgression,false,40);
        AddFormulas("BasicFormulas","GeometricProgression",GeometricProgression,false,40);

        String[][] Triangle = new String[][] {
                {"S", "0.5*h*a", "0.5*b*c*sin(α)", "√(p(p - a)(p - b)(p - c))", "p*r"},
                {"R", "(a*b*c)/4S"},
                {"2R", "a/sin(a)", "b/sin(b)", "c/sin(c)"},
                {"c²", "b² + c² - 2ab*cos(α)"},
        };
        String[][] RectangularTriangle = new String[][] {
                {"S", "0.5*a*b", "0.5*c*h"},
                {"c²", "b² + c²"},
                {"r", "(a + b - c)/2"},
                {"R", "c/2"},
        };
        String[][] Rhomb = new String[][] {
                {"S", "a*hₒ", "a² * sin a", "0.5*d1*d2",},
                {"r", "h/2", "S/2a",},
                {"a", "S/h", "S/2r", "P/4",},
                {"d1", "2S/d2",},
                {"d2", "2S/d1",},
        };
        AddFormulas("Planimetry","Triangle",Triangle,true,40);
        AddFormulas("Planimetry","RectangularTriangle",RectangularTriangle,false,40);
        AddFormulas("Planimetry","Rhomb",Rhomb,false,40);

        String[][] RectangularParallelepiped = new String[][] {
                {"V", "abc"},
                {"S", "2(ab + bc + ca)"},
                {"Sбок", "2c(a + b)"},
                {"d", "√(a² + b² + c²)"},
        };
        String[][] Cube = new String[][] {
                {"a", "b", "c"},
                {"V", "a³"},
                {"S", "6a²"},
                {"Sбок", "4a²"},
                {"d", "√(3a²)"},
        };
        AddFormulas("Stereometry","RectangularParallelepiped",RectangularParallelepiped,true,40);
        AddFormulas("Stereometry","Cube",Cube,false,40);

        String[][] DerivativesOfFunctions = new String[][] {
                {"f`(x)","k","tg(α)"},
                {"с`", "0"},
                {"(c * u)`", "c * u`"},
                {"(u + y)`", "u` + y`"},
                {"(u * y)`","u`y + uy`"},
                {"(u / y)`", "(u`y - uy`)/y²"},
                {"(xⁿ)`", "n * xⁿ⁻¹"},
                {"(aˣ)`", "aˣ * ln a"},
                {"(ln a)`,1/x"},
                {"(logₙa)`,1/(a * ln n)"}
        };
        String[][] DerivativesOfTrigonometricFunctions = new String[][] {
                {"(sin x)`", "cos x"},
                {"(cos x)`", "-sin x"},
                {"(tg x)`", "1/(cos² x)"},
                {"(ctg x)`", "-1/(cos² x)"},
                {"(arcsin x)`", "1/√(1-x²)"},
                {"(arccos x)`", "-1/√(1-x²)"},
                {"(arctg x)`", "1/(1+x²)"},
                {"(arcctg x)`", "-1/(1+x²)"},
        };
        AddFormulas("Derivative","DerivativesOfFunctions",DerivativesOfFunctions,true,40);
        AddFormulas("Derivative","DerivativesOfTrigonometricFunctions",DerivativesOfTrigonometricFunctions,false,40);

        String[][] Logarithms = new String[][] {
                {"lg m","log₁₀m"},
                {"ln m","logₑm"},
                {"n^(logₙm)","m"},
                {"logₙ1","0"},
                {"logₙn","1"},
        };
        String[][] LogarithmsConversion = new String[][] {
                {"lg m","log₁₀m"},
                {"ln m","logₑm"},
                {"n^(logₙm)","m"},
                {"logₙ1","0"},
                {"logₙn","1"},
        };
        AddFormulas("Logarithms","LogarithmsExpressions",Logarithms,true,40);
        AddFormulas("Logarithms","LogarithmsConversion",LogarithmsConversion,false,40);
    }
    private static List<Integer> GetRandomArrayList(int start, int length) {
        List<Integer> IntegerList = new ArrayList<>(length);
        for (int i = 0;i < length;i++) {
            IntegerList.add(i+start);
        }
        Collections.shuffle(IntegerList);
        return IntegerList;
    }

    public static void Setup(String Topic) {
        ArrayList<String> AvailableChapters = new ArrayList<>();
        Log.d("GetFormulasAvailability",  Topic+ "\n" + Database.GetFormulasAvailability().get(Topic) + "\n" + Database.GetFormulasAvailability().entrySet());
        for (Map.Entry<String, Boolean> entry : Database.GetFormulasAvailability().get(Topic).entrySet()) {
            if (entry.getValue()) {
                AvailableChapters.add(entry.getKey());
            }
        }
        SubTopic = AvailableChapters.get((int)(Math.random()*AvailableChapters.size()));
        String[][] formulas = Formulas.get(Topic).get(SubTopic);
        int RandomQuestion = (int)(Math.random() * formulas.length);
        if (RandomQuestion == PreviousQuestion && Objects.equals(SubTopic, PreviousSubTopic) && Objects.equals(Topic, PreviousTopic)) {
            if (RandomQuestion == 0)
                RandomQuestion = 1;
            else if (RandomQuestion == (formulas.length-1))
                RandomQuestion -= 1;
            else if ((int)(Math.random()*2) == 0)
                RandomQuestion += 1;
            else
                RandomQuestion -= 1;
        }
        PreviousQuestion = RandomQuestion;
        PreviousSubTopic = SubTopic;
        PreviousTopic = Topic;

        Reward = FormulasRewards.get(Topic).get(SubTopic);

        QuestionAndAnswers = new String[LENGTH];

        List<Integer> QuestionAndAnswersDirection = GetRandomArrayList(0, formulas[RandomQuestion].length);
        List<Integer> SelectedList = GetRandomArrayList(1,6);

        CorrectAnswersCount = 1+(int)(Math.random()*(formulas[RandomQuestion].length-1));
        CorrectAnswers = new int[CorrectAnswersCount];

        QuestionAndAnswers[0] = formulas[RandomQuestion][QuestionAndAnswersDirection.get(0)];
        for (int i = 0;i < CorrectAnswersCount;i++) {
            QuestionAndAnswers[SelectedList.get(i)] = formulas[RandomQuestion][QuestionAndAnswersDirection.get(i + 1)];
            CorrectAnswers[i] = SelectedList.get(i);
        }

        for (int select = 1; select < LENGTH; select++) {
            if (QuestionAndAnswers[select] != null) {
                continue;
            }
            String formula = null;
            for (int i = 0; i < formulas.length; i++) {
                if (RandomQuestion == i) {
                    continue;
                }
                String[] formulas1 = formulas[i];
                boolean Break = false;
                Integer[] randomDirection = GetRandomArrayList(0, formulas1.length).toArray(new Integer[0]);
                for (int formulaDirection : randomDirection) {
                    boolean contains = false;
                    for (int selectSearch = 0; selectSearch < LENGTH; selectSearch++) {
                        if (Objects.equals(QuestionAndAnswers[selectSearch],formulas1[formulaDirection])) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        formula = formulas1[formulaDirection];
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
    public static String GetSubTopic(Context context) {
        Logcat.Log(SubTopic,context.getClass().getName(),"Theory");
        int[] indexes = Resources.GetSubTopic(SubTopic);
        return Resources.SubTopicsNames(context)[indexes[0]][indexes[1]];
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
}