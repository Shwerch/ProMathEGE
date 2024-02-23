package com.pro.math.EGE;

import android.util.Log;

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
    };private static final int[] AbbreviatedMultiplicationFormulasCoincidences = {
            1,
            0,
            3,
            2,
            5,
            4,
            7,
            6,
            9,
            8,
            11,
            10,
            13,
            12,
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
    };private static final int[] DegreeFormulasCoincidences = {
            1,
            0,
            3,
            2,
            5,
            4,
            7,
            6,
            9,
            8,
            11,
            10,
            13,
            12,
    };

    public static String[] CreateTask() {
        int Chapter = (int)(Math.random()*2);
        String[] Formulas;
        int[] Coincidences;
        String[] Selected = new String[6];

        if (Chapter == 0) {
            Formulas = AbbreviatedMultiplicationFormulas;
            Coincidences = AbbreviatedMultiplicationFormulasCoincidences;
        } else {
            Formulas = DegreeFormulas;
            Coincidences = DegreeFormulasCoincidences;
        }
        
        int RandomAnswer = (int)(Math.random()*Formulas.length);
        Selected[0] = Formulas[RandomAnswer];
        Selected[1+((int)(Math.random()*5))] = Formulas[Coincidences[RandomAnswer]];
        Collections.shuffle(Arrays.asList(Formulas));

        for (int select = 1;select < 6;select++) {
            String formula = null;
            for (String formulaSearch : Formulas) {
                boolean contains = false;
                for (String selectSearch : Selected) {
                    if (Objects.equals(selectSearch, formulaSearch)) {
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
                for (int select1 = select;select1 < 6;select1++) {
                    Selected[select1] = "";
                }
                break;
            } else {
                Selected[select] = formula;
            }
        }

        return Selected;
    }
}
