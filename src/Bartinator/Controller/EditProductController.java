package Bartinator.Controller;

import Bartinator.Model.*;
import Bartinator.Utility.AlertBoxes;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    public TextField txtProductName;
    public ListView listViewProd;
    public TextField txtCategoryName;
    private Category activeCategory;
    private ArrayList<Product> activeProducts = new ArrayList<>();
    public TableView<Product> productTable;
    private final ObservableList<Product> data = FXCollections.observableArrayList();
    private final ObservableList<String> catData = FXCollections.observableArrayList();
    private final ObservableList<String> catColumns = FXCollections.observableArrayList();
    private ArrayList<TableColumn<Product, ?>> columns = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private int activecolumn;
    ProductDataAccessObject pdao = ProductDataAccessObject.getInstance();


    @FXML
    void initialize(){
        pdao.refresh();

		editorModel.categories = (ArrayList<Category>) pdao.getCategories();

		products.clear();
		products.addAll(pdao.getProducts());

        System.out.println(products.size());
        for(Product product : products){
            System.out.println(product.toString());
        }

        activeCategory = editorModel.categories.get(0);

        //createTestProducts();
        setListViewProd();
        setCategories();
        makeColumns();
        populateCells();
        setListView();
        setListViewProd();

        productTable.getColumns().addAll(columns);
        productTable.setItems(data);
        categoryMenu.setItems(catData);
        productTable.setEditable(true);

        btnAddColumn.setOnAction(event -> addColumn());
    }

    private void addColumn() {
        if(txtFieldColumn.getText().isEmpty() == false){
            activeCategory.addColumn(txtFieldColumn.getText());
            pdao.updateCategory(activeCategory);
            updateTable();
        }else{
            AlertBoxes alertBoxes = new AlertBoxes();
            alertBoxes.displayInformationBox("ERROR", "Must not be empty");
        }
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
        String col = catColumns.get(activecolumn);

        for (Product product : products) {
            if (activeCategory.contains(product)) {
                product.getDescriptions().remove(col);
            }
        }
        for (Product p : products){
            pdao.updateProduct(p);
        }

        activeCategory.getColumns().remove(activecolumn);
        pdao.updateCategory(activeCategory);
        updateTable();

    }

    private void setListViewProd(){
        activeProducts.clear();

        for(Product p: products){
            if(activeCategory.contains(p)){
                activeProducts.add(p);
            }
        }
        ObservableList<Product> ps = javafx.collections.FXCollections.observableList(activeProducts);
        listViewProd.setItems(ps);
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
        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        columns.add(idColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        columns.add(nameColumn);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> t) {
                        Product product = t.getRowValue();
                        product.setName(t.getNewValue());
                        pdao.updateProduct(product);
                    }
                }
        );
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        columns.add(priceColumn);
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, Double> t) {
                        Product product = t.getRowValue();
                        product.setPrice(t.getNewValue());
                        pdao.updateProduct(product);
                    }
                }
        );
        for (int i = 0; i < activeCategory.getColumns().size(); i++) {
                String category = activeCategory.getColumns().get(i);
                System.out.println(category);
                TableColumn<Product, String> tableColumn = new TableColumn<>(category);
                columns.add(tableColumn);

                tableColumn.setCellValueFactory(new OurPropertyValueFactory(category));
                tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                tableColumn.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<Product, String> t) {
                                Product product = t.getRowValue();
                                product.setDescription(t.getTableColumn().getText(), t.getNewValue());
                                pdao.updateProduct(product);
                            }
                        }
                );
        }
    }

    private void populateCells(){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getCat().getName().equals(activeCategory.getName())){
                data.add(products.get(i));
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
            products.add(p);
        }
    }

    public void btnAddProduct(ActionEvent actionEvent) {


        String name = txtProductName.getText();
        if(name.isEmpty()==false){
            Product p = new Product();
            p.setName(name);
            products.add(p);
            p.setCat(activeCategory);
            updateTable();
            pdao.saveProduct(p);
            setListViewProd();
        }else{
            AlertBoxes.displayInformationBox("ERROR", "Must not not be null");
        }

    }

    public void handleRemoveProd(ActionEvent actionEvent) {
        int selectedItem = listViewProd.getSelectionModel().getSelectedIndex();

        if(selectedItem >= 0){
            products.remove(selectedItem);
            updateTable();
            setListViewProd();
        }else{
            AlertBoxes.displayInformationBox("ERROR", "No product selected");
        }

    }

    public void addCategoryHandler(ActionEvent actionEvent) {
        Category c = new Category();
        c.setName(txtCategoryName.getText());



        if(editorModel.categories.contains(c)){

        }else{
            editorModel.categories.add(c);
            pdao.saveCategories();
        }


    }

    public void removeCategoryHandler(ActionEvent actionEvent) {
        for(Product p : products){
            if(activeCategory.contains(p)){
                products.remove(p);
            }
        }
        editorModel.categories.remove(activeCategory);
        pdao.removeCategory(activeCategory);
        updateTable();
    }
}
