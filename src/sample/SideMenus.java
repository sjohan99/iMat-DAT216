package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SideMenus extends AnchorPane {
    
    private UIController parentController;
    private HistoryController historyParentController;
    private BackendController backend = new BackendController();
    public ButtonGrouper myPagesButtonGroup;
    public ButtonGrouper shoppingButtonGroup;
    ButtonGrouper checkoutButtonsGroup;
    /**
     * Different side menu views
     */
    @FXML AnchorPane shopping_menuPane, my_pages_menuPane, checkout_menuPane, history_menuPane;
    @FXML Button allButton, mejeriButton, meatButton, fruitButton, skafferiButton, snacksButton, dryckButton, ekoButton, greensButton;
    @FXML Button personUppgifterButton, adressButton, kortUppgifterButton, checkoutButton1, checkoutButton2, checkoutButton3, checkoutButton4, checkoutButton5, checkoutButton6;
    @FXML ImageView image1, image2, image3, image4, image5, image6;
    @FXML FlowPane receiptFlowPane;
    @FXML ScrollPane historyScrollPane;

    private List<ImageView> images;
    public List<Node> historyButtonList;

    public SideMenus(UIController parentController, HistoryController historyController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("side_menus.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.historyParentController = historyController;
        this.parentController = parentController;
        initButtons();
        initImages();
        updateHistory();
        historyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        historyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
    
    public void updateHistory() {
        receiptFlowPane.getChildren().clear();
        historyButtonList = new ArrayList<>();
        int i = 0;
        for (Order order : backend.getOrdersSorted()) {
            receiptFlowPane.getChildren().add(new HistoryMenuButton(order, historyParentController, this));
            historyButtonList.add(receiptFlowPane.getChildren().get(i));
            i++;
        }
    }
    
    private void initButtons() {
        initMyPagesButtons();
        initShoppingButtons();
        initCheckoutButtons();
    }
    
    private void initShoppingButtons() {
        shoppingButtonGroup = new ButtonGrouper();
        shoppingButtonGroup.addButtonToList(allButton);
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
        checkoutButtonsGroup = new ButtonGrouper();
        checkoutButtonsGroup.addButtonToList(checkoutButton1);
        checkoutButtonsGroup.addButtonToList(checkoutButton2);
        checkoutButtonsGroup.addButtonToList(checkoutButton3);
        checkoutButtonsGroup.addButtonToList(checkoutButton4);
        checkoutButtonsGroup.addButtonToList(checkoutButton5);
        checkoutButtonsGroup.addButtonToList(checkoutButton6);
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
            image.setStyle("-fx-opacity: 0");
        }

        for (int i = 0; i < step; i++) {
            images.get(i).setImage(new Image("sample/resources/checkmark.png"));
            images.get(i).setFitHeight(35);
            images.get(i).setFitWidth(35);
            images.get(i).setStyle("-fx-opacity: 1");
        }
        if (step < 6) {
            images.get(step).setStyle("-fx-opacity: 1");
            images.get(step).setImage(new Image("sample/resources/arrow.png"));
            images.get(step).setFitWidth(50);
            images.get(step).setFitHeight(50);
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
                parentController.showAllItems();
                break;
        }
    }
}
