package Bartinator.ProductModule;

import Bartinator.Model.Category;
import Bartinator.Model.Product;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 */
public class EditProductController implements Initializable {
	public TableView<ObservableProduct> productTable;
	public TextField productTxtField;
	public TextField categoryTxtField;
	public Label categoryLabel;
	public TreeView<Category> categoryView;
    public TextField columnTxtField;
	public Label columnLabel;
	public ListView<String> columnView;
	public Button columnRemoveButton;
	public Button categoryRemoveButton;

	private final Property<Category> activeCategory = new SimpleObjectProperty<>();
	private final ObservableList<String> catColumns = FXCollections.observableArrayList();

    private ArrayList<TableColumn<ObservableProduct, ?>> columns = new ArrayList<>();

    private ProductCatalog productCatalog = ProductCatalog.getInstance();


	@FXML
	@Override
	public void initialize(URL location, ResourceBundle resources){
        productCatalog.refresh();

		ObservableList<Category> categories = productCatalog.getCategories();
        if(categories.size()>0){
			activeCategory.setValue(categories.get(0));
		} else {
        	activeCategory.setValue(null);
		}

        makeColumns();
		setCategoryTreeView();
        setColumnListView();
		setProductTable();

        updateTable();

    }

    // udfører operationer på dataen.
	// TODO Burde måske være i en anden klasse, men de passer måske heller ikke helt i ProductCatalog..?
	private boolean addColumn(Category category, String columnName) {
		if (columnName.isEmpty()) {
			AlertBoxes.displayErrorBox("No Name Given", "The column needs a name.");
		} else {
			if (category.getColumns().contains(columnName)) {
				AlertBoxes.displayErrorBox("Name Taken", "That name has been used already.");
			} else {
				category.addColumn(columnName);
				productCatalog.updateCategory(category);
				updateTable();
				return true;
			}
		}
		return false;
	}

    private void removeColumn(Category category, String columnName) {
    	for (ObservableProduct product : productCatalog.getProductsByCategory(category)) {
			product.getDescriptions().remove(columnName);
			productCatalog.updateProduct(product);
        }

        category.removeColumn(columnName);
        productCatalog.updateCategory(category);
        updateTable();
    }

    private void renameColumn(Category category, String oldName, String newName) {
		if(newName.isEmpty()) {
			AlertBoxes.displayErrorBox("No Name Given", "The column needs a name.");
		} else {
			if (category.getColumns().contains(newName)) {
				AlertBoxes.displayErrorBox("Cannot Rename", "A column with that name already exists!");
			} else {
				for (ObservableProduct product : productCatalog.getProductsByCategory(category)) {
					Map<String, String> descriptions = product.getDescriptions();
					descriptions.put(newName, descriptions.get(oldName));
					descriptions.remove(oldName);
					productCatalog.updateProduct(product);
				}

				category.removeColumn(oldName);
				category.addColumn(newName);
				productCatalog.updateCategory(category);
				updateTable();
			}
		}
	}

	private boolean addCategory(Category parent, String name) {
		if(name.isEmpty()){
			AlertBoxes.displayErrorBox("Cannot add category", "The category needs a name");
		} else {
			Category category = new Category();
			category.setName(name);
			category.setCategory(parent);
			if (productCatalog.contains(category)) {
				AlertBoxes.displayErrorBox("Cannot add category", "A category with that name already exists");
			} else {
				productCatalog.saveCategory(category);
				categoryView.setRoot(treeifyCategories(null));
				return true;
			}
		}
		return false;
	}

	private void removeCategory(Category category) {
		if(category == null) {
			AlertBoxes.displayErrorBox("Missing Category", "That category seems to not exist");
		} else {
			if (AlertBoxes.displayConfirmationBox("Warning", String.format("You are about to remove the category \"%s\" and all the products and categories it contains.%n%nAre you sure you wish to proceed?", activeCategory.getValue().getName()))) {
				productCatalog.removeCategory(category);
				categoryView.setRoot(treeifyCategories(null));
				updateTable();
			}
		}
	}

	private void renameCategory(Category category, String newName){
		if(newName.isEmpty()) {
			AlertBoxes.displayErrorBox("No Name Given", "The category needs a name.");
		} else {
			if (productCatalog.getCategoryByName(newName) != null) {
				AlertBoxes.displayErrorBox("Cannot Rename", "A category with that name already exists!");
			} else {
				productCatalog.renameCategory(category, newName);
			}
		}
	}

	private boolean addProduct(String name, Category category) {
		if(!name.isEmpty()){
			productCatalog.createProduct(name, category);
			return true;
		}else{
			AlertBoxes.displayErrorBox("Missing Name", "Product must have a name!");
			return false;
		}
	}

	private void removeProduct(ObservableProduct product) {
		if (product == null) {
			AlertBoxes.displayErrorBox("Cannot Remove", "No product selected");
		} else {
			System.out.println(product.getName());
			productCatalog.removeProduct(product);
		}
	}

	// Indstiller ui til at starte med
	private void setProductTable() {
		productTable.setEditable(true);
	}

	private void setColumnListView(){
		columnView.setItems(catColumns);
		columnView.setEditable(true);
		columnView.setCellFactory(TextFieldListCell.forListView());
		columnView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null){
				columnRemoveButton.setDisable(true);
				columnLabel.setText("");
			} else {
				columnRemoveButton.setDisable(false);
				columnLabel.setText(newValue);
			}
		});
		columnView.setOnEditCommit(event -> {
			renameColumn(activeCategory.getValue(), event.getSource().getItems().get(event.getIndex()), event.getNewValue());
			updateTable();
		});
	}

    private void setCategoryTreeView(){
    	categoryView.setShowRoot(true);
		categoryView.setEditable(true);
		categoryView.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<Category>() {
			@Override
			public String toString(Category category) {
				if(category == null){
					return "";
				} else {
					return category.getName();
				}
			}

			@Override
			public Category fromString(String string) {
				if(string.isEmpty()) return null;
				Category category = new Category();
				category.setName(string);
				return category;
			}
		}));
		categoryView.setOnEditCommit(event -> {
			System.out.println(event.getOldValue()+" -> "+event.getNewValue());
			renameCategory(event.getOldValue(), event.getNewValue().getName());
		});
    	categoryView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.getValue() == null) {
    			categoryRemoveButton.setDisable(true);
				activeCategory.setValue(null);
				categoryView.setEditable(false);
				categoryLabel.setText("");
				updateTable();
			} else {
				activeCategory.setValue(newValue.getValue());
				categoryRemoveButton.setDisable(false);
				categoryLabel.setText(activeCategory.getValue().getName());
				categoryView.setEditable(true);
				updateTable();
			}
		});
		categoryView.setRoot(treeifyCategories(null));
    }

    // Updaterer de forskellige ui elementer
	private TreeItem<Category> treeifyCategories(Category root){
		TreeItem<Category> categoryTreeItem = new TreeItem<>(root);
		categoryTreeItem.setExpanded(true);
		for(Category category : productCatalog.getCategoriesByCategory(root)){
			categoryTreeItem.getChildren().add(treeifyCategories(category));
		}
		return categoryTreeItem;
	}

    private void updateTable(){
        productTable.getColumns().clear();
        columns.clear();
		catColumns.clear();
		if(activeCategory.getValue()!=null) {
			makeColumns();
			catColumns.addAll(activeCategory.getValue().getColumns());
			productTable.getColumns().addAll(columns);
			productTable.setItems(productCatalog.getProductsByCategory(activeCategory.getValue()));
		}
	}

    private void makeColumns(){
        TableColumn<ObservableProduct, Integer> idColumn = new TableColumn<>("ID");
        columns.add(idColumn);
        //idColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
		idColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<Integer>(cell.getValue().getId()));

        TableColumn<ObservableProduct, String> nameColumn = new TableColumn<>("Name");
        columns.add(nameColumn);
        //nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
				cell -> {
					ObservableProduct product = cell.getRowValue();
					product.setName(cell.getNewValue());
					productCatalog.updateProduct(product);
				}
		);
        TableColumn<ObservableProduct, Double> priceColumn = new TableColumn<>("Price");
        columns.add(priceColumn);
        //priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		priceColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<Double>(cell.getValue().getPrice()));
        priceColumn.setCellFactory(TextFieldTableCell.<ObservableProduct, Double>forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(
				cell -> {
					ObservableProduct product = cell.getRowValue();
					product.setPrice(cell.getNewValue());
					productCatalog.updateProduct(product);
				}
		);

        // The custom columns
        for (String columnName : activeCategory.getValue().getColumns()) {
			TableColumn<ObservableProduct, String> tableColumn = new TableColumn<>(columnName);
			columns.add(tableColumn);

			//tableColumn.setCellValueFactory(new OurPropertyValueFactory(columnName));
			tableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDescriptions().get(columnName)));
			tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			tableColumn.setOnEditCommit(
					cell -> {
						ObservableProduct product = cell.getRowValue();
						product.setDescription(cell.getTableColumn().getText(), cell.getNewValue());
						productCatalog.updateProduct(product);
					}
			);
        }
    }

	//    private void createTestProducts(){
//        Category c = new Category();
//        Category c1 = new Category();
//        categories.add(c1);
//        categories.add(c);
//        c1.setName("test1");
//        c.setName("test2");
//        activeCategory.setValue(c);
//
//        for (int i = 0; i < 10; i++) {
//            c1.getColumns().add(i + ". test");
//        }
//
//        c.getColumns().add("ID");
//        c.getColumns().add("Name");
//        c.getColumns().add("Price");
//
//
//        for (int i = 0; i < 10; i++) {
//            Product p = new Product();
//            p.setName("mads" + i);
//            p.setPrice(150);
//            p.setCategory(c);
//            p.setId(i);
//            //products.add(p);
//        }
//    }

	// Der er kun handlers herfra
	public void handleRefresh(ActionEvent actionEvent){
    	productCatalog.refresh();
	}

    public void handleAddProduct(ActionEvent actionEvent) {
		String name = productTxtField.getText();
		Category category = activeCategory.getValue();
		addProduct(name, category);
	}

	public void handleRemoveProduct(ActionEvent actionEvent) {
		ObservableProduct product = productTable.getSelectionModel().getSelectedItem();
		removeProduct(product);

	}

	public void handleAddCategory(ActionEvent actionEvent) {
    	String name = categoryTxtField.getText();
		if(addCategory(activeCategory.getValue(), name)) {
			categoryTxtField.setText("");
		}
	}

	public void handleRemoveCategory(ActionEvent actionEvent) {
    	Category category = activeCategory.getValue();
		removeCategory(category);
	}

	public void handleAddColumn(ActionEvent actionEvent) {
		if(addColumn(activeCategory.getValue(), columnTxtField.getText())) {
			columnTxtField.setText("");
		}
	}

	public void handleRemoveColumn(ActionEvent actionEvent) {
		removeColumn(activeCategory.getValue(), columnView.getSelectionModel().getSelectedItem());
	}

	public void handleExit(ActionEvent actionEvent) {
		Navigator.switchToAdminView();
	}
}
