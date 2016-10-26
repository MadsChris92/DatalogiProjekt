package Bartinator.Model;

import javafx.scene.control.Button;


public class ButtonCustom extends Button{
    String name;
    float price;

    public Button getB() {
        return b;
    }

    public void setB(Button b) {
        this.b = b;
    }

    Button b;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setup(){
        this.setText(name);

    }
}
