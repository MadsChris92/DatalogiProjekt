package Bartinator.Controller;

import Bartinator.Model.editorModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class EditorController2 {
    public Button categoryEdit;
    public Button categoryAdd;
    public HBox bottomContainer;
    public Button productEdit;


    @FXML
    public void initialize(){
        editorModel em = new editorModel();
        em.setCatAdd(categoryAdd);
        em.setProductEdit(productEdit);
        em.setCatEdit(categoryEdit);
        em.setContainer(bottomContainer);
    }
}
