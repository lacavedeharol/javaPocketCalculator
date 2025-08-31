package com.lacavedeharol.calculator.controller;

import com.lacavedeharol.calculator.view.CalculatorDisplayText;
import com.lacavedeharol.calculator.view.CalculatorFrame;
import com.lacavedeharol.calculator.model.CalculatorModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController implements ActionListener {

    private final CalculatorDisplayText calculatorDisplayResult;
    private final CalculatorDisplayText calculatorDisplayOperation;

    private String displayResult = "0.0";
    private String displayOperation = "";

    /**
     * Model performs statically, not instantiated through the controllers
     * constructor, or anywhere.
     *
     * @param calculatorFrame
     */
    public CalculatorController(CalculatorFrame calculatorFrame) {
        this.calculatorDisplayResult = calculatorFrame.getCalculatorDisplayResult();
        this.calculatorDisplayOperation = calculatorFrame.getCalculatorDisplayOperation();
        calculatorFrame.getButtonList().forEach(button -> button.addActionListener(this));
        updateDisplay();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            appendNumber(command);
        } else if (command.matches("[/*\\-+]")) {
            handleOperator(command);
        } else {
            switch (command) {
                case "C" -> {
                    displayOperation = "";
                    displayResult = "0.0";
                }
                case "CE" -> {
                    displayResult = "0.0";
                }
                case "." -> {
                    appendDecimalPoint();
                }
                case "=" -> {
                    if (!displayOperation.isEmpty()) {
                        calculateResult();
                    }
                }
            }
        }
        updateDisplay();
    }

    private void appendNumber(String number) {
        if (displayResult.equals("0.0") || "Error".equals(displayResult)) {
            displayResult = number;
        } else {
            displayResult += number;
        }
    }

    private void appendDecimalPoint() {
        if (!displayResult.contains(".")) {
            displayResult += ".";
        }
    }

    private void handleOperator(String operator) {
        if (isLastCharacterOperator() && displayResult.isEmpty()) {
            displayOperation = displayOperation.substring(0, displayOperation.length() - 1) + operator;
        } else if (!displayResult.isEmpty() && !"Error".equals(displayResult)) {

            displayOperation += displayResult + operator;
            displayResult = "";
        }
    }

    private void calculateResult() {
        if (displayResult.isEmpty()) {
            return;
        }
        String fullExpression = displayOperation + displayResult;
        displayResult = CalculatorModel.calculate(fullExpression);
        displayOperation = "";
    }

    private boolean isLastCharacterOperator() {
        if (displayOperation.isEmpty()) {
            return false;
        }
        char lastChar = displayOperation.charAt(displayOperation.length() - 1);
        return "+-*/".indexOf(lastChar) != -1;
    }

    private void updateDisplay() {
        calculatorDisplayOperation.setText(displayOperation);
        calculatorDisplayResult.setText(displayResult);
    }
}
