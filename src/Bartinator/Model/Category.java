package Bartinator.Model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
	@Id
	private	String name;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> columns = new ArrayList<>();

	public Category(){}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		return name.equals(category.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public void addColumn(String name){
		columns.add(name);
	}

	public void removeColumn(String name){
		columns.remove(name);
	}

	public boolean contains(Product product){
		return product.getCat().getName().equals(this.name);
	}
}
