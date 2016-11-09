package Bartinator.DataAccessObjects;

import Bartinator.Model.Order;


public class OrderDataAccessObject extends MainDataAccessObject {

    private static final OrderDataAccessObject instance = new OrderDataAccessObject();

    public static OrderDataAccessObject getInstance(){ return instance; }

    public void saveOrder(Order order){
        save(order);
    }

}
