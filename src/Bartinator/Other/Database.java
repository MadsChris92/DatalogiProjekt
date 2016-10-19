package Bartinator.Other;


import Bartinator.Model.Consumer;
import Bartinator.Model.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
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

	private MysqlDataSource dataSource;

    // Hold a reusable reference to a SessionFactory (since only one is needed)
    private static final SessionFactory sessionFactory = buildSessionFactory();


    private static SessionFactory buildSessionFactory() {
        // create a stadardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    /// setupBartinator.users
    // http://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database
    public void setup(String user, String password) {
        dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(endpoint);
    }

    public static void stop(){
		sessionFactory.close();
	}


    @SuppressWarnings("unchecked")
	private static User fetchUser(String username){
		// Open a session
		Session session = sessionFactory.openSession();

		// Create criteria object
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(User.class);
		Root<User> userRoot = query.from(User.class);
		query.select(userRoot);
		query.where(criteriaBuilder.equal(userRoot.get("username"), username));

		// Get a list of Contact objects according to the criteria object
		List<User> users = session.createQuery(query).list();
		User user;
		if(users.size() > 0) {
			if(users.size() > 1) System.out.println("Multiple users with the same username");
			user = users.get(0);
		}else {
			user = null; // user not found, or multiple users
		}

		// Close the session
		session.close();

		// Return the list of Contacts
		return user;

	}

    private static List<User> fetchAllUsers(){
        // Open a session
        Session session = sessionFactory.openSession();

        // Create criteria object
        // Criteria criteria = session.createCriteria(User.class); // Apparently deprecated, probably wasn't complicated enough :/
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery query = criteriaBuilder.createQuery(User.class);
		query.select(query.from(User.class));

        // Get a list of Contact objects according to the criteria object
        List<User> users = session.createQuery(query).list();

        // Close the session
        session.close();

        // Return the list of Contacts
        return users;
    }

    private static void save(Object user){
        // Open a seesion
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        session.save(user);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

	private static void remove(Object user){
		// Open a seesion
		Session session = sessionFactory.openSession();

		// Begin a transaction
		session.beginTransaction();

		// Use the session to save the contact
		session.remove(user);

		// Commit the transaction
		session.getTransaction().commit();

		// Close the session
		session.close();
	}

    public static User verifyLogin(String username, String password) {
        // TODO: make a method that fetches just one user, instead of all of them
        User user = fetchUser(username);
		if(user != null) {
			if (!password.equals("") && user.getPassword() == password.hashCode()) {
				System.out.println(user.getName() + " has logged in");
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
        User testUser = new User();
        testUser.setName("Hans");
        testUser.setUsername("hans");
        testUser.setPassword("hund".hashCode());
        save(testUser);
        fetchAllUsers().forEach(System.out::println);
		remove(testUser);

		Consumer testConsumer = new Consumer();
		testConsumer.setName("Hans");
		testConsumer.setUsername("hans");
		testConsumer.setPassword("hund".hashCode());
		testConsumer.setBalance(100.00);
		save(testConsumer);
		//fetchAllUsers().forEach(System.out::println);
		//remove(testConsumer);
	}
}