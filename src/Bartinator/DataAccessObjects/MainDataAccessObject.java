package Bartinator.DataAccessObjects;


import Bartinator.Model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;


// singleton
public abstract class MainDataAccessObject {

    // Hold a reusable reference to a SessionFactory (since only one is needed)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Laver en ny session factory ud fra indstillingerne i hibernate.cfg.xml
    private static SessionFactory buildSessionFactory() {
        // create a standardServiceRegistry
        //final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


	public static List<?> fetch(Class<?> clazz){
		// Open a session
		Session session = sessionFactory.openSession();

		// Create criteria object
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(clazz);
		query.select(query.from(clazz));

		// Get a list of objects according to the criteria object
		List<?> objects = session.createQuery(query).list();

		// Close the session
		session.close();

		// Return the list of Objects
		return objects;
	}

	// Det er en metode der bliver kaldt fra de andre DAO klasser for at henter objekter fra databasen
	public static List<?> fetch(Class<?> clazz, String attribute, Object value){
		// Open a session
		Session session = sessionFactory.openSession();

		// Create criteria object
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(clazz);
		Root<Class> root = query.from(clazz);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get(attribute), value));

		// Get a list of objects according to the criteria object
		List<?> objects = session.createQuery(query).list();

		// Close the session
		session.close();

		// Return the list of Objects
		return objects;
	}

	Session getSession(){
		return sessionFactory.openSession();
	}

    public static void save(Object object){
        // Open a session
		Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        session.save(object);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

	static void remove(Object object){
		// Open a session
		Session session = sessionFactory.openSession();

		// Begin a transaction
		session.beginTransaction();

		// Use the session to save the contact
		session.remove(object);

		// Commit the transaction
		session.getTransaction().commit();

		// Close the session
		session.close();
	}

	static void update(Object object){
		// Open a session
		Session session = sessionFactory.openSession();

		// Begin a transaction
		session.beginTransaction();

		// Use the session to update the object
		session.update(object);

		// Commit the transaction
		session.getTransaction().commit();

		// Close the session
		session.close();
	}


	public static void stop(){
		sessionFactory.close();
	}
}