package Bartinator.Controller;

import Bartinator.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import Bartinator.DataAccessObjects.*;
import java.util.ArrayList;


public class EditProductController {
    public ChoiceBox categoryMenu;
    public ListView listViewCol;
    public Button btnRemoveColumn;
    public TextField txtFieldColumn;
    public Button btnAddColumn;
    private Category activeCategory;
    public TableView<Product> productTable;
    private final ObservableList<Product> data = FXCollections.observableArrayList();
    private final ObservableList<String> catData = FXCollections.observableArrayList();
    private final ObservableList<String> catColumns = FXCollections.observableArrayList();
    private ArrayList<TableColumn<Product, ?>> columns = new ArrayList<>();
    private ArrayList<Product> ps = new ArrayList<>();
    private int activecolumn;
    ProductDataAccessObject pdao = new ProductDataAccessObject();

    @FXML
    void initialize(){


        createTestProducts();
        setCategories();
        makeColumns();
        populateCells();
        setListView();

        productTable.getColumns().addAll(columns);
        productTable.setItems(data);
        categoryMenu.setItems(catData);
        productTable.setEditable(true);

        btnAddColumn.setOnAction(event -> addColumn());
    }

    private void addColumn() {

    }

    private void setListView(){
        listViewCol.setMinWidth(120);
        listViewCol.setMinHeight(300);

        catColumns.clear();
        listViewCol.getItems().clear();
        catColumns.addAll(activeCategory.getColumns());
        listViewCol.setItems(catColumns);
        listViewCol.setEditable(true);
        listViewCol.setCellFactory(TextFieldListCell.forListView());
        listViewCol.setOnEditCommit(new EventHandler<ListView.EditEvent>() {
            @Override
            public void handle(ListView.EditEvent event) {
                activeCategory.getColumns().set(event.getIndex(), event.getNewValue().toString());
                updateTable();
            }
        });
        listViewCol.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                activecolumn = newValue.intValue();
                btnRemoveColumn.setOnAction(event -> removeColumn());
            }


        });
    }

    private void removeColumn() {
        if(activecolumn > 2) {
            activeCategory.getColumns().remove(activecolumn);
            updateTable();
        }
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
                        updateTable();
                    }
                }
        );
    }

    private void updateTable(){
        productTable.getColumns().removeAll(columns);
        productTable.getItems().clear();
        columns.clear();
        data.clear();

        makeColumns();
        populateCells();
        productTable.getColumns().addAll(columns);
        productTable.setItems(data);
        setListView();
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
                tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                tableColumn.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<Product, String> t) {
                                ((Product) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                ).setName(t.getNewValue());
                            }
                        }
                );
            }
            if(i == 2){
                TableColumn<Product, Double> tableColumn = new TableColumn<>(activeCategory.getColumns().get(i));
                columns.add(tableColumn);
                tableColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
                tableColumn.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
                    tableColumn.setOnEditCommit(
                            new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
                                @Override
                                public void handle(TableColumn.CellEditEvent<Product, Double> t) {
                                    ((Product) t.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    ).setPrice(t.getNewValue());

                                }
                            }
                    );
                }

            if(i > 2){
                TableColumn<Product, String> tableColumn = new TableColumn<>(activeCategory.getColumns().get(i));
                columns.add(tableColumn);
//                tableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>());
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

        for (int i = 0; i < 10; i++) {
            c1.getColumns().add(i + ". test");
        }

        c.getColumns().add("ID");
        c.getColumns().add("Name");
        c.getColumns().add("Price");


        for (int i = 0; i < 10; i++) {
            Product p = new Product();
            p.setName("mads" + i);
            p.setPrice(150);
            p.setCat(c);
            p.setId(i);
            ps.add(p);
        }
    }
}
