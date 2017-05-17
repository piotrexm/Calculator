package com.example.piotrek.calculator;

/**
 * Created by Piotrek on 2017-05-11.
 */

public class Operation {

    private String toCalculate;
    private String result;

    public Operation(String toCalculate, String result) {
        this.toCalculate = toCalculate;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getToCalculate() {
        return toCalculate;
    }

}
