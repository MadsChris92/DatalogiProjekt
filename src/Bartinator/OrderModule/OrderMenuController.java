package Bartinator.OrderModule;

import Bartinator.DataAccessObjects.OrderDataAccessObject;
import Bartinator.Model.Order;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class OrderMenuController implements Initializable {

	public TableView<Order> mOrderTable;
	public TableColumn<Order, Integer> mIdColumn;
	public TableColumn<Order, String> mBartenderColumn;
	public TableColumn<Order, Double> mReceiptColumn;
	public TableColumn<Order, Date> mDateColumn;
	public ListView<String> mReceiptView; // dette er det listview der bliver brugt til at vise Receipt.
	public DatePicker mDatePickerFrom;
	public DatePicker mDatePickerTo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Add listener til DatePicker
		mDatePickerFrom.valueProperty().addListener((observable, oldValue, newValue) -> showOrderOnDate(newValue));
		mDatePickerFrom.setValue(LocalDate.now());

		mOrderTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		//Listener der fortæller om der er valgt noget i order table.
		mOrderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue == null) { //Hvis ingen reciept er valgt
						mReceiptView.setItems(null);
					} else { //Hvis en reciept er valgt
						ObservableList<String> list = getStrings(newValue);
						mReceiptView.setItems(list);
					}
				}
		);

		//Konfigurer cellerne i tabellen.
		mIdColumn.setCellValueFactory(
				(TableColumn.CellDataFeatures<Order, Integer> param) ->
						new ReadOnlyObjectWrapper<Integer>(param.getValue().getId())
		);
		mBartenderColumn.setCellValueFactory(
				(TableColumn.CellDataFeatures<Order, String> param) ->
						new ReadOnlyObjectWrapper<String>(param.getValue().getBartenderName())
		);
		mReceiptColumn.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<Double>(param.getValue().getTotalPrice())
		);
		mDateColumn.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<Date>(param.getValue().getTimestamp())
		);

		//Sætter fonten i alle cellerne på reciept viewet
		Font font = new Font("Rod", 11);
		mReceiptView.setCellFactory(value -> {
			ListCell<String> cell = new ListCell<String>(){
				@Override
				protected void updateItem(String item, boolean empty){
					super.updateItem(item, empty); //super.updateItem er fra cell klassen i java
					if(item != null) {
						setText(item);
					} else {
						setText("");
					}
				}
			};
			cell.setFont(font);
			return cell;
		});
	}

	//Viser orders fra den valgte dato
	private void showOrderOnDate(LocalDate localDate) {
		mOrderTable.getItems().clear();
		if(localDate == null) return; //no date chosen, stop function here.

		//Converts the LocalDate into a date object(Date keeps track of time as well as date,
		// LocalDate just keeps track of day, month and year)
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		//Fetch the orders from the database, which were made on the given day
		List<Order> orders = OrderDataAccessObject.getInstance().getOrdersOnDay(date);
		for (Order order : orders) {
			mOrderTable.getItems().add(order);
		}
	}
	//Laver en viselig reciept til reciept view
	private ObservableList<String> getStrings(Order order) {
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		return FXCollections.observableArrayList(Printer.makeReceipt(orders));
	}

	public void handleProductManagementBtn(ActionEvent actionEvent) {
		Navigator.switchToProductManagementView();
	}

	public void handleEmployeeManagementBtn(ActionEvent actionEvent) {
		Navigator.switchToEmployeeManagementView();
	}

	public void handleStockManagementBtn(ActionEvent actionEvent) {
		AlertBoxes.displayErrorBox("Under Construction", "Sorry, this function is not yet available!");
	}

	public void handleLogOut(ActionEvent actionEvent) {
		if (AlertBoxes.displayConfirmationBox("Logging out!", "Are you sure, you want to log out?")) {
			Navigator.switchToLoginView();
		}
	}

	public void handleExit(ActionEvent actionEvent) {
		Navigator.switchToAdminView();
	}

	public void handlePrintButton() {
		//TODO Fix det så det bliver til pdf
		LocalDate date = mDatePickerFrom.getValue();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save report");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Html Files", "*.html"),
				new FileChooser.ExtensionFilter("Text Files", "*.txt")
		);
		fileChooser.setInitialFileName(String.format("report (%s).html", date));


		File file = fileChooser.showSaveDialog(Navigator.getInstance().getTheStage());

		if(file!=null) {
			List<Order> orders = mOrderTable.getItems();
			double sumTotal = 0;
			for (Order order : orders) sumTotal += order.getTotalPrice();

			String html = new Printer().htmlIt(orders, date, sumTotal);
			saveFile(html, file);//bruger metoden lige nedeunder
		}
	}
		// skriver om hvorvidt den har gemt eller ej og den gemmer for den overstående metode
	private void saveFile(String html, File file) {
		System.out.println(file);
		try {
			if (file.createNewFile()) {
				System.out.println("Created file " + file.getName());
			}
		} catch (IOException e) {
			AlertBoxes.displayErrorBox("Couldn't create file", e.getMessage());
			e.printStackTrace();
		}
		if (file.exists()) {
			if (file.isFile()) {
				try {
					PrintWriter writer = new PrintWriter(file, "UTF-8");
					writer.print(html);
					writer.close();
				} catch (FileNotFoundException e) {
					AlertBoxes.displayErrorBox("Couldn't save file", e.getMessage());
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					AlertBoxes.displayErrorBox("Encoding not Supported", e.getMessage());
					e.printStackTrace();
				}
			} else {
				System.out.println("File " + file.getName() + " is apparently not a file.");
			}
		} else {
			System.out.println("File " + file.getName() + " not found. Didn't I just make it tho?");
		}
	}
}
