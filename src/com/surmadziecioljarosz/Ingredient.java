package com.surmadziecioljarosz;

public class Ingredient implements IName {
    private String name;
    /**
     * @description - price = rounded PLN price we'd pay for the 100g of ingredient in the market
     */
    private Double price;

    public Ingredient(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
