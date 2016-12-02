package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.OrderDataAccessObject;
import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;
import Bartinator.Model.Order;
import Bartinator.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cashier {
	private ObservableList<CartItem> mCartItems = FXCollections.observableArrayList(CartItem.extractor());

	public void addProduct(Product product, int quantity) {
		int index = indexOf(product);
		if(index!=-1)
			mCartItems.get(index).add(quantity);
		else {
			mCartItems.add(new CartItem(product, quantity));
		}
	}

    public boolean removeProduct(Product product) {
		int index = indexOf(product);
		if(index!=-1) {
			mCartItems.remove(index);
			return true;
		} else {
			return false;
		}
    }

    public boolean removeProduct(Product product, int quantity) {
		int index = indexOf(product);
		if(index!=-1) {
			CartItem item = mCartItems.get(index);
			item.subtract(quantity);
			if(item.getQuantity() <= 0)
				mCartItems.remove(index);
			return true;
		} else {
			return false;
		}
    }

    public void clearCart(){
		mCartItems.clear();
    }

    public double getTotal(){
        double sum = 0;
		for (CartItem item : mCartItems) {
			sum += item.getTotal();
		}
        return sum;
    }

    public ObservableList<CartItem> getObservableCart(){
		return mCartItems;
    }

    public boolean checkOut() {
        //create receipt
		List<String> receipt = new ArrayList<>();
		for (CartItem item : mCartItems) {
			receipt.add(String.format("%.2fDKK   x %d  %s - Product Id%d", item.getProduct().getPrice(), item.getQuantity(), item.getProduct().getName(), item.getProduct().getId()));
		}

        //Create order
        Employee employee = EmployeeDataAccessObject.getInstance().getActiveEmployee();
        Order order = new Order(getTotal(), receipt, employee.getName(), employee.getId());

        //Save and return
        System.out.printf("%s", order.toString());

        OrderDataAccessObject.getInstance().saveOrder(order);
        return true;
    }

    private int indexOf(Product product){
		for (int i = 0; i < mCartItems.size(); i++) {
			CartItem item = mCartItems.get(i);
			if (item.getProduct().equals(product)) return i;
		}
		return -1;
	}
}
