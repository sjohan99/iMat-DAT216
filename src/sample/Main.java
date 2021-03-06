package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.util.ResourceBundle;

public class Main extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("base.fxml"));
        primaryStage.setTitle("iMat");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    
    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                IMatDataHandler.getInstance().shutDown();
            }
        }));
    }
}
