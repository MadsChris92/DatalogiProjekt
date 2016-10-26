package Bartinator.Controller;

import Bartinator.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class EditProductController {
    public ChoiceBox categoryMenu;
    private Category activeCategory;
    public TableView<Product> productTable;
    private final ObservableList<Product> data = FXCollections.observableArrayList();
    private final ObservableList<String> catData = FXCollections.observableArrayList();
    private ArrayList<TableColumn<Product, ?>> columns = new ArrayList<>();
    private ArrayList<Product> ps = new ArrayList<>();

    @FXML
    void initialize(){


        createTestProducts();
        setCategories();
        makeColumns();
        populateCells();


        productTable.setEditable(true);
        productTable.getColumns().addAll(columns);
        productTable.setItems(data);
        categoryMenu.setItems(catData);
    }

    void setCategories(){
        for (int i = 0; i < editorModel.categories.size(); i++) {
            catData.add(editorModel.categories.get(i).getName());
        }
        categoryMenu.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        activeCategory = editorModel.categories.get(newValue.intValue());
                        System.out.println(activeCategory.getName());
                        resetTable();
                        makeColumns();
                        populateCells();
                        productTable.getColumns().addAll(columns);
                        productTable.setItems(data);
                    }
                }
        );
    }

    private void resetTable(){
        productTable.getColumns().removeAll(columns);
        productTable.getItems().clear();
        columns.clear();
        data.clear();
    }

    private void makeColumns(){
        for (int i = 0; i < activeCategory.getColumns().size(); i++) {
            if(i == 0){
                TableColumn<Product, String> tableColumn = new TableColumn<>(activeCategory.getColumns().get(i));
                columns.add(tableColumn);
                tableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
            }

            if(i == 1){
               TableColumn<Product, String> tableColumn = new TableColumn<>(activeCategory.getColumns().get(i));
               columns.add(tableColumn);
               tableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            }
            if(i == 2){
                TableColumn<Product, Integer> tableColumn = new TableColumn<>(activeCategory.getColumns().get(i));
                columns.add(tableColumn);
                tableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
            }

        }
    }

    private void populateCells(){
        for (int i = 0; i < ps.size(); i++) {

            if(ps.get(i).getCat() == activeCategory){

                data.add(ps.get(i));
            }
        }
    }

    private void createTestProducts(){
        Category c = new Category();
        Category c1 = new Category();
        editorModel.categories.add(c1);
        editorModel.categories.add(c);
        c1.setName("test1");
        c.setName("test2");
        activeCategory = c;

        c1.getColumns().add("name1");
        c1.getColumns().add("price2");
        c1.getColumns().add("id");

        c.getColumns().add("ID");
        c.getColumns().add("name");
        c.getColumns().add("price");


        for (int i = 0; i < 10; i++) {
            Product p = new Product();
            p.setName("mads" + i);
            p.setPrice(100);
            p.setCat(c);
            p.setID(i);
            ps.add(p);
        }
    }
}
