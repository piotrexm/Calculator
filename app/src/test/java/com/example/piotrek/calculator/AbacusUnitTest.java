package com.example.piotrek.calculator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AbacusUnitTest {
    @Test
    public void OperationSeqenceTest() throws Exception {
        String toCalculate = "(6+12*(8+4))/(3-6)";
        //String toCalculate = "(150/-3)";
        String expected = "-50";
        String result = new Abacus().calculate(toCalculate);

        assertEquals(expected, result);
    }

    @Test
    public void MinusOperatorsTest() throws Exception {
        String toCalculate = "-6*2-3*-2";
        String expected = "-6";
        String result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);

        toCalculate = "6/-2";
        expected = "-3";
        result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);

        toCalculate = "6+-2";
        expected = "4";
        result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);

        toCalculate = "6--2";
        expected = "8";
        result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);
    }

    @Test
    public void Err1_YouCantDivideByZero() throws Exception {
        String toCalculate = "6+12*12/(6-3-2-1)";
        String expected = "Error! Dividing by 0";
        String result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);
    }

    @Test
    public void Err2_SyntaxError_MultipleOperator() throws Exception {
        List<String> operators = Arrays.asList("*", "/", "+", "-");
        String expected = "Syntax error!";
        for (int i = 0; i < operators.size(); i++) {
            for (int j = 0; j < (operators.size()-1); j++) {
                String toCalculate = "6";
                toCalculate = toCalculate + operators.get(i) + operators.get(j) + "2";
                String result = new Abacus().calculate(toCalculate);
                assertEquals(expected, result);
            }
        }
    }

    @Test
    public void Err4_SyntaxError_OpeningBracket() throws Exception {
        List<String> operators = Arrays.asList("*", "/", "+", "-", "", "5");
        String expected = "Error! Missing bracket. check syntax";
        for (int i = 0; i < (operators.size()-1); i++) {
            String toCalculate = "6(";
            toCalculate = toCalculate + operators.get(i) + "2";
            String result = new Abacus().calculate(toCalculate);
            assertEquals(expected, result);
        }
    }

    @Test
    public void Err4_SyntaxError_ClosingBracket() throws Exception {
        List<String> operators = Arrays.asList("*", "/", "+", "-", "", "5");
        String expected = "Error! Missing bracket. check syntax";
        for (int i = 0; i < (operators.size()-1); i++) {
            String toCalculate = "6";
            toCalculate = toCalculate + operators.get(i) + ")2";
            String result = new Abacus().calculate(toCalculate);
            assertEquals(expected, result);
        }
    }

    @Test
    public void Err3_SyntaxError_EmptyBrackets() throws Exception {
        String expected = "Can't calculate! check syntax";
        String toCalculate = "6()2";
        String result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);
    }

    @Test
    public void Err2_SyntaxError_2dots() throws Exception {
        String expected = "Syntax error!";
        String toCalculate = "6.3+2.0.5";
        String result = new Abacus().calculate(toCalculate);
        assertEquals(expected, result);
    }
}