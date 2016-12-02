package Bartinator.ProductModule;

import Bartinator.Model.*;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import Bartinator.DataAccessObjects.*;
import java.util.ArrayList;


public class EditProductController {
    public ChoiceBox<String> categoryMenu;
    public ListView<String> listViewCol;
    public Button btnRemoveColumn;
    public TextField txtFieldColumn;
    public Button btnAddColumn;
    public TextField txtProductName;
    public ListView<Product> listViewProd;
    public TextField txtCategoryName;
    public Label catLabel;
    public TabPane tabPane;
    private Category activeCategory;
    private ArrayList<Product> activeProducts = new ArrayList<>();
    public TableView<Product> productTable;
    private final ObservableList<Product> data = FXCollections.observableArrayList();
    private final ObservableList<String> catData = FXCollections.observableArrayList();
    private final ObservableList<String> catColumns = FXCollections.observableArrayList();
    private ArrayList<TableColumn<Product, ?>> columns = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private int activeColumn;
    private ProductDataAccessObject pdao = ProductDataAccessObject.getInstance();

	private ArrayList<Category> categories = new ArrayList<>();


    @FXML
    void initialize(){
        pdao.refresh();

		categories = (ArrayList<Category>) pdao.getCategories();

		products.clear();
		products.addAll(pdao.getProducts());

        System.out.println(products.size());
        for(Product product : products){
            System.out.println(product.toString());
        }

        activeCategory = categories.get(0);

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
        updateTable();

    }

    private void addColumn() {
        if(!txtFieldColumn.getText().isEmpty()){
            activeCategory.addColumn(txtFieldColumn.getText());
            pdao.updateCategory(activeCategory);
            updateTable();
        }else{
            AlertBoxes.displayInformationBox("ERROR", "Must not be empty");
        }
    }

    private void setListView(){
        catColumns.clear();
        listViewCol.getItems().clear();
        catColumns.addAll(activeCategory.getColumns());
        listViewCol.setItems(catColumns);
        listViewCol.setEditable(true);
        listViewCol.setCellFactory(TextFieldListCell.forListView());
        listViewCol.setOnEditCommit(event -> {
			activeCategory.getColumns().set(event.getIndex(), event.getNewValue());
			updateTable();
		});
        listViewCol.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			activeColumn = newValue.intValue();
			btnRemoveColumn.setOnAction(event -> removeColumn());
		});
    }

    private void removeColumn() {
        String col = catColumns.get(activeColumn);

        for (Product product : products) {
            if (activeCategory.contains(product)) {
                product.getDescriptions().remove(col);
            }
        }
        for (Product p : products){
            pdao.updateProduct(p);
        }

        activeCategory.getColumns().remove(activeColumn);
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

    private void setCategories(){
		for (Category category : categories) {
			catData.add(category.getName());
		}
        categoryMenu.getSelectionModel().selectedIndexProperty().addListener(
				(observable, oldValue, newValue) -> {
					activeCategory = categories.get(newValue.intValue());
					System.out.println(activeCategory.getName());
					updateTable();
				}
		);
    }

    private void updateTable(){
        productTable.getColumns().removeAll(columns);
        productTable.getItems().clear();
        columns.clear();
        data.clear();
        catLabel.setText(activeCategory.getName());
        makeColumns();
        populateCells();
        productTable.getColumns().addAll(columns);
        productTable.setItems(data);
        setListView();
        setListViewProd();

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
				cell -> {
					Product product = cell.getRowValue();
					product.setName(cell.getNewValue());
					pdao.updateProduct(product);
				}
		);
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        columns.add(priceColumn);
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(
				cell -> {
					Product product = cell.getRowValue();
					product.setPrice(cell.getNewValue());
					pdao.updateProduct(product);
				}
		);
        for (int i = 0; i < activeCategory.getColumns().size(); i++) {
                String category = activeCategory.getColumns().get(i);
                System.out.println(category);
                TableColumn<Product, String> tableColumn = new TableColumn<>(category);
                columns.add(tableColumn);

                //tableColumn.setCellValueFactory(new OurPropertyValueFactory(category));
                tableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDescriptions().get(category)));
                tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                tableColumn.setOnEditCommit(
						cell -> {
							Product product = cell.getRowValue();
							product.setDescription(cell.getTableColumn().getText(), cell.getNewValue());
							pdao.updateProduct(product);
						}
				);
        }
    }

    private void populateCells(){
		for (Product product : products) {
			if (product.getCategory().getName().equals(activeCategory.getName())) {
				data.add(product);
			}
		}
    }

    private void createTestProducts(){
        Category c = new Category();
        Category c1 = new Category();
        categories.add(c1);
        categories.add(c);
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
            p.setCategory(c);
            p.setId(i);
            products.add(p);
        }
    }

    public void btnAddProduct(ActionEvent actionEvent) {


        String name = txtProductName.getText();
        if(!name.isEmpty()){
            Product product = new Product();
            product.setName(name);
            products.add(product);
            product.setCategory(activeCategory);
            updateTable();
            pdao.saveProduct(product);
            setListViewProd();
        }else{
            AlertBoxes.displayInformationBox("ERROR", "Must not not be null");
        }

    }

    public void handleRemoveProd(ActionEvent actionEvent) {
        int selectedItem = listViewProd.getSelectionModel().getSelectedIndex();
        if(selectedItem >= 0){
            Product product = activeProducts.get(selectedItem);

            System.out.println(product.getName());
            products.remove(product);
            pdao.removeProduct(product);
            updateTable();
            setListViewProd();
        }else{
            AlertBoxes.displayInformationBox("ERROR", "No product selected");
        }

    }

    public void addCategoryHandler(ActionEvent actionEvent) {
        Category category = new Category();
        category.setName(txtCategoryName.getText());
		if (!categories.contains(category)) {
			categories.add(category);
			pdao.saveCategories();
		}
	}

    public void removeCategoryHandler(ActionEvent actionEvent) {
        for(Product product : products){
            if(activeCategory.contains(product)){
                products.remove(product);
            }
        }
        categories.remove(activeCategory);
        pdao.removeCategory(activeCategory);
        updateTable();
    }
    public void handleExit(ActionEvent actionEvent) {
        Navigator.switchToAdminView();
    }
}
