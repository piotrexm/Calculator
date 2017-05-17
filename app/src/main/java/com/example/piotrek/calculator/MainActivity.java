package com.example.piotrek.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private TableLayout buttonsTableLayout;
    private TextView lineToCalculate;
    private OperationAdapter adapter;
    private KeyboardHelper keyboardHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        this.list = (ListView) findViewById(R.id.list);
        this.adapter = new OperationAdapter(this);
        this.list.setAdapter(this.adapter);
        this.buttonsTableLayout = (TableLayout) findViewById(R.id.buttons);
        this.lineToCalculate = (TextView) findViewById(R.id.line_to_calculate);
        this.keyboardHelper = new KeyboardHelper(list, buttonsTableLayout, lineToCalculate, adapter);

        keyboardHelper.readButtons();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<String> savedArrayList = new ArrayList<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            savedArrayList.add(adapter.getItem(i).getToCalculate());
            savedArrayList.add(adapter.getItem(i).getResult());
            outState.putStringArrayList("list", savedArrayList);
        }
        outState.putStringArrayList("list", savedArrayList);

        String toCalculate = (String) lineToCalculate.getText();
        outState.putString("toCalculate", toCalculate);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<String> savedArrayList = savedInstanceState.getStringArrayList("list");
        for (int i = 0; i < savedArrayList.size(); i = i + 2) {
            this.adapter.add(new Operation(savedArrayList.get(i), savedArrayList.get(i + 1)));
        }
        keyboardHelper.copyNumbers(savedInstanceState.getString("toCalculate"));
    }
}