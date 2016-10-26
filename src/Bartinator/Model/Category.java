package Bartinator.Model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

/**
 * Created by Martin on 19-10-2016.
 */

@Entity
public class Category {
	@Id
	private	String name;
	@ElementCollection
	private	ArrayList<String> columns = new ArrayList<>();

	public Category(String name){
		this.name = name;
	}

	public void addColumn(String name){
		columns.add(name);
	}

	public void removeColumn(String name){
		columns.remove(name);
	}
}
