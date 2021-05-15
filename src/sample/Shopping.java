package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Shopping extends AnchorPane {
    
    private UIController parentController;
    
    public Shopping(ShoppingController controller) {
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shopping.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(controller);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
    }
}
