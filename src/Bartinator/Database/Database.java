package Bartinator.Database;


import Bartinator.Model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class Database {

    private static final String endpoint = "datalogiprojektruc2016-bartinator.chcbu6lph5q9.eu-central-1.rds.amazonaws.com";

    // Hold a reusable reference to a SessionFactory (since only one is needed)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // create a stadardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void stop(){
		sessionFactory.close();
	}

	private static List<?> fetch(Class<?> clazz){
		// Open a session
		Session session = sessionFactory.openSession();

		// Create criteria object
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(clazz);
		query.select(query.from(clazz));

		// Get a list of objects according to the criteria object
		List<Object> objects = session.createQuery(query).list();

		// Close the session
		session.close();

		// Return the list of Objects
		return objects;
	}

	private static List<?> fetch(Class<?> clazz, String attribute, Object value){

		// Open a session
		Session session = sessionFactory.openSession();

		// Create criteria object
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(clazz);
		Root<Class> userRoot = query.from(clazz);
		query.select(userRoot);
		query.where(criteriaBuilder.equal(userRoot.get(attribute), value));

		// Get a list of objects according to the criteria object
		List<Object> objects = session.createQuery(query).list();

		// Close the session
		session.close();

		// Return the list of Objects
		return objects;
	}

	@SuppressWarnings("unchecked")
    private static List<User> fetchAllUsers(){
		return (List<User>)fetch(User.class);
    }

	@SuppressWarnings("unchecked")
	private static User fetchUser(String username){
		// Henter en bruger baseret på brugernavn, hvis brugeren ikke findes returnes null
		// sql udgaven af det functionen gør er:
		// SELECT * FROM User WHERE User."username"=username;
		// hvis der er mere end én bruger med samme brugernavn, så vælger den det første resultat
		List<User> users = (List<User>) fetch(User.class, "username", username);
		User user;
		if(users.size() > 0) {
			if(users.size() > 1) System.out.println("Multiple users with the same username");
			user = users.get(0);
		}else {
			user = null; // user not found, or multiple users
		}

		return user;
	}

    private static void save(Object object){
        // Open a seesion
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

	private static void remove(Object object){
		// Open a seesion
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

	public static boolean userExists(String username){
		return fetchUser(username)!=null;
	}

    public static User verifyLogin(String username, String password) {
        User user = fetchUser(username);
		if(user != null) {
			if (/* !password.equals("") && */ user.getPassword() == password.hashCode()) {
				return user;
			} else {
				System.out.println("incorrect password");
				return null;
			}
		} else {
			System.out.println("user not found");
			return null;
		}
    }

    public static void test(){
		Admin admin = new Admin();
        admin.setName("Hans");
        admin.setUsername("hans");
        save(admin);
        fetch(Admin.class).forEach(System.out::println);
		remove(admin);

		Consumer consumer = new Consumer();
		consumer.setName("Heksen");
		consumer.setUsername("hex69");
		consumer.setBalance(420.00);
		save(consumer);
		fetch(Consumer.class).forEach(System.out::println);
		remove(consumer);

		Bartender bartender = new Bartender();
		bartender.setName("Grethe");
		bartender.setUsername("greathe");
		save(bartender);
		fetch(Bartender.class).forEach(System.out::println);
		remove(bartender);

		Product testProduct = new Product();
		testProduct.setName("Hansens");
		testProduct.setPrice(15);
		testProduct.setCategory("Drink");
		testProduct.setDescription("alco", "15%");
		testProduct.setDescription("year", "Lav");
		save(testProduct);
		fetch(Product.class).forEach(System.out::println);
		remove(testProduct);
	}
}