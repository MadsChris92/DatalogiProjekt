package Bartinator.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
	@Id
	private	String name;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> columns = new ArrayList<>();
	@ManyToOne
	private Category category;

	//en tom konstruktør
	public Category(){}


	//getteer's og setter til hele lortet
	public String getName() {
		return name;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	//bliver ikke brugt og er ikke en getter eller setters
	public boolean contains(Product product){
		return product.getCategory().getName().equals(this.name);
	}


	// Under søger om et object o er magen til den aktuelle kategori objekt
	@Override //ps. bliver ikke brugt.
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		return name.equals(category.name);

	}

	@Override// tostring konventer et objekt til en string.
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
