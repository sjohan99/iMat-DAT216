package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {
    
    
    //   /../resources/.ttf
    @Override
    public void start(Stage primaryStage) throws Exception{
        //ResourceBundle bundle = java.util.ResourceBundle.getBundle("sample/resources/Main");
        Parent root = FXMLLoader.load(getClass().getResource("base.fxml"));
        
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
