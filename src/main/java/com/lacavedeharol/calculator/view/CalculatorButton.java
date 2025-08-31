package com.lacavedeharol.calculator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

public class CalculatorButton extends JButton {

    private static final String WIDE_BUTTONS = "C0";
    private static final String SPECIAL_FOREGROUND_BUTTONS = "CE";
    private static final String EQUALS_BUTTON = "=";

    private static final Dimension WIDE_BUTTON_SIZE = new Dimension(130, 48);
    private static final Dimension NORMAL_BUTTON_SIZE = new Dimension(64, 48);

    private static final Font FONT_DEFAULT = new Font("Dialog", Font.PLAIN, 16);
    private static final Font FONT_PRESSED = new Font("Dialog", Font.PLAIN, 15);

    public CalculatorButton(String text) {
        super(text);
        configureButton();
        addChangeListener(e -> repaint());
    }

    private void configureButton() {
        setFocusable(false);
        setContentAreaFilled(false);
        setPreferredSize(WIDE_BUTTONS.contains(getText()) ? WIDE_BUTTON_SIZE : NORMAL_BUTTON_SIZE);
    }

    private Color getForegroundColor() {
        boolean isPressed = getModel().isPressed();
        if (SPECIAL_FOREGROUND_BUTTONS.contains(getText()) && !isPressed) {
            return Utilities.moldGreen;
        }
        return Utilities.darkGreen;
    }

    private Color getBackgroundColor() {
        if (getModel().isPressed()) {
            return Utilities.moldGreen;
        } else if (EQUALS_BUTTON.equals(getText())) {
            return Utilities.lightGreen;
        } else {
            return Utilities.greenWhite;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        setFont(getModel().isPressed() ? FONT_PRESSED : FONT_DEFAULT);
        setForeground(getForegroundColor());
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackgroundColor());
        g2.fill(Utilities.roundRectangle(this));
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getModel().isPressed() ? Utilities.moldGreen : Utilities.lightGreen);
        g2.draw(Utilities.roundRectangle(this));
    }
}
