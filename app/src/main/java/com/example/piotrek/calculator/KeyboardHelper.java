package com.example.piotrek.calculator;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Piotrek on 2017-05-12.
 */

public class KeyboardHelper {

    private Abacus abacus = new Abacus();
    private String toCalculate = "";
    private ListView list;
    private TableLayout buttonsTableLayout;
    private TextView lineToCalculate;
    private OperationAdapter adapter;

    public KeyboardHelper(ListView list, TableLayout buttonsTableLayout, TextView lineToCalculate, OperationAdapter adapter) {
        this.list = list;
        this.buttonsTableLayout = buttonsTableLayout;
        this.lineToCalculate = lineToCalculate;
        this.adapter = adapter;
    }

    public void readButtons() {
        for (int y = 0; y < 4; y++) {
            TableRow tableRow = (TableRow) buttonsTableLayout.getChildAt(y);

            for (int x = 0; x < 5; x++) {
                final Button button = (Button) tableRow.getChildAt(x);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String buttonClicked = (String) button.getText();
                        if (buttonClicked.equals("=") && !toCalculate.equals("")) {
                            String result = abacus.calculate(toCalculate);
                            Operation operation = new Operation(toCalculate, result);
                            adapter.add(operation);
                            toCalculate = "";
                            lineToCalculate.setText(toCalculate);
                            chengeClearName("clear all");
                        } else if (buttonClicked.equals("backspace") && toCalculate.trim().length() != 0) {
                            toCalculate = toCalculate.trim();
                            toCalculate = toCalculate.substring(0, toCalculate.length() - 1);
                            lineToCalculate.setText(toCalculate);
                        } else if (buttonClicked.equals("backspace")) {
                            chengeClearName("clear all");
                        } else if (buttonClicked.equals("clear all")) {
                            adapter.clear();
                            toCalculate = "";
                            lineToCalculate.setText(toCalculate);
                        } else if (buttonClicked.equals("clear")) {
                            toCalculate = "";
                            lineToCalculate.setText(toCalculate);
                            chengeClearName("clear all");
                        } else if (!buttonClicked.equals("=")) {
                            toCalculate = toCalculate + buttonClicked;
                            lineToCalculate.setText(toCalculate);
                            chengeClearName("clear");
                        }
                    }
                });
            }
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chosenResult = adapter.getItem(position).getResult();
                copyNumbers(chosenResult);
            }
        });
    }

    public void copyNumbers(String numbers) {
        toCalculate = toCalculate + " " + numbers;
        toCalculate = toCalculate.trim();
        lineToCalculate.setText(toCalculate);
        chengeClearName("clear");
    }

    private void chengeClearName(String name) {
        TableRow tableRow = (TableRow) buttonsTableLayout.getChildAt(0);
        Button clear = (Button) tableRow.getChildAt(4);
        clear.setText(name);
    }
}
