package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.OrderDataAccessObject;
import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.EmployeeModule.EmployeeRoster;
import Bartinator.EmployeeModule.ObservableEmployee;
import Bartinator.Model.Employee;
import Bartinator.Model.Order;
import Bartinator.Model.Product;
import Bartinator.Model.ReceiptItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Cashier {
	private ObservableList<CartItem> mCartItems = FXCollections.observableArrayList(CartItem.extractor());

	public void addProduct(Product product, int quantity) {
		int index = indexOf(product);
		if(index!=-1)// hvis den er der tilføj en ekstra quantity
			mCartItems.get(index).add(quantity);
		else {//hvis produkt ikke er i kurven
			mCartItems.add(new CartItem(product, quantity));
		}
	}
	//fjerne produkt uanset mængden
    public boolean removeProduct(Product product) {
		int index = indexOf(product);
		if(index!=-1) {
			mCartItems.remove(index);
			return true;
		} else {
			return false;
		}
    }

    // fjerner kun en bestemt mængde (1), dog kan den godt fjerne 5 eller et andet magisk tal hvis man havde lyst til det
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
	// ryder hele kurven
    public void clearCart(){
		mCartItems.clear();
    }
		//regner total ud
    public double getTotal(){
        double sum = 0;
		for (CartItem item : mCartItems) {
			sum += item.getTotal();
		}
        return sum;
    }
	//henter alle cartitems som Obervable
    public ObservableList<CartItem> getObservableCart(){
		return mCartItems;
    }
		//handle checkout
    public boolean checkOut() {
        //create receipt
		List<ReceiptItem> receipt = new ArrayList<>();
		for (CartItem item : mCartItems) {
			receipt.add(new ReceiptItem(item.getQuantity(), item.getProduct().getName(), item.getTotal()));
		}

        //Create order
		ObservableEmployee employee = EmployeeRoster.getInstance().getActiveEmployee();
        Order order = new Order(getTotal(), receipt, employee.getName(), employee.getId());

        //Save and return
        System.out.printf("%s", order.toString());

        OrderDataAccessObject.getInstance().saveOrder(order);
        return true;
    }
	//finder ud af hvor et produkt er på listen hvis den ikke er returner den -1
    private int indexOf(Product product){
		for (int i = 0; i < mCartItems.size(); i++) {
			CartItem item = mCartItems.get(i);
			if (item.getProduct().equals(product)) return i;
		}
		return -1;
	}
}
