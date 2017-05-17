package com.example.piotrek.calculator;

import java.util.ArrayList;

/**
 * Created by Piotrek on 2017-05-11.
 */

// ewentualnie można by to nazwać calculatingMechanism
public class Abacus {

    private ArrayList<String> result = new ArrayList<String>();
    private int err;
    private final int NOT_EXIST = -1;
    private final int FIRST_EXPRESSION = 0;

    public String calculate(String strLine) {
        err = 0;
        MyParser parser = new MyParser();
        result = parser.cutOnPieces(strLine);
        doMathInBrackets(FIRST_EXPRESSION);
        if (err == 0) {
            doMath(FIRST_EXPRESSION, result.size() - 1);
        }
        if (err == 0) {
            result.set(FIRST_EXPRESSION, convertResult(result.get(FIRST_EXPRESSION)));
        }
        return giveAnswer();
    }

    private void doMathInBrackets(int start) {
        boolean existingBrackets = true;

        while (existingBrackets && err == 0) {
            int closing = findFirstClosingBracket(start);
            int opening = findRelatedOpeningBracket(closing);
            if (opening >= FIRST_EXPRESSION && closing >= FIRST_EXPRESSION && err == 0) {
                int end = doMath(opening + 1, closing - 1);
                closing = end + 1;
                result.remove(closing);
                result.remove(opening);
            } else if (opening == NOT_EXIST && closing == NOT_EXIST) {
                existingBrackets = false;
            } else if (opening == NOT_EXIST || closing == NOT_EXIST) {
                //Error! Missing bracket. check syntax
                err = 4;
                break;
            }
        }
    }

    private int doMath(int start, int end) {
        end = resultOfMultipleAndDivide(start, end);
        end = resultOfAddAndSubtract(start, end);
        if (start != end && err == 0) {
            //Can't calculate! check syntax
            err = 3;
            return end;
        } else {
            return end;
        }
    }

    private int findFirstClosingBracket(int start) {
        for (int i = start; i < result.size(); i++) {
            if (result.get(i).equals(")") && err == 0) {
                return i;
            }
        }
        return NOT_EXIST;
    }

    private int findRelatedOpeningBracket(int closing) {
        if (closing == NOT_EXIST) {
            closing = result.size() - 1;
        }
        for (int i = closing; i >= FIRST_EXPRESSION; i--) {
            if (result.get(i).equals("(") && err == 0) {
                return i;
            }
        }
        return NOT_EXIST;
    }

    private int resultOfMultipleAndDivide(int start, int end) {
        for (int i = start; i <= end && start != end; i++) {
            if (result.get(i).equals("*") && err == 0) {
                try {
                    double a = Double.valueOf(result.get(i - 1));
                    double b = Double.valueOf(result.get(i + 1));
                    result.set((i + 1), String.valueOf(Multiply(a, b)));
                    result.remove(i - 1);
                    result.remove(i - 1);
                    i = i - 2;
                    end = end - 2;
                } catch (NumberFormatException e) {
                    err = 2;
                }
            } else if (result.get(i).equals("/") && err == 0) {
                try {
                    double a = Double.valueOf(result.get(i - 1));
                    double b = Double.valueOf(result.get(i + 1));
                    if (b == 0.0) {
                        //System.out.println("nie można dzielić przez zero!");
                        err = 1;
                        break;
                    }
                    result.set((i + 1), String.valueOf(Divide(a, b)));
                    result.remove(i - 1);
                    result.remove(i - 1);
                    i = i - 2;
                    end = end - 2;
                } catch (NumberFormatException e) {
                    err = 2;
                }
            }
        }
        return end;
    }

    private int resultOfAddAndSubtract(int start, int end) {
        for (int i = start; i <= end && start != end; i++) {
            if (result.get(i).equals("+") && err == 0) {
                try {
                    double a = Double.valueOf(result.get(i - 1));
                    double b = Double.valueOf(result.get(i + 1));
                    result.set((i + 1), String.valueOf(Add(a, b)));
                    result.remove(i - 1);
                    result.remove(i - 1);
                    i = i - 2;
                    end = end - 2;
                } catch (NumberFormatException e) {
                    err = 2;
                }
            } else if (result.get(i).equals("-") && err == 0) {
                try {
                    double a = Double.valueOf(result.get(i - 1));
                    double b = Double.valueOf(result.get(i + 1));
                    result.set((i + 1), String.valueOf(Subtract(a, b)));
                    result.remove(i - 1);
                    result.remove(i - 1);
                    i = i - 2;
                    end = end - 2;
                } catch (NumberFormatException e) {
                    err = 2;
                }
            } else if (err == 1) {
                break;
            }
        }
        return end;
    }

    private String giveAnswer() {
        if (err == 1) {
            result.clear();
            return "Error! Dividing by 0";
        } else if (err == 2) {
            result.clear();
            return "Syntax error!";
        } else if (err == 3) {
            result.clear();
            return "Can't calculate! check syntax";
        } else if (err == 4) {
            result.clear();
            return "Error! Missing bracket. check syntax";
        } else {
            return result.get(FIRST_EXPRESSION);
        }
    }

    private String convertResult(String toConvert) {
        toConvert = toConvert.trim();
        double tempDouble;
        String resultConverted = "";
        try {
            tempDouble = Double.valueOf(toConvert);
            if (tempDouble % 1 == 0 && !toConvert.contains("E")) {
                int tempInt = (int) tempDouble;
                resultConverted = String.valueOf(tempInt);
            } else {
                resultConverted = String.valueOf(tempDouble);
            }
        } catch (NumberFormatException e) {
            err = 2;
        }
        return resultConverted;
    }

    private double Multiply(double a, double b) {
        return (a * b);
    }

    private double Divide(double a, double b) {
        return (a / b);
    }

    private double Add(double a, double b) {
        return (a + b);
    }

    private double Subtract(double a, double b) {
        return (a - b);
    }
}
