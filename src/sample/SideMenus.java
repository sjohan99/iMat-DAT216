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
    private ButtonGrouper myPagesButtonGroup;
    private ButtonGrouper shoppingButtonGroup;
    
    
    /**
     * Different side menu views
     */
    @FXML AnchorPane shopping_menuPane, my_pages_menuPane, checkout_menuPane, history_menuPane;
    @FXML Button personUppgifterButton, adressButton, kortUppgifterButton;
    @FXML Button mejeriButton, meatButton, fruitButton, skafferiButton, snacksButton, dryckButton, ekoButton, greensButton;

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
        initMyPagesButtons();
        initShoppingButtons();
    }
    
    private void initShoppingButtons() {
        shoppingButtonGroup = new ButtonGrouper();
        shoppingButtonGroup.addButtonToList(mejeriButton);
        shoppingButtonGroup.addButtonToList(meatButton);
        shoppingButtonGroup.addButtonToList(snacksButton);
        shoppingButtonGroup.addButtonToList(dryckButton);
        shoppingButtonGroup.addButtonToList(fruitButton);
        shoppingButtonGroup.addButtonToList(skafferiButton);
        shoppingButtonGroup.addButtonToList(ekoButton);
        shoppingButtonGroup.addButtonToList(greensButton);
    
        for (Button button : shoppingButtonGroup.getButtons()) {
            button.setOnAction(e -> buttonPressed(e));
        }
    }
    
    private void initMyPagesButtons() {
        myPagesButtonGroup = new ButtonGrouper();
        myPagesButtonGroup.addButtonToList(personUppgifterButton);
        myPagesButtonGroup.addButtonToList(adressButton);
        myPagesButtonGroup.addButtonToList(kortUppgifterButton);
    
        for (Button button : myPagesButtonGroup.getButtons()) {
            button.setOnAction(e -> buttonPressed(e));
        }
    }
    
    private void buttonPressed(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        parentController.myPagesController.myPagesChangeWindow(id);
        parentController.shoppingController.ShoppingChangeWindow(id);
        myPagesButtonGroup.activateSideBarButtons(id);
        shoppingButtonGroup.activateSideBarButtons(id);
    }

    /**
     * Changes the side menu view depending on the top menu bar button id
     * @param buttonId
     */
    public void changeSideMenu(String buttonId) {
        System.out.println(buttonId);
        switch(buttonId) {
            case "history_button":
                history_menuPane.toFront();
                break;
            case "my_pages_button":
                my_pages_menuPane.toFront();
                break;
            case "checkout_button":
                checkout_menuPane.toFront();
                break;
            default:
                shopping_menuPane.toFront();
                break;
        }
    }
}
