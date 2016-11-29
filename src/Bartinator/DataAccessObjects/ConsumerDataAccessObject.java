package Bartinator.DataAccessObjects;


import java.io.IOException;
import java.util.List;

public class ConsumerDataAccessObject extends MainDataAccessObject {

	private static final ConsumerDataAccessObject instance = new ConsumerDataAccessObject();

	public static ConsumerDataAccessObject getInstance() {
		return instance;
	}

	public List<Consumer> fetchConsumers() throws IOException {
		List<Consumer> consumers = (List<Consumer>) fetch(Consumer.class);
		if(consumers != null){
			return consumers;
		}
		throw new IOException("Fetching Consumers failed");
	}

	public Consumer fetchConsumerById(int id) {
		List<Consumer> consumers = (List<Consumer>) fetch(Consumer.class, "mConsumerID", id);
		Consumer consumer;
		if(consumers.size() > 0){
			consumer = consumers.get(0);
		} else {
			consumer = null;
		}
		return consumer;
	}

	public boolean consumerExists(int id){
		return fetchConsumerById(id) != null;
	}

	public void saveConsumer(Consumer consumer) {
		save(consumer);
	}

	public void deleteConsumer(int id) {
		Consumer consumer = fetchConsumerById(id);
		remove(consumer);
	}

	public void updateConsumer(Consumer consumer){
		update(consumer);
	}
}
