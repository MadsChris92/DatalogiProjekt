package Bartinator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static Stage theStage;
    Button b;
    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View/loginView.fxml"));
        theStage.setTitle("Bartinator");
        theStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
        b = (Button) root.lookup("#loginBtn");

        b.setOnAction(e -> {
            try {
                loginHandler();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        b.setText("test");

    }

    private void loginHandler() throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("View/editor.fxml"));
        theStage.setScene(new Scene(root1, 700, 600));
 //       b.setText("dasda");
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setPrimaryStage(Scene s){
        theStage.setScene(s);
    }
} // test
// test2
