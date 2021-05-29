package com.surmadziecioljarosz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static com.surmadziecioljarosz.Ingredients.*;

public class Pizzeria {
    private static ArrayList<Pizza> pizzas = new ArrayList<>();
    private static Scanner orderReader = new Scanner(System.in);
    private static Double orderCost = 0.0;
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

    public static void finishOrder() {
        try {
            orderReader.nextLine();

            String decision = orderReader.nextLine();
            String decisionLowerCased = decision.toLowerCase();
            String yes = "tak";
            String no = "nie";

            if (decisionLowerCased.equals(yes)) {
                makeOrder();
                return;
            }
            if (decisionLowerCased.equals(no)) {
                System.out.println("Dziekujemy, oto paragon: " + f.format(orderCost) + " PLN");
                return;
            }


            throw new IllegalArgumentException("Decision should be equal to yes or no");
        } catch (IllegalArgumentException e) {
            System.out.println("Rozumiem, że wystarczy, oto paragon: " + f.format(orderCost) + " PLN");
        }
    }

    public static void addOrder(int pizzaNoOrdered) {
        try {
            Pizza orderedPizza = pizzas.stream().filter(p -> p.getMenuNo() == pizzaNoOrdered).findAny().orElse(null);

            if (orderedPizza == null) {
                throw new IllegalArgumentException("Pizza no out of bounds");
            }

            orderCost += orderedPizza.getPrice();
        } catch (IllegalArgumentException e) {
            System.out.println("Przykro nam, nie mamy takiej pizzy. Do widzenia!");
            System.exit(1);
        }
    }

    public static void makeOrder() {
        try {
            System.out.println("Wpisz nr pizzy jaką chcesz zamówić");

            int pizzaNoOrdered = orderReader.nextInt();
            addOrder(pizzaNoOrdered);
            System.out.println("Pizza nr. " + pizzaNoOrdered + " została dodana do zamówienia, coś jeszcze? [Tak/Nie]");

            finishOrder();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Prosiliśmy o numer :X");
        }
    }

    public static void start() {
        System.out.println("Witamy, w pizzerii JavaJava!");
        System.out.println("-----------MENU---------");

        for (Pizza p : pizzas) {
            System.out.printf(p.getMenuNo() + ". " + p.getName());
            System.out.printf("\nSkładniki: " + p.getIngredientsList() + "\n");
            System.out.printf("Cena: " + f.format(p.getPrice()) + " PLN\n");
        }

        makeOrder();
    }
}
