package com.example.piotrek.calculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Piotrek on 2017-05-11.
 */

public class OperationAdapter extends ArrayAdapter<Operation> {

    public OperationAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Operation operation = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View listResults = layoutInflater.inflate(R.layout.list_operation, null);

        TextView toCalculateTextView = (TextView) listResults.findViewById(R.id.to_calculate);
        toCalculateTextView.setText(operation.getToCalculate());

        TextView resultTextView = (TextView) listResults.findViewById(R.id.result);
        resultTextView.setText(operation.getResult());

        return listResults;
    }
}
