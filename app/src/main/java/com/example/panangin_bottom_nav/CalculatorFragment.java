package com.example.panangin_bottom_nav;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.DecimalFormat;

public class CalculatorFragment extends Fragment {

    private EditText num1EditText, num2EditText;
    private Button addButton, subtractButton, multiplyButton, divideButton;
    private TextView resultTextView;
    private DecimalFormat decimalFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calculator_fragment, container, false);

        // Initialize views
        num1EditText = view.findViewById(R.id.num1EditText);
        num2EditText = view.findViewById(R.id.num2EditText);
        addButton = view.findViewById(R.id.addButton);
        subtractButton = view.findViewById(R.id.subtractButton);
        multiplyButton = view.findViewById(R.id.multiplyButton);
        divideButton = view.findViewById(R.id.divideButton);
        resultTextView = view.findViewById(R.id.resultTextView);

        decimalFormat = new DecimalFormat("#.##");

        // Set up the click listeners for the buttons
        addButton.setOnClickListener(v -> calculate('+'));
        subtractButton.setOnClickListener(v -> calculate('-'));
        multiplyButton.setOnClickListener(v -> calculate('*'));
        divideButton.setOnClickListener(v -> calculate('/'));

        return view;
    }

    private void calculate(char operator) {
        String num1Str = num1EditText.getText().toString();
        String num2Str = num2EditText.getText().toString();

        if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double result;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        resultTextView.setText("Error: Divide by zero");
                        return;
                    }
                    break;
                default:
                    resultTextView.setText("Invalid operation");
                    return;
            }

            resultTextView.setText("Result: " + decimalFormat.format(result));
        } else {
            resultTextView.setText("Please enter both numbers.");
        }
    }
}