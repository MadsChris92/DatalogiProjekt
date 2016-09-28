package Bartinator.Model;

import javafx.scene.control.Button;


public class Product {
    private String name, category;
    private float price;
    private Button b;

    public Button getB() {
        return b;
    }

    public void setB(Button b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



}
