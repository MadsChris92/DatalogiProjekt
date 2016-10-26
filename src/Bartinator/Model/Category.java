package Bartinator.Model;

import java.util.ArrayList;

/**
 * Created by Mads on 24-10-2016.
 */
public class Category {
    ArrayList<String> columns = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;


    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }


}
