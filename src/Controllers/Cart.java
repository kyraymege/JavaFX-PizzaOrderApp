package Controllers;

public class Cart {
    private String pizza_name;
    private int pizza_unit;
    private double pizza_price;

    public Cart(String pizza_name,int pizza_unit,double pizza_price){
        this.pizza_name = pizza_name;
        this.pizza_unit = pizza_unit;
        this.pizza_price = pizza_price;
    }

    public String getPizza_name() {
        return pizza_name;
    }

    public void setPizza_name(String pizza_name) {
        this.pizza_name = pizza_name;
    }

    public int getPizza_unit() {
        return pizza_unit;
    }

    public void setPizza_unit(int pizza_unit) {
        this.pizza_unit = pizza_unit;
    }

    public double getPizza_price() {
        return pizza_price;
    }

    public void setPizza_price(double pizza_price) {
        this.pizza_price = pizza_price;
    }
}
