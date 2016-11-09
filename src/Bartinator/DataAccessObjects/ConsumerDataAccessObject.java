package Bartinator.DataAccessObjects;


import Bartinator.Model.Consumer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerDataAccessObject extends MainDataAccessObject {

	private static final ConsumerDataAccessObject instance = new ConsumerDataAccessObject();

	public static ConsumerDataAccessObject getInstance() {
		return instance;
	}

	private List<Consumer> mConsumers;

	private ConsumerDataAccessObject() {
		mConsumers = new ArrayList<>();
		mConsumers.addAll((List<Consumer>) fetch(Consumer.class));
	}

	public List<Consumer> getConsumers() {
		return mConsumers;
	}

	public Consumer getConsumerById(int id) {
		for (Consumer c : mConsumers) {
			if (c.getStudentID() == id) {
				return c;
			}
		}
		return null;
	}

	public List<Consumer> searchConsumersByName(String search) {
		List<Consumer> result = new ArrayList<>();
		for (Consumer c : mConsumers) {
			if (c.getLastName().toLowerCase().contains(search.toLowerCase())) {
				result.add(c);
			}
		}
		return result;
	}
	public boolean saveConsumer(Consumer consumer) {
		boolean success = true;
		for (Consumer storedConsumer : mConsumers){
			if (storedConsumer.getStudentID() == consumer.getStudentID()){
				success = false;
			}
		}
		if(success){
			save(consumer);
		}
		return success;
	}
}
