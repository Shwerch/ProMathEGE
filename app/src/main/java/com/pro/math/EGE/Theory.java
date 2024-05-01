package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import com.pro.math.EGE.Tasks.Question;

import java.util.ArrayList;
import java.util.Collections;

public class Theory {
    private static int LastTopicId = -1;
    private static int LastSubTopicId = -1;
    private static int LastTaskIndex = -1;
    @SuppressLint("DiscouragedApi")
    public static void GetTask(Context context, int topicId, Question question) {
        ArrayList<Integer> AvailableSubTopics = Database.GetAvailableSubTopics(topicId);
        final int subTopicId = AvailableSubTopics.get((int)(AvailableSubTopics.size() * Math.random()));
        int taskIndex;
        if (LastTopicId == topicId && LastSubTopicId == subTopicId) {
            taskIndex = (int) ((Database.TheoryAttributes.Get(topicId,subTopicId).tasksCount - 1) * Math.random());
            if (taskIndex >= LastTaskIndex)
                taskIndex++;
        } else
            taskIndex = (int) (Database.TheoryAttributes.Get(topicId,subTopicId).tasksCount * Math.random());



        LastTopicId = topicId;
        LastSubTopicId = subTopicId;
        LastTaskIndex = taskIndex;
        /*Console.L(topicId);
        String topic = Database.TheoryTopics.Get(topicId);
        ArrayList<Integer> AvailableSubTopics = Database.GetAvailableSubTopics(topicId);
        Resources EngRes = Sources.GetLocaleResources(context);
        if (TopicsAttributes == null)
            TopicsAttributes = EngRes.getStringArray(R.array.TopicsAttributes);
        if (SubTopics == null || LastTopic != topicId)
            SubTopics = Sources.GetStringArray(EngRes, topic);

        final int subTopicId = AvailableSubTopics.get((int)(AvailableSubTopics.size() * Math.random()));
        String subTopic = Database.TheorySubTopics.Get(topicId,subTopicId);
        String[] Tasks = Sources.GetStringArray(EngRes,topic
                +" "+subTopic);
        int taskIndex = (int)((Tasks.length - 1) * Math.random());
        if (taskIndex >= LastTaskIndex && subTopicId == LastSubTopic)
            taskIndex += 1;


        ArrayList<int[]> Answers = new ArrayList<>();
        for (int i = 0;i < Tasks.length;i++) {
            if (i == taskIndex) continue;
            for (int k = 0;k < Tasks[i].split(" = ").length;k++) {
                Answers.add(new int[] {i,k});
            }
        }
        Collections.shuffle(Answers);


        String[] Task = Tasks[taskIndex].split(" = ");
        final int rightAnswer = (int) (Task.length * Math.random());
        final int correctAnswersCount = (int)((Task.length - 1) * Math.random()) + 1;

        ArrayList<int[]> allAnswers = new ArrayList<>(Task.length - 1);
        for (int i = 0;i < Task.length;i++) {
            if (i == rightAnswer) continue;
            final String[] task = Task[i].split(" = ");
            for (int k = 0;k < task.length;k++) {
                allAnswers.add(new int[] {i,k});
            }
        }
        Collections.shuffle(allAnswers);


        ArrayList<int[]> RealAnswers = new ArrayList<>(6);
        for (int i = 0;i < correctAnswersCount;i++) {
            RealAnswers.add(Answers.get(i));
        } for (int i = correctAnswersCount;i < 6;i++) {
            RealAnswers.add(allAnswers.get(i - correctAnswersCount));
        }
        Collections.shuffle(RealAnswers);


        ArrayList<Integer> AnswersRight = new ArrayList<>();
        final String[] RealAnswersArray = new String[6];
        for (int i = 0;i < 6;i++) {
            if (Answers.contains(RealAnswers.get(i)))
                AnswersRight.add(i);
            RealAnswersArray[i] = Tasks[RealAnswers.get(i)[0]].split(" = ")[RealAnswers.get(i)[1]];
        }


        question.Change(Task[taskIndex],RealAnswersArray,AnswersRight,Sources.GetInteger(EngRes,subTopic+" reward"),correctAnswersCount);
        LastTopic = topicId;
        LastTaskIndex = taskIndex;
        LastSubTopic = subTopicId;*/
    }
}


        /*ArrayList<Integer> allAnswers = new ArrayList<>(Task.length - 1);
        for (int i = 0;i < Task.length;i++) {
            if (i != rightAnswer) {
                allAnswers.add(i);
            }
        }
        Collections.shuffle(allAnswers);
        ArrayList<int[]> Answers = new ArrayList<>();
        for (int i = 0;i < correctAnswersCount;i++) {
            Answers.add(new int[] {taskIndex,allAnswers.get(i)});
        }
        while (Answers.size() < 6) {
            ArrayList<Integer> freeIndexes = new ArrayList<>();
            for (int k = 0;k < Tasks.length;k++) {
                if (k == taskIndex)
                    continue;
                boolean free = true;
                for (int[] answer : Answers) {
                    if (answer[0] == k) {
                        free = false;
                        break;
                    }
                }
                if (free)
                    freeIndexes.add(k);
            }
            final int falseAnswerIndex = freeIndexes.get((int)(freeIndexes.size() * Math.random()));

            Answers.add(new int[] {});
        }
        Collections.shuffle(Answers);*/

        /*for (int i = correctAnswersCount;i < 6;i++) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int k = 0;k < Tasks.length;k++) {
                if (k != taskIndex && !Answers.contains(k)) {
                    arrayList.add(k);
                }
            }
            int falseTaskIndex = arrayList.get((int)(arrayList.size() * Math.random()));

        }*/

        /*ArrayList<Integer> ShuffledArrayList = GetShuffledArrayList(0,6);
        ArrayList<Integer> AnswersIndexes = new ArrayList<>(correctAnswersCount);*/
        /*for (int i = 0;i < 6;i++) {
            final int index = ShuffledArrayList.get(i);
            Answers[index] = "";
            if (i < correctAnswers.size())
                AnswersIndexes.add(index);
        }*/

/*
    public static void Setup(Context context,String Topic) {
        ArrayList<String> AvailableChapters = Database.GetAvailableSubTopics(context,Topic);
        SubTopic = AvailableChapters.get((int)(Math.random()*AvailableChapters.size()));
        String[][] formulas = Objects.requireNonNull(Formulas.get(Topic)).get(SubTopic);
        assert formulas != null;
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


import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    /static {
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
        AddFormulas(Sources.Topics[0],"ShortMultiplicationFormulas",ShortMultiplicationFormulas,true,20);
        AddFormulas(Sources.Topics[0],"DegreesFormulas",Degrees,true,20);
        AddFormulas(Sources.Topics[0],"ArithmeticProgression",ArithmeticProgression,false,40);
        AddFormulas(Sources.Topics[0],"GeometricProgression",GeometricProgression,false,40);

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
        AddFormulas(Sources.Topics[1],"Triangle",Triangle,true,40);
        AddFormulas(Sources.Topics[1],"RectangularTriangle",RectangularTriangle,false,40);
        AddFormulas(Sources.Topics[1],"Rhomb",Rhomb,false,40);

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
        AddFormulas(Sources.Topics[3],"RectangularParallelepiped",RectangularParallelepiped,true,40);
        AddFormulas(Sources.Topics[3],"Cube",Cube,false,40);

        String[][] DerivativesOfFunctions = new String[][] {
                {"f`(x)","k","tg(α)"},
                {"с`", "0"},
                {"(c * u)`", "c * u`"},
                {"(u + y)`", "u` + y`"},
                {"(u * y)`","u`y + uy`"},
                {"(u / y)`", "(u`y - uy`)/y²"},
                {"(xⁿ)`", "n * xⁿ⁻¹"},
                {"(aˣ)`", "aˣ * ln a"},
                {"(ln a)`","1/x"},
                {"(logₙa)`","1/(a * ln n)"}
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
        AddFormulas(Sources.Topics[5],"DerivativesOfFunctions",DerivativesOfFunctions,true,40);
        AddFormulas(Sources.Topics[5],"DerivativesOfTrigonometricFunctions",DerivativesOfTrigonometricFunctions,false,40);

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
        AddFormulas(Sources.Topics[6],"LogarithmsExpressions",Logarithms,true,40);
        AddFormulas(Sources.Topics[6],"LogarithmsConversion",LogarithmsConversion,false,40);

        String[][] BasicTrigonometricFunctions = new String[][] {
                {"sin²(x) + cos²(x)","1"},
                {"tg(x)","sin(x) / cos(x)","1 / ctg(x)"},
                {"ctg(x)","cos(x) / sin(x)","1 / tg(x)"},
                {"tg²(x) + 1","1 / cos²(x)"},
                {"ctg²(x) + 1","1 / sin²(x)"},
                {"sin(2x)","2sin(x)cos(x)"},
                {"cos(2x)","cos²(x) - sin²(x)","2cos²(x) - 1","1 - 2sin²(x)"},
                {"tg(2x)","2tg(x) / (1 - tg²(x))"},
                {"sin(a ± b)","sin(a)cos(b) ± cos(a)sin(b)"},
                {"cos(a ± b)","cos(a)cos(b) ∓ sin(a)sin(b)"},
                {"tg(a ± b)","(tg(a) ± tg(b)) / (1 ∓ tg(a)tg(b))"},
                {"cos²(x)","(1 + cos(2x)) / 2"},
                {"sin²(x)","(1 - cos(2x)) / 2"},
        };
        String[][] InverseTrigonometricFunctions = new String[][] {
                {"arcsin(a) = t, a ∈ [-1; 1]","sin(t) = a, t ∈ [-π/2; π/2]"},
                {"arccos(a) = t, a ∈ [-1; 1]","cos(t) = a, t ∈ [0; π]"},
                {"arctg(a) = t","tg(t) = a,t ∈ [-π/2; π/2]"},
                {"sin(x) = a","x = arcsin(a) + 2πn; π - arcsin(a) + 2πn"},
                {"cos(x) = a","x = ±arccos(a) + 2πn"},
        };
        String[][] TabularValuesOfTrigonometricFunctions = new String[][] {
                {"-1","sin(3π/2)","cos(π)"},
                {"-√3/2","sin(4π/3)","cos(5π/6)","sin(5π/3)","cos(7π/6)"},
                {"-√2/2","sin(5π/4)","cos(3π/4)","sin(7π/4)","cos(5π/4)"},
                {"-1/2","sin(7π/6)","cos(2π/3)","sin(11π/6)","cos(4π/3)"},
                {"0","sin(0)","sin(π)","cos(π/2)","cos(3π/2)"},
                {"1/2","sin(π/6)","cos(π/3)","sin(5π/6)","cos(5π/3)"},
                {"√2/2","sin(π/4)","cos(π/4)","sin(3π/4)","cos(7π/4)"},
                {"√3/2","sin(π/3)","cos(π/6)","sin(2π/3)","cos(11π/6)"},
                {"1","sin(π/2)","cos(0)"},
        };
        AddFormulas(Sources.Topics[7],"BasicTrigonometricFunctions",BasicTrigonometricFunctions,true,40);
        AddFormulas(Sources.Topics[7],"InverseTrigonometricFunctions",InverseTrigonometricFunctions,false,40);
        AddFormulas(Sources.Topics[7],"TabularValuesOfTrigonometricFunctions",TabularValuesOfTrigonometricFunctions,false,80);
    }/

    /public static String[] GetQuestionAndAnswers() {
        return QuestionAndAnswers;
    }
    public static String GetSubTopic(Context context) {
        int[] indexes = Sources.GetSubTopic(SubTopic);
        return Sources.SubTopicsNames(context)[indexes[0]][indexes[1]];
    }
    public static int[] GetCorrectAnswers() {
        return CorrectAnswers;
    }
    public static long GetReward() {
        return Reward;
    }
    public static int GetCorrectAnswersCount() {
        return CorrectAnswersCount;
    }/
}*/