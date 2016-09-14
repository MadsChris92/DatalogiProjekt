package Bartinator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage theStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View/loginView.fxml"));
        theStage.setTitle("Bartinator");
        theStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
        Button b = (Button) root.lookup("loginBtn");
        b.setText("test");

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setPrimaryStage(Scene s){
        theStage.setScene(s);
    }
} // test
// test2
