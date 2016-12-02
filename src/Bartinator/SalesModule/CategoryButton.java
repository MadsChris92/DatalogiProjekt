package Bartinator.SalesModule;

import Bartinator.Model.Category;
import javafx.scene.control.Button;

/**
 * Created by martin on 12/1/16.
 */
public class CategoryButton extends Button {
	private Category mCategory;

	public CategoryButton(SalesController controller, Category category) {
		mCategory = category;
		setText(category.getName());
		setStyle("-fx-base: #f5efb9;");
		setOnAction(controller.handleCatBtn);
		setMinHeight(controller.mBtnRadius);
		setMinWidth(controller.mBtnRadius);
		setMaxHeight(controller.mBtnRadius);
		setMaxWidth(controller.mBtnRadius);
	}

	public Category getCategory() {
		return mCategory;
	}
}
