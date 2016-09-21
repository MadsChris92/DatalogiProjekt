package Bartinator.Controller;

import Bartinator.Model.ButtonCustom;
import Bartinator.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class editorController {

    Stage popUpStage;
    public Button addItemBtn;
    public VBox btnContainer;
    public Button removeItemBtn;

    ArrayList<Button> btnList = new ArrayList<Button>();
    static ArrayList<Product> products = new ArrayList<>();

    public void addItemHandler(ActionEvent actionEvent) throws IOException {
        ButtonCustom b = new ButtonCustom();
        b.setOnAction(e -> itemHandler());
        btnContainer.getChildren().add(b);
        btnList.add(b);
        setBtnPreference();
        startPopUp();
    }

    private void itemHandler() {
        System.out.println("fads");
    }

    public void startPopUp() throws IOException {
        Parent root1;
        popUpStage = new Stage();
        root1 = FXMLLoader.load(loginController.class.getResource("../View/btnCreateMenu.fxml"));
        popUpStage.setScene(new Scene(root1, 500, 500));
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.show();
    }


    private void setBtnPreference(){
        for (Button aBtnList : btnList) {
            aBtnList.setMinSize(100, 100);
        }
    }
}
