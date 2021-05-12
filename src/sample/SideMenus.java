package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SideMenus extends AnchorPane {
    
    private UIController parentController;
    @FXML AnchorPane shopping_menuPane;

    public SideMenus(UIController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("side_menus.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.parentController = parentController;
        shopping_menuPane.toFront();
    }

    public void changeSideMenu(String buttonId) {

    }
}
