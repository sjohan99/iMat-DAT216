package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SideMenus extends AnchorPane {
    
    private UIController parentController;
    private TopMenuBarButtons buttonGroup;
    /**
     * Different side menu views
     */
    @FXML AnchorPane shopping_menuPane, my_pages_menuPane, checkout_menuPage, history_menuPane;
    @FXML Button personUppgifterButton, adressButton, kortUppgifterButton;

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
        initButtons();
    }
    
    private void initButtons() {
        buttonGroup = new TopMenuBarButtons();
        buttonGroup.addButtonToList(personUppgifterButton);
        buttonGroup.addButtonToList(adressButton);
        buttonGroup.addButtonToList(kortUppgifterButton);
    
        for (Button button : buttonGroup.getButtons()) {
            button.setOnAction(e -> buttonPressed(e));
        }
    }
    
    private void buttonPressed(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        parentController.myPagesController.myPagesChangeWindow(e, id);
        buttonGroup.activateSideBarButtons(id);
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
