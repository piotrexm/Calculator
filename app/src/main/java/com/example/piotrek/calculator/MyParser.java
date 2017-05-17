package com.example.piotrek.calculator;

import java.util.ArrayList;

/**
 * Created by Piotrek on 2017-05-11.
 */

public class MyParser {

    private ArrayList<String> result;

    public ArrayList<String> cutOnPieces(String strLine) {
        strLine = strLine.trim();
        strLine = strLine.replace(",", ".");
        strLine = strLine.replace("(", " ( ");
        strLine = strLine.replace(")", " ) ");
        strLine = strLine.replace("+", " + ");
        strLine = strLine.replace("-", " - ");
        strLine = strLine.replace("x", "*");
        strLine = strLine.replace("*", " * ");
        strLine = strLine.replace("/", " / ");
        strLine = strLine.trim();

        result = new ArrayList<String>();

        String[] parts = strLine.split(" ");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
            if (parts[i].equals("") || parts[i].equals(null)) {
                //skip empty or null
            } else {
                result.add(parts[i].trim());
            }
        }
        for (int i = 0; i < result.size() - 2; i++) {
            if ((result.get(i).equals("*") || result.get(i).equals("/") || result.get(i).equals("+") || result.get(i).equals("-") || result.get(i).equals("("))
                    && result.get(i + 1).equals("-")) {
                result.set(i + 2, "-" + result.get(i + 2));
                result.remove(i + 1);
                i--;
            }
        }
        modifyMinusAtTheBegining(result);
        return result;
    }

    public void modifyMinusAtTheBegining(ArrayList<String> result) {
        if (result.get(0).equals("-")) {
            result.set(1, "-" + result.get(1));
            result.remove(0);
        }
    }
}
