package com.surmadziecioljarosz;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static javax.swing.JOptionPane.showMessageDialog;
import static com.surmadziecioljarosz.Ingredients.*;

public class Pizzeria {
    private static ArrayList<Pizza> pizzas = new ArrayList<>();
    private static DecimalFormat f = new DecimalFormat("##.00");


    private static void addBasicIngredients(Pizza pizza) {
        pizza.addIngredient(dough, 300);
        pizza.addIngredient(tomatoSauce, 100);
        pizza.addIngredient(basicCheese, 200);
    }

    public Pizzeria() {
        Pizza margherita = new Pizza("Margherita", 1);
        addBasicIngredients(margherita);
        margherita.addIngredient(oregano, 50);
        pizzas.add(margherita);

        Pizza capriciosa = new Pizza("Capriciosa", 2);
        addBasicIngredients(capriciosa);
        capriciosa.addIngredient(mushrooms, 150);
        capriciosa.addIngredient(ham, 150);
        pizzas.add(capriciosa);

        Pizza salami = new Pizza("Salami", 3);
        addBasicIngredients(salami);
        salami.addIngredient(Ingredients.salami, 200);
        salami.addIngredient(oregano, 50);
        pizzas.add(salami);

        Pizza fourCheese = new Pizza("4 sery", 4);
        addBasicIngredients(fourCheese);
        fourCheese.addIngredient(spinach, 100);
        fourCheese.addIngredient(gorgonzolaCheese, 50);
        fourCheese.addIngredient(parmesanCheese, 50);
        fourCheese.addIngredient(cheddarCheese, 50);
        fourCheese.addIngredient(mozzarellaCheese, 50);
        pizzas.add(fourCheese);

        Pizza tuna = new Pizza("Tuńczykowa", 5);
        addBasicIngredients(tuna);
        tuna.addIngredient(Ingredients.tuna, 45);
        tuna.addIngredient(anchovy, 30);
        tuna.addIngredient(mozzarellaCheese, 50);
        tuna.addIngredient(olives, 40);
        tuna.addIngredient(caper, 25);
        pizzas.add(tuna);
    }


    public static void start() {
        JFrame frame = new JFrame("Okienko pizzerii JavaJava");
        JLabel menuTitle = new JLabel("Nasze menu:");

        JPanel panel = new JPanel();
        Gui.makeColumnLayout(panel);

        panel.add(menuTitle);
        ArrayList<JTextField> orderAmountTextFields = new ArrayList<>();

        for (Pizza p : pizzas) {
            Gui.makePizzaContent(panel, p, orderAmountTextFields, f);
        }

        JButton makeOrder = new JButton("Złóż zamówienie");
        makeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ordersAmount = orderAmountTextFields.stream().mapToInt(txtField -> Integer.parseInt(txtField.getText())).sum();

                if (ordersAmount == 0) {
                    showMessageDialog(null, "Nie złożył Pan zamówienia, przykro nam :(");
                    return;
                }

                int[] pizzaAmountPerCategory = orderAmountTextFields.stream().mapToInt(txtField -> Integer.parseInt(txtField.getText())).toArray();

                double[] pricesPerCategory = IntStream.range(0, pizzas.size()).mapToDouble(i -> {
                    int index = (int) i;
                    Pizza currentPizza = pizzas.get(index);
                    double currentPizzaPrice = currentPizza.getPrice();
                    int amount = pizzaAmountPerCategory[index];

                    double price = amount * currentPizzaPrice;
                    return price;
                }).toArray();

                double price = Arrays.stream(pricesPerCategory).sum();

                String orderList = IntStream.range(0, pizzas.size()).mapToObj(i -> {
                    Pizza currentPizza = pizzas.get(i);
                    boolean hasPizzaOrdered = pizzaAmountPerCategory[i] != 0;

                    return hasPizzaOrdered ? pizzaAmountPerCategory[i] + "x " + currentPizza.getName() + " = " + pricesPerCategory[i] + "zł" : "";
                }).filter(str -> str != "").collect(Collectors.joining("\n"));


                showMessageDialog(null, "Twoje zamówienie: \n" + orderList + "\nSuma: " + price + "zł");
            }
        });

        panel.add(makeOrder);
        panel.setBorder(new EmptyBorder(10, 10, 0, 0));
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) 1E3, (int) 8E2);
        frame.setVisible(true);
    }
}
