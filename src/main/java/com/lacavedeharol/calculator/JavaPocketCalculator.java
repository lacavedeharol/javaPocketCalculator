package com.lacavedeharol.calculator;

import com.lacavedeharol.calculator.controller.CalculatorController;
import com.lacavedeharol.calculator.view.CalculatorFrame;
import javax.swing.SwingUtilities;

public class JavaPocketCalculator {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorController(new CalculatorFrame());
        });
    }
}
