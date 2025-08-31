package com.lacavedeharol.calculator.view;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalculatorFrame extends JFrame {

    private final List<CalculatorButton> calculatorButtonList;
    private final CalculatorDisplayText calculatorDisplayResult;
    private final CalculatorDisplayText calculatorDisplayOperation;

    private static final String[] BUTTON_LABELS = {
        "CE", "C", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "="
    };

    public CalculatorFrame() {
        this.calculatorButtonList = new ArrayList<>();
        this.calculatorDisplayOperation = new CalculatorDisplayText("", Utilities.OPERATION_DISPLAY_HEIGHT);
        this.calculatorDisplayResult = new CalculatorDisplayText("0.0", Utilities.RESULT_DISPLAY_HEIGHT);

        setTitle("Calculator");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        add(createMainPanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel() {

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        mainPanel.setBackground(Utilities.greenWhite);
        mainPanel.setPreferredSize(Utilities.FRAME_SIZE);

        mainPanel.add(calculatorDisplayOperation);
        mainPanel.add(calculatorDisplayResult);

        for (String label : BUTTON_LABELS) {
            CalculatorButton button = new CalculatorButton(label);
            mainPanel.add(button);
            calculatorButtonList.add(button);
        }

        return mainPanel;
    }

    public List<CalculatorButton> getButtonList() {
        return calculatorButtonList;
    }

    public CalculatorDisplayText getCalculatorDisplayResult() {
        return calculatorDisplayResult;
    }

    public CalculatorDisplayText getCalculatorDisplayOperation() {
        return calculatorDisplayOperation;
    }
}
