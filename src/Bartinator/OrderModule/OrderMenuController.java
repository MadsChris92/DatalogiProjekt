package Bartinator.OrderModule;

import Bartinator.DataAccessObjects.OrderDataAccessObject;
import Bartinator.Model.Order;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class OrderMenuController implements Initializable {

	public TableView<Order> mOrderTable;
	public TableColumn<Order, Integer> mIdColumn;
	public TableColumn<Order, String> mBartenderColumn;
	public TableColumn<Order, Double> mReceiptColumn;
	public TableColumn<Order, Date> mDateColumn;
	public ListView<String> mReceiptView;
	public DatePicker mDatePicker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mOrderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		mOrderTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Order>() {
			@Override
			public void onChanged(Change<? extends Order> change) {
				if(change.getList().isEmpty()){
					mReceiptView.setItems(null);
				} else {
					ObservableList<String> list = getStrings(change);
					mReceiptView.setItems(list);
				}
			}
		});
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
	}

	private ObservableList<String> getStrings(ListChangeListener.Change<? extends Order> change) {
		ObservableList<? extends Order> orders = change.getList();
		ObservableList<String> list = FXCollections.observableArrayList(Printer.makeReceipt((List<Order>) orders, mDatePicker.getValue()));
		return list;
	}


	//TODO: Find et bedre hjem til den her funktionær


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

	public void handleExit(ActionEvent actionEvent){
		Navigator.switchToAdminView();
	}

	public void handleDatePicked(ActionEvent actionEvent) {
		LocalDate localDate = ((DatePicker)actionEvent.getSource()).getValue();
		//Converts the LocalDate into a date object(Date keeps track of time as well as date, LocalDate just keeps track of day, month and year)
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		//Fetch the orders from the database, which were made on the given day
		List<Order> orders = OrderDataAccessObject.getInstance().getOrderOnDay(date);
		mOrderTable.getItems().clear();
		for(Order order : orders) {
			mOrderTable.getItems().add(order);
		}
	}

	public void handlePrintButton(){
	}
}
