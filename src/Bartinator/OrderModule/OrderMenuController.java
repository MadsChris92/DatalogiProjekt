package Bartinator.OrderModule;

import Bartinator.DataAccessObjects.OrderDataAccessObject;
import Bartinator.Model.Order;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mOrderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		mOrderTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Order>() {
			@Override
			public void onChanged(Change<? extends Order> change) {
				if(change.getList().isEmpty()){
					mReceiptView.setItems(null);
				} else {
					ObservableList<String> list = FXCollections.observableArrayList();
					int priceSum = 0;
					for(Order order : change.getList()){
						list.addAll(order.getReceipt());
						list.add("              ------------");
						list.add(String.format("%-35s %5.2f", "Subtotal:", order.getTotalPrice()));
						list.add(String.format("%-35s %5.2f", "25% Moms:", order.getTotalPrice()*0.2));
						priceSum += order.getTotalPrice()*100;
					}
					list.add("              -==========-");
					list.add(String.format("%-35s %5.2f", "Total:", priceSum/100.0));
					list.add(String.format("%-35s %5.2f", "25% Moms:", priceSum/500.0));
					for (String string : list) {
						System.out.println(string);
					}
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
		System.out.println(localDate);
		System.out.println();
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Order> orders = OrderDataAccessObject.getInstance().getOrderOnDay(date);
		for(Order order : orders) {
			mOrderTable.getItems().add(order);
		}
	}
}
