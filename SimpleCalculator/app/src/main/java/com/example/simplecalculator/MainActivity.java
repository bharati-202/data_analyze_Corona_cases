package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private String currentNumber = "";
    private String leftOperand = "";
    private String rightOperand = "";
    private char currentOperation = ' ';
    private double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> onNumberClick(button.getText().toString()));
        }

        int[] operatorButtonIds = {
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> onOperatorClick(button.getText().toString().charAt(0)));
        }

        findViewById(R.id.buttonEquals).setOnClickListener(v -> onEqualsClick());
        findViewById(R.id.buttonClear).setOnClickListener(v -> onClearClick());
        findViewById(R.id.buttonDecimal).setOnClickListener(v -> onDecimalClick());
        findViewById(R.id.buttonBackspace).setOnClickListener(v -> onBackspaceClick());
    }

    private void onNumberClick(String number) {
        currentNumber += number;
        updateDisplay(currentNumber);
    }

    private void onOperatorClick(char operator) {
        if (!currentNumber.isEmpty()) {
            if (!leftOperand.isEmpty()) {
                // If there's already a left operand, calculate the previous operation
                rightOperand = currentNumber;
                calculateResult();
                leftOperand = String.valueOf(result);
                rightOperand = "";
            } else {
                leftOperand = currentNumber;
            }
            currentNumber = "";
        }
        currentOperation = operator;
        // Display the operator or the left operand if result is already shown
        if (result != 0 && leftOperand.equals(String.valueOf(result))) {
             updateDisplay(leftOperand + " " + currentOperation);
        } else if (!leftOperand.isEmpty()){
            updateDisplay(leftOperand + " " + currentOperation);
        }
    }

    private void onEqualsClick() {
        if (leftOperand.isEmpty() || currentNumber.isEmpty() || currentOperation == ' ') {
            return; // Not enough info to calculate
        }
        rightOperand = currentNumber;
        calculateResult();
        updateDisplay(String.valueOf(result));
        leftOperand = String.valueOf(result); // Store result for further operations
        currentNumber = ""; // Reset current number
        // Keep currentOperation if user wants to continue with the result
    }

    private void calculateResult() {
        double num1 = Double.parseDouble(leftOperand);
        double num2 = Double.parseDouble(rightOperand);

        switch (currentOperation) {
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
                if (num2 == 0) {
                    updateDisplay("Error"); // Handle division by zero
                    clearCalculatorState();
                    return;
                }
                result = num1 / num2;
                break;
        }
        currentNumber = String.valueOf(result); // Prepare for display or next operation
    }

    private void onClearClick() {
        clearCalculatorState();
        updateDisplay("0");
    }

    private void clearCalculatorState() {
        currentNumber = "";
        leftOperand = "";
        rightOperand = "";
        currentOperation = ' ';
        result = 0.0;
    }


    private void onDecimalClick() {
        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0.";
            } else {
                currentNumber += ".";
            }
            updateDisplay(currentNumber);
        }
    }

    private void onBackspaceClick() {
        if (currentNumber != null && !currentNumber.isEmpty()) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            if (currentNumber.isEmpty()){
                updateDisplay("0");
            } else {
                updateDisplay(currentNumber);
            }
        } else if (!leftOperand.isEmpty() && currentOperation != ' ' && rightOperand.isEmpty() && currentNumber.isEmpty()) {
            // If we deleted the number and are back to "leftOperand operation"
            currentOperation = ' ';
            currentNumber = leftOperand; // Make left operand current again
            leftOperand = "";
            updateDisplay(currentNumber);
        }
    }

    private void updateDisplay(String text) {
        // Basic formatting for large numbers or decimals could be added here
        if (text.equals("Error")) {
            textViewResult.setText(text);
            return;
        }
        try {
            double d = Double.parseDouble(text);
            if (d == (long) d) { // if it's an integer value
                textViewResult.setText(String.format("%d", (long) d));
            } else {
                textViewResult.setText(String.format("%s", text));
            }
        } catch (NumberFormatException e) {
            // This might happen if the text is "leftOp op", e.g. "5 +"
            textViewResult.setText(text);
        }

        if (text.isEmpty()){
            textViewResult.setText("0");
        }
    }
}
