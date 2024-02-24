package com.pro.math.EGE;

import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class BasicFormulas {
    private static final String[] AbbreviatedMultiplicationFormulas = {
            /*0*/"(a + b)²",
            /*1*/"a² + 2ab + b²",
            /*2*/"(a - b)²",
            /*3*/"a² - 2ab + b²",
            /*4*/"a² - b²",
            /*5*/"(a - b)(a + b)",
            /*6*/"(a + b)³",
            /*7*/"a³ + 3a²b + 3ab² + b³",
            /*8*/"(a - b)³",
            /*9*/"a³ - 3a²b + 3ab² - b³",
            /*10*/"a³ + b³",
            /*11*/"(a + b)(a² - ab + b²)",
            /*12*/"a³ - b³",
            /*13*/"(a - b)(a² + ab + b²)",
    };private static final int[][] AbbreviatedMultiplicationFormulasCoincidences = {
            /*0*/{1,3},
            /*1*/{0,2},
            /*2*/{3,1},
            /*3*/{2,0},
            /*4*/{5,3},
            /*5*/{4,2},
            /*6*/{7,9},
            /*7*/{6,8},
            /*8*/{9,7},
            /*9*/{8,6},
            /*10*/{11,13},
            /*11*/{10,12},
            /*12*/{13,11},
            /*13*/{12,10},
    };
    private static final String[] DegreeFormulas = {
            /*0*/"a⁰",
            /*1*/"1",
            /*2*/"a¹",
            /*3*/"a",
            /*4*/"aⁿ * aᵐ",
            /*5*/"aⁿ⁺ᵐ",
            /*6*/"(aⁿ)ᵐ",
            /*7*/"aⁿᵐ",
            /*8*/"aⁿbⁿ",
            /*9*/"(ab)ⁿ",
            /*10*/"a⁻ⁿ",
            /*11*/"1/aⁿ",
            /*12*/"aⁿ/aᵐ",
            /*13*/"aⁿ⁻ᵐ",
    };private static final int[][] DegreeFormulasCoincidences = {
            /*0*/{1,3},
            /*1*/{0,2},
            /*2*/{3,1},
            /*3*/{2,0},
            /*4*/{5,7},
            /*5*/{4,6},
            /*6*/{7,4},
            /*7*/{6,5},
            /*8*/{9,5},
            /*9*/{8,4},
            /*10*/{11,1},
            /*11*/{10,0},
            /*12*/{13,6},
            /*13*/{12,7},
    };

    private static int PreviousQuestion = -1;
    private static int PreviousChapter = -1;

    public static String[] CreateTask() {
        final int LENGHT = 7;

        int Chapter = (int)(Math.random()*2);
        String[] Formulas;
        int[][] Coincidences;
        String[] Selected = new String[LENGHT+1];

        if (Chapter == 0) {
            Formulas = AbbreviatedMultiplicationFormulas.clone();
            Coincidences = AbbreviatedMultiplicationFormulasCoincidences;
        } else {
            Formulas = DegreeFormulas.clone();
            Coincidences = DegreeFormulasCoincidences;
        }
        
        int RandomAnswer = (int)(Math.random()*Formulas.length);
        if (RandomAnswer == PreviousQuestion && Chapter == PreviousChapter) {
            if (RandomAnswer == 0) {
                RandomAnswer = 1;
            } else if (RandomAnswer == (Formulas.length-1)) {
                RandomAnswer -= 1;
            } else if ((int)(Math.random()*2) == 0) {
                RandomAnswer += 1;
            } else {
                RandomAnswer -= 1;
            }
        }
        PreviousQuestion = RandomAnswer;
        PreviousChapter = Chapter;

        Selected[0] = Formulas[RandomAnswer];
        int RightAnswer = 1+((int)(Math.random()*(LENGHT-1)));
        int WrongAnswer = 1+((int)(Math.random()*(LENGHT-1)));
        if (WrongAnswer == RightAnswer) {
            if (RightAnswer == 0) {
                WrongAnswer = 1;
            } else if (RightAnswer == LENGHT-1) {
                WrongAnswer = LENGHT-2;
            } else if ((int)(Math.random()*2) == 0) {
                WrongAnswer += 1+(int)(Math.random()*((LENGHT-1)-WrongAnswer));
            } else {
                WrongAnswer -= 1+(int)(Math.random()*(WrongAnswer-1));
            }
        }
        Selected[RightAnswer] = Formulas[Coincidences[RandomAnswer][0]];
        Selected[WrongAnswer] = Formulas[Coincidences[RandomAnswer][1]];
        Selected[LENGHT] = String.valueOf(RightAnswer);
        Collections.shuffle(Arrays.asList(Formulas));

        for (int select = 1;select < LENGHT;select++) {
            if (Selected[select] != null) {
                continue;
            }
            String formula = null;
            for (String formulaSearch : Formulas) {
                boolean contains = false;
                for (int selectSearch = 0;selectSearch < LENGHT;selectSearch++) {
                    if (Objects.equals(Selected[selectSearch], formulaSearch)) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    formula = formulaSearch;
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
    }
}
