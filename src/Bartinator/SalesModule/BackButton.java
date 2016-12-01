package Bartinator.SalesModule;

import javafx.scene.control.Button;

/**
 * Created by martin on 12/1/16.
 */
public class BackButton extends Button{
	public BackButton(SalesController controller) {
		super("<-");
		setOnAction(controller.handleBackBtn);
		setMinHeight(controller.mBtnRadius);
		setMinWidth(controller.mBtnRadius);
		setMaxHeight(controller.mBtnRadius);
		setMaxWidth(controller.mBtnRadius);
	}
}
