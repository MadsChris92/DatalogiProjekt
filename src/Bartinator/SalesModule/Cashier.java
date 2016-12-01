package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.OrderDataAccessObject;
import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;
import Bartinator.Model.Order;
import Bartinator.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cashier {

    private Map<Product, Integer> mCart = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {

        if (mCart.containsKey(product)) {
            mCart.replace(product, mCart.get(product) + quantity);
            //TODO: Kan gøres mere simpelt -> Tjek dokumentationen Map
            /*for (Map.Entry<Product, Integer> p : mCart.entrySet()) {
                if (p.getKey().getId() == product.getId()) {
                    int currValue = mCart.get(product);
                    mCart.replace(product, currValue + quantity);
                }
            }*/
        } else {
            mCart.put(product, quantity);
        }
    }

    public boolean removeProduct(Product product) {
        if(mCart.containsKey(product)) {
            mCart.remove(product);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeProduct(Product product, int quantity) {
        if (mCart.containsKey(product)) {
            int currValue = mCart.get(product);
            mCart.replace(product, currValue - quantity);
            if(mCart.get(product) == 0){
                mCart.remove(product);
            }
            return true;
        } else {
            return false;
        }
    }

    public void clearCart(){
        mCart.clear();
    }

    public double getTotal(){
        double sum = 0;
        for (Map.Entry<Product,Integer> p : mCart.entrySet()) {
            sum += (p.getValue()*p.getKey().getPrice());
        }
        return sum;
    }

    public ObservableList<String> getObservableCart(){
        List<String> resultAsList = new ArrayList<>();
        for (Product product : mCart.keySet()) {
            resultAsList.add(product.getPrice() + "DKK   x " + mCart.get(product) + "  " + product.getName() + " -" + product.getId());
        }
        resultAsList.add("Total: " + getTotal() + " DKK");
        ObservableList<String> result = FXCollections.observableList(resultAsList);

        return result;
    }
    public boolean checkOut() {

        //create receipt
        List<String> receipt = new ArrayList<>();
        for (Map.Entry<Product, Integer> p : mCart.entrySet()) {
            receipt.add(p.getKey().getPrice() + "DKK   x " + p.getValue() + "  " + p.getKey().getName() + " - Product Id" + p.getKey().getId());
        }

        //Create order
        Employee employee = EmployeeDataAccessObject.getInstance().getActiveEmployee();
        Order order = new Order(getTotal(), receipt, employee.getName(), employee.getId());

        //Save and return
        System.out.printf("%s", order.toString());

        OrderDataAccessObject.getInstance().saveOrder(order);
        return true;
    }

//    public void getObservableCart(){
//        ObservableMap<Product, Integer> result =
//    }
}
