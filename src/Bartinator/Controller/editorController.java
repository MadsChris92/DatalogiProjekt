package Bartinator.Controller;

import Bartinator.Other.ButtonCustom;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Gamer on 14-09-2016.
 */
public class editorController {
    Stage popUpStage;
    public Button addItemBtn;
    public VBox btnContainer;
    public Button removeItemBtn;
    public Label txtHeader;
    int btnCount = 0;
    Parent root1 = null;
    ArrayList<Button> btnList = new ArrayList<Button>();

    public void addItemHandler(ActionEvent actionEvent) {
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

    public void startPopUp(){
        try {
            root1 = FXMLLoader.load(getClass().getResource("../View/btnCreate.fxml"));
            System.out.println("dada");
        } catch (IOException e) {
            e.printStackTrace();
        }
        popUpStage.setScene(new Scene(root1, 500, 500));
        System.out.println("d");
        popUpStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void setBtnPreference(){
        for (int i = 0; i < btnList.size(); i++) {
            btnList.get(i).setMinSize(100,100);
        }
    }

}
