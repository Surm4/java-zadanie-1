package com.surmadziecioljarosz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.font.TextAttribute;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import static javax.swing.JOptionPane.showMessageDialog;

public class Gui {
    private static int maxPizzasAmount = 10;
    private static int minPizzasAmount = 0;
    private static String defaultPizzasAmount = Integer.toString(minPizzasAmount);

    public static BoxLayout makeColumnLayout(JPanel target) {
        BoxLayout layout = new BoxLayout(target, BoxLayout.Y_AXIS);
        target.setLayout(layout);

        return layout;
    }

    public static FlowLayout makeRowLayout(JPanel target) {
        FlowLayout rowLayout = new FlowLayout(FlowLayout.LEFT);
        target.setLayout(rowLayout);

        return rowLayout;
    }

    public static JLabel makeLightLabel(String text) {
        JLabel lightLabel = new JLabel(text);

        Font font = lightLabel.getFont();
        font = font.deriveFont(
                Collections.singletonMap(
                        TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD));

        lightLabel.setFont(font);

        return lightLabel;
    }

    public static void makePizzaContent(JPanel target, Pizza p, ArrayList<JTextField> orderAmountTextFields, DecimalFormat f) {
        JPanel pizzaPanel = new JPanel();
        Gui.makeColumnLayout(pizzaPanel);

        JLabel pizza = Gui.makeLightLabel(p.getMenuNo() + ". \"" + p.getName() + "\", " + f.format(p.getPrice()) + "zł");
        JLabel ingredients = Gui.makeLightLabel("Składniki: " + p.getIngredientsList());

        JPanel actionsPanel = new JPanel();
        Gui.makeRowLayout(actionsPanel);

        JLabel amountLabel = new JLabel("Ilość:");

        JTextField amount = new JTextField(defaultPizzasAmount, 2);
        orderAmountTextFields.add(amount);
        amount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String currentValue = amount.getText();
                try {
                    int value = Integer.parseInt(String.valueOf(currentValue));

                    if (value < minPizzasAmount || value > maxPizzasAmount) throw new IllegalArgumentException("Illegal pizzas amount");
                } catch (NumberFormatException err) {
                    amount.setText(defaultPizzasAmount);
                    showMessageDialog(null, "Podano ilość pizz jest nieodpowiednia");
                } catch (IllegalArgumentException err) {
                    amount.setText(defaultPizzasAmount);
                    showMessageDialog(null, "Nie można zamówić < 1 pizz");
                }
            }
        });

        JButton decrementButton = new JButton("-");
        decrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentValue = amount.getText();
                int newValue = Integer.parseInt(currentValue) - 1;
                if (newValue < 0) {
                    showMessageDialog(null, "Nie można zamówić < 0 pizz");
                    return;
                }
                amount.setText(Integer.toString(newValue));
            }
        });

        JButton incrementButton = new JButton("+");
        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentValue = amount.getText();
                int newValue = Integer.parseInt(currentValue) + 1;
                if (newValue > maxPizzasAmount) {
                    showMessageDialog(null, "Nie można zamówić > 10 pizz");
                    return;
                }
                amount.setText(Integer.toString(newValue));
            }
        });

        actionsPanel.add(amountLabel);
        actionsPanel.add(amount);
        actionsPanel.add(decrementButton);
        actionsPanel.add(incrementButton);

        pizzaPanel.add(pizza);
        pizzaPanel.add(ingredients);
        pizzaPanel.add(actionsPanel);

        target.add(pizzaPanel);
    }
}
