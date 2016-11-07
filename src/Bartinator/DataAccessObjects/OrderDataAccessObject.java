package Bartinator.DataAccessObjects;

import Bartinator.Model.Order;

/**
 * Created by Lord Daniel on 02-11-2016.
 */
public class OrderDataAccessObject extends MainDataAccessObject {

    private static final OrderDataAccessObject instance = new OrderDataAccessObject();

    public static OrderDataAccessObject getInstance(){ return instance; }

    public void saveOrder(Order order){
        save(order);
    }

}
