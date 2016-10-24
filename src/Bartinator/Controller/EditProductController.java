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
    public TableView productTable;
    final ObservableList<Product> data = FXCollections.observableArrayList();
    ArrayList<Product> ps = new ArrayList<>();

    @FXML
    void initialize(){
        for (int i = 0; i < 10; i++) {
            Product p = new Product();
            p.setName("mads" + i);
            ps.add(p);
        }

        data.addAll(ps);
        productTable.setEditable(true);
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        productTable.getColumns().add(firstNameCol);

        productTable.setItems(data);
    }

    void makeCollums(){

    }
}
