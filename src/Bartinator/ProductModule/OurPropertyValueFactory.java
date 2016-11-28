package Bartinator.ProductModule;

import Bartinator.Model.Product;
import com.sun.javafx.scene.control.Logging;
import javafx.beans.NamedArg;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import sun.util.logging.PlatformLogger;

import java.util.Map;

/**
 * Created by martin on 11/2/16.
 */
public class OurPropertyValueFactory implements Callback<TableColumn.CellDataFeatures<Product,String>, ObservableValue<String>> {
	private final String key;

	private Class<?> columnClass;
	private String previousProperty;
	private Map<String, String> descriptions;

	/**
	 * Creates a default PropertyValueFactory to extract the value from a given
	 * TableView row item reflectively, using the given key name.
	 *
	 * @param key The name of the key with which to attempt to
	 *      reflectively extract a corresponding value for in a given object.
	 */
	public OurPropertyValueFactory(@NamedArg("key") String key) {
		this.key = key;
	}

	/** {@inheritDoc} */
	@Override public ObservableValue<String> call(TableColumn.CellDataFeatures<Product,String> param) {
		return getCellData(param.getValue());
	}

	/**
	 * Returns the key name provided in the constructor.
	 */
	public final String getKey() { return key; }

	private ObservableValue<String> getCellData(Product rowData) {
		if (getKey() == null || getKey().isEmpty() || rowData == null) return null;

		try {
			// we attempt to cache the key reference here, as otherwise
			// performance suffers when working in large data models. For
			// a bit of reference, refer to RT-13937.
			if (columnClass == null || previousProperty == null ||
					! columnClass.equals(rowData.getClass()) ||
					! previousProperty.equals(getKey())) {

				// create a new PropertyReference
				this.columnClass = rowData.getClass();
				this.previousProperty = getKey();
			}

			this.descriptions = rowData.getDescriptions();
			String value = descriptions.get(getKey());
			return new ReadOnlyObjectWrapper<String>(value);
		} catch (IllegalStateException e) {
			// log the warning and move on
			final PlatformLogger logger = Logging.getControlsLogger();
			if (logger.isLoggable(PlatformLogger.Level.WARNING)) {
				logger.finest("Can not retrieve key '" + getKey() +
						"' in PropertyValueFactory: " + this +
						" with provided class type: " + rowData.getClass(), e);
			}
		}

		return null;
	}
}
