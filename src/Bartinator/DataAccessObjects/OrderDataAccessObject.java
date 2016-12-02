package Bartinator.DataAccessObjects;

import Bartinator.Model.Order;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class OrderDataAccessObject extends MainDataAccessObject {

    private static final OrderDataAccessObject instance = new OrderDataAccessObject();

    public static OrderDataAccessObject getInstance(){ return instance; }

    public void saveOrder(Order order){
    	save(order);
    }

	private List<?> fetchBetween(Date start, Date end){

		// Open a session
		Session session = getSession();

		// Create criteria object
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		query.select(root);
		query.where(criteriaBuilder.between(root.get("mTimestamp"), start, end));

		// Get a list of objects according to the criteria object
		List<Object> objects = session.createQuery(query).list();

		// Close the session
		session.close();

		// Return the list of Objects
		return objects;
	}

    public List<Order> getOrderOnDay(Date start){
    	Date end;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(start);
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	end = calendar.getTime();
		System.out.println(start + " -> " + end);
		return (List<Order>) fetchBetween(start, end);
    }

    public Date getCurrentDate(){
		//TODO: Hent tiden fra databasen
    	return new Date();
	}
}
