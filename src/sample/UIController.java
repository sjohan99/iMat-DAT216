package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    public BackendController backend = new BackendController();
    public MyPagesController myPagesController = new MyPagesController(this);
    public HistoryController historyController = new HistoryController(this);
    public ShoppingController shoppingController = new ShoppingController(this, backend);
    public CheckoutController checkoutController;
    private AnimationTimer timer = new MyTimer();


    @FXML public Button shoppingButton, historyButton, myPagesButton, helpButton, expandButton, checkoutButton, backToShoppingButton,// main
            startShoppingButton, startHistoryButton, startMyPagesButton, skipGuideButton, endGuideButton, nextStepButton;// welcome page
    @FXML public Label iMatLabel, adressLabel, totalPriceLabel, itemAmountLabel, endGuideLabel, skipGuideLabel, varukorgLabel;
    @FXML private ImageView testimage;
    @FXML private FlowPane shoppingCartPane;
    @FXML public AnchorPane sideMenuParentAnchorPane, parentView, shoppingCartAnchorPane, startPagePane, guidePane1, guidePane2, guidePane3, guidePane4, guidePane5, guidePane6, guideButtonsPane, topBarPane;
    @FXML public StackPane guideStackPane, purchaseDonePane;
    @FXML private Line cartLineDivider;
    @FXML private ScrollPane shoppingCartScrollPane;
    @FXML private TextField searchTextField;

    private ButtonGrouper buttonGrouper;
    public SideMenus sideMenus = new SideMenus(this, historyController);
    public boolean shoppingCartExpanded;
    List<AnchorPane> guidePanes = new ArrayList<>();
    private int guideStep = 1;
    private Shopping shopping;
    public int varor = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCartExpanded = false;
        checkoutController = new CheckoutController(this, sideMenus);
        initMenuButtons();
        initStartMenuButtons();
        addSideMenus();
        startPagePane.toFront();
        guideStackPane.toBack();
        shoppingCartScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        shopping = new Shopping(shoppingController);
        initSearch();
        
        backend.shoppingCart.clear(); // Remove this to save cart between runs
        updateItemCardAmounts();
        updateShoppingCart();
    }

    /**
     * Adds all guidePanes to a List. Lowers opacity for surrounding elements and brings guide to front
     */
    public void initGuideView() {
        guidePanes.add(0,guidePane1);
        guidePanes.add(1,guidePane2);
        guidePanes.add(2,guidePane3);
        guidePanes.add(3,guidePane4);
        guidePanes.add(4,guidePane5);
        guidePanes.add(5,guidePane6);
        for (AnchorPane pane : guidePanes) { pane.setStyle("-fx-opacity: 0"); }
        guidePane1.setStyle("-fx-opacity: 1");
        topBarPane.setStyle("-fx-opacity: 0.5");
        shoppingCartAnchorPane.setStyle("-fx-opacity: 0.5");
        sideMenuParentAnchorPane.setStyle("-fx-opacity: 0.5");
        guideStackPane.toFront();
        guideButtonsPane.toFront();
        nextStepButton.toFront();
        skipGuideButton.setStyle("-fx-opacity: 1");
        skipGuideLabel.setStyle("-fx-opacity: 1");
    }
    
    private void initSearch() {
        searchTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                shoppingController.search(searchTextField.getText());
                changeMainView("shopping_button");
            }
        });
    }

    /**
     * Changes opacities on guidePanes and surrounding elements on button press
     * @param e
     */
    public void changeGuideView(ActionEvent e) {
        for (AnchorPane pane : guidePanes) { pane.setStyle("-fx-opacity: 0"); }

        switch(guideStep) {
            case 1:
                topBarPane.setStyle("-fx-opacity: 1");
                break;
            case 3:
                topBarPane.setStyle("-fx-opacity: 0.5");
                sideMenuParentAnchorPane.setStyle("-fx-opacity: 1");
                break;
            case 4:
                sideMenuParentAnchorPane.setStyle("-fx-opacity: 0.5");
                shoppingCartAnchorPane.setStyle("-fx-opacity: 1");
                break;
            case 5:
                shoppingCartAnchorPane.setStyle("-fx-opacity: 0.5");
                topBarPane.setStyle("-fx-opacity: 1");
                skipGuideButton.setStyle("-fx-opacity: 0");
                skipGuideLabel.setStyle("-fx-opacity: 0");
                endGuideButton.toFront();
                endGuideLabel.toFront();
                break;
            default:

                break;
        }
        guidePanes.get(guideStep).setStyle("-fx-opacity: 1");
        guideStep++;
    }

    public void changeMainView(String buttonId) {
        startPagePane.toBack(); // Needed because it won't disappear otherwise
        guideStackPane.toBack();
        purchaseDonePane.toBack();
        topBarPane.setStyle("-fx-opacity: 1");
        shoppingCartAnchorPane.setStyle("-fx-opacity: 1");
        sideMenuParentAnchorPane.setStyle("-fx-opacity: 1");
        guideStep = 1;
        parentView.getChildren().clear();
        switch(buttonId) {
            case "history_button":
                parentView.getChildren().add(new History(historyController));
                sideMenus.updateHistory(); // Needed to reset button styles
                break;
            case "my_pages_button":
                parentView.getChildren().add(new MyPages(myPagesController));
                sideMenus.myPagesButtonGroup.activateSideBarButtons("personal_info");
                break;
            case "checkout_button":
                shrinkShoppingCart();
                parentView.getChildren().add(new Checkout(checkoutController));
                sideMenus.changeIcon(0);
                checkoutController.populateItemsToBeBought();
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton1", 0);
                break;
            case "help_button":
                initGuideView();
                break;
            case "next_step_button_final":
            case "go_back_to_shopping_button":
            case "shopping_button":
                //parentView.getChildren().add(new Shopping(shoppingController));
                parentView.getChildren().add(shopping);
                sideMenus.shoppingButtonGroup.activateSideBarButtons("");
                break;
            case "imat":
                startPagePane.toFront();
                buttonGrouper.activate("");
                break;
        }
        if (buttonId.equals("checkout_button")) {
            disableCheckoutButton();
            disableExpandCartButton();
        }
        else {
            enableCheckoutButton();
            enableExpandCartButton();
        }
    }
    
    private void disableCheckoutButton() {
            checkoutButton.getStyleClass().removeAll("checkout_button");
            checkoutButton.getStyleClass().removeAll("checkout_button_disabled");
            checkoutButton.getStyleClass().add("checkout_button_disabled");
            checkoutButton.setText("Du är i kassan");
    }
    
    private void enableCheckoutButton() {
        checkoutButton.getStyleClass().removeAll("checkout_button");
        checkoutButton.getStyleClass().removeAll("checkout_button_disabled");
        checkoutButton.getStyleClass().add("checkout_button");
        checkoutButton.setText("Gå till kassan");
    }
    
    private void disableExpandCartButton() {
        expandButton.setMouseTransparent(true);
        expandButton.setStyle("-fx-opacity: 0.6;");
        expandButton.setText("Ändra ej tillgänglig");
    }
    
    private void enableExpandCartButton() {
        expandButton.setText("Ändra i varukorgen");
        expandButton.setMouseTransparent(false);
        expandButton.setStyle("-fx-opacity: 1;");
    }

    /**
     * Method is called when expanding shopping cart.
     * Resizes necessary elements and changes the button-text.
     */
    public void expandShoppingCart() {
        if (!shoppingCartExpanded) {
            expandButton.setText("Minska varukorg");
        }
        else {
            expandButton.setText("Ändra i varukorgen");
        }
        timer.start();
        
        for (Node item : shoppingCartPane.getChildren()) {
            ((CartItem) item).resizeNameLabel();
        }
    }
    
    public void shrinkShoppingCart() {
        if (shoppingCartExpanded) {
            expandButton.setText("Ändra i varukorgen");
            timer.start();
        }
    }
    
    private void addSideMenus() {
        sideMenuParentAnchorPane.getChildren().clear();
        sideMenuParentAnchorPane.getChildren().add(sideMenus);
    }
    
    public void updateShoppingCart() {
        shoppingCartPane.getChildren().clear();
        varor = 0;
        int backgroundCount = 0;
        int length = backend.shoppingCart.getItems().size();
        List<ShoppingItem> list = backend.shoppingCart.getItems();
        for (int i = length - 1; i >= 0; i--) {
            shoppingCartPane.getChildren().add(backend.createFinalShoppingCartItem(list.get(i).getProduct(), list.get(i).getAmount(), this, backgroundCount++));
        }
        updateItemCardAmounts();
        totalPriceLabel.setText("Totalt: " + backend.getProductPrice(backend.shoppingCart.getTotal()) + " kr");
        itemAmountLabel.setText("Varor: " + varor + " st");
    }
    
    public void updateItemCardAmounts() {
        for (ItemCard itemCard : shoppingController.itemCards) {
            boolean found = false;
            for (ShoppingItem shoppingItem : backend.shoppingCart.getItems()) {
                if (shoppingItem.getProduct().equals(itemCard.getProduct())) {
                    itemCard.amount = shoppingItem.getAmount();
                    found = true;
                }
            }
            if (!found) {
                itemCard.amount = 0;
            }
        }
        for (ItemCard itemCard : shoppingController.itemCards) {
            if (itemCard.getProduct().getUnitSuffix().equals("kg")) {
                itemCard.cardAmountTextField.setText(String.valueOf(itemCard.amount));
            } else {
                itemCard.cardAmountTextField.setText(String.valueOf((int) itemCard.amount));
            }
            
            if (itemCard.amount > 0) {
                itemCard.itemAmountTextField.getStyleClass().removeAll("rounded");
                itemCard.itemAmountTextField.getStyleClass().removeAll("rounded_in_cart");
                itemCard.itemAmountTextField.getStyleClass().add("rounded_in_cart");
            } else {
                itemCard.itemAmountTextField.getStyleClass().removeAll("rounded");
                itemCard.itemAmountTextField.getStyleClass().removeAll("rounded_in_cart");
                itemCard.itemAmountTextField.getStyleClass().add("rounded");
            }
            
        }
        
    }
    
    /**
     * Initializes buttons to be "toggled" by setting their id's (has to be the same as their css id
     * to not remove their individual styling) and adding an actionevent which reads the id of the pressed button.
     */
    private void initMenuButtons() {
        buttonGrouper = new ButtonGrouper();
        buttonGrouper.addButtonToList(shoppingButton);
        buttonGrouper.addButtonToList(helpButton);
        buttonGrouper.addButtonToList(historyButton);
        buttonGrouper.addButtonToList(myPagesButton);
        
        shoppingButton.setId("shopping_button");
        helpButton.setId("help_button");
        historyButton.setId("history_button");
        myPagesButton.setId("my_pages_button");
        
        for (Button button : buttonGrouper.getButtons()) {
            button.setOnAction(e -> toggleOnButton(e));
        }
    }
    
    private void initStartMenuButtons() {
        
        startHistoryButton.setId("history_button");
        startShoppingButton.setId("shopping_button");
        startMyPagesButton.setId("my_pages_button");
        
        // Lägger de här sålänge
        checkoutButton.setId("checkout_button");
        checkoutButton.setOnAction(e -> toggleOnButton(e));
        backToShoppingButton.setId("next_step_button_final");
        backToShoppingButton.setOnAction(e -> toggleOnButton(e));
        
        skipGuideButton.setId("shopping_button");
        skipGuideButton.setOnAction(e -> toggleOnButton(e));
        endGuideButton.setId("shopping_button");
        endGuideButton.setOnAction(e -> toggleOnButton(e));
        
        iMatLabel.setOnMouseClicked(e -> labelButton(e));
    }
    
    private void labelButton(MouseEvent e) {
        changeMainView("imat");
    }
    
    /**
     * Reads the id of the pressed button and sends it topMenuBarButtons to handle the rest. Also tells SideMenus to update view
     *
     * @param e triggered event
     */
    public void toggleOnButton(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        buttonGrouper.activate(id);
        sideMenus.changeSideMenu(id);
        changeMainView(id);
    }
    
    public void showAllItems() {
        shoppingController.shoppingFlowPane.getChildren().clear();
        shoppingController.resetScrollbar();
        shoppingController.addItems(shoppingController.itemCards.subList(0, shoppingController.loadedItems));
        shoppingController.shoppingHeadline.setText("Alla");
    }
    
    /**
     * Animation timer for sliding out the cart when expanded.
     */
    private class MyTimer extends AnimationTimer {
        
        double speed = 20;
        
        @Override
        public void handle(long l) {
            slideOut();
        }
        
        /**
         * Method is called every frame, starts with high speed then slows down at the end
         */
        private void slideOut() {
            if (!shoppingCartExpanded) {
                if (speed > 1) {
                    speed = 0.93 * speed;
                }
                
                shoppingCartAnchorPane.setMaxWidth(shoppingCartAnchorPane.getMaxWidth() + speed);
                cartLineDivider.setEndX(cartLineDivider.getEndX() + speed);
                
                if (shoppingCartAnchorPane.getMaxWidth() >= 500) {
                    stop();
                    speed = 20;
                    shoppingCartAnchorPane.setMaxWidth(500);
                    
                    shoppingCartExpanded = true;
                }
            } else {
                if (speed > 1) {
                    speed = 0.93 * speed;
                }
                
                shoppingCartAnchorPane.setMaxWidth(shoppingCartAnchorPane.getMaxWidth() - speed);
                cartLineDivider.setEndX(cartLineDivider.getEndX() - speed);
                
                if (shoppingCartAnchorPane.getMaxWidth() <= 275) {
                    stop();
                    speed = 20;
                    shoppingCartAnchorPane.setMaxWidth(275);
                    
                    shoppingCartExpanded = false;
                }
            }
        }
    }
}
