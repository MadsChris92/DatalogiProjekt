package Bartinator.Controller;

import Bartinator.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;


public class EditProductController {
    Category activeCategory;
    public TableView productTable;
    final ObservableList<Product> data = FXCollections.observableArrayList();
    ArrayList<TableColumn> columns = new ArrayList<>();
    ArrayList<Product> ps = new ArrayList<>();

    @FXML
    void initialize(){
        Category c = new Category();
        c.setName("Bla");
        c.getColumns().add("name");
        c.getColumns().add("price");
        activeCategory = c;
        for (int i = 0; i < 10; i++) {
            Product p = new Product();
            p.setName("mads" + i);
            p.setCat(c);
            ps.add(p);
        }
        makeCollums();
//        data.addAll(ps);
        productTable.setEditable(true);
//        TableColumn firstNameCol = new TableColumn("First Name");
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        productTable.getColumns().addAll(columns);

        productTable.setItems(data);
    }

    void makeCollums(){

        for (int i = 0; i < activeCategory.getColumns().size(); i++) {
            columns.add(new TableColumn(activeCategory.getColumns().get(i)));

        }

        for (int i = 0; i < ps.size(); i++) {
            if(ps.get(i).getCat() == activeCategory){
                data.add(ps.get(i));
            }
        }
    }
}
