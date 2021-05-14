package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyPages extends AnchorPane {
    
    private UIController parentController;
    
    public MyPages(MyPagesController controller) {
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mina_sidor.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(controller);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        
    }
}
