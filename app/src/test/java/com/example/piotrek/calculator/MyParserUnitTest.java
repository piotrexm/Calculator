package com.example.piotrek.calculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyParserUnitTest {
    @Test
    public void OperationSeqenceTest() throws Exception {
        String in = "(-6+12*(8+4))/(-3-6)";
        List<String> expected = Arrays.asList("(","-6","+","12","*","(","8","+","4",")",")","/","(","-3","-","6",")");
        ArrayList<String> result = new MyParser().cutOnPieces(in);
        assertEquals(expected, result);
    }
}