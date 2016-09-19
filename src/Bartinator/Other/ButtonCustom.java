package Bartinator.Other;

import javafx.scene.control.Button;

/**
 * Created by Mads on 19-09-2016.
 */
public class ButtonCustom extends Button{
    String name;
    float price;

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
