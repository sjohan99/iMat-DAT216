package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SideMenus extends AnchorPane {
    
    private UIController parentController;
    private ButtonGrouper myPagesButtonGroup;
    private ButtonGrouper shoppingButtonGroup;
    ButtonGrouper checkoutButtons = new ButtonGrouper();
    /**
     * Different side menu views
     */
    @FXML AnchorPane shopping_menuPane, my_pages_menuPane, checkout_menuPane, history_menuPane;
    @FXML Button mejeriButton, meatButton, fruitButton, skafferiButton, snacksButton, dryckButton, ekoButton, greensButton;
    @FXML Button personUppgifterButton, adressButton, kortUppgifterButton, checkoutButton1, checkoutButton2, checkoutButton3, checkoutButton4, checkoutButton5, checkoutButton6;
    @FXML ImageView image1, image2, image3, image4, image5, image6;

    private List<ImageView> images;

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
        initImages();
    }
    
    private void initButtons() {
        initMyPagesButtons();
        initShoppingButtons();
        initCheckoutButtons();
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
    
    private void initCheckoutButtons() {
        checkoutButtons.addButtonToList(checkoutButton1);
        checkoutButtons.addButtonToList(checkoutButton2);
        checkoutButtons.addButtonToList(checkoutButton3);
        checkoutButtons.addButtonToList(checkoutButton4);
        checkoutButtons.addButtonToList(checkoutButton5);
        checkoutButtons.addButtonToList(checkoutButton6);
    }

    private void initImages() {
        images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        images.add(image6);
    }

    public void changeIcon(int step) {

        for (ImageView image : images) {
            image.setImage(new Image("sample/resources/arrow.png"));
        }

        for (int i = 0; i < step ; i++) {
            System.out.println(images.get(i));
            images.get(i).setImage(new Image("sample/resources/checkmark.png"));
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
