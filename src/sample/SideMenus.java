package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SideMenus extends AnchorPane {
    
    private UIController parentController;
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

// "shopping_button");
//        helpButton.setId("help_button");
//        historyButton.setId("history_button");
//        myPagesButton.setId("my_pages_button"