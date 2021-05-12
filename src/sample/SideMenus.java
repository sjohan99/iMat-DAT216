package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SideMenus extends AnchorPane {
    
    private UIController parentController;
    /**
     * Different side menu views
     */
    @FXML AnchorPane shopping_menuPane, my_pages_menuPane, checkout_menuPage, history_menuPane;

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
    }

    /**
     * Changes the side menu view depending on the top menu bar button id
     * @param buttonId
     */
    public void changeSideMenu(String buttonId) {
        switch(buttonId) {
            case "history_button":
                history_menuPane.toFront();
                break;
            case "my_pages_button":
                my_pages_menuPane.toFront();
                break;
            default:
                shopping_menuPane.toFront();
                break;
        }

    }
}
