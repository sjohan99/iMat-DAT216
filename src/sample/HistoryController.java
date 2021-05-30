package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private UIController parentController;
    public BackendController backend = new BackendController();

    @FXML AnchorPane historyViewParentAnchorPane, historyGuidePane, historyItemPane;
    @FXML ScrollPane historyScrollPane;
    @FXML Label totalprisLabel, timeLabel;
    @FXML FlowPane HistoryItemFlowPane;
    @FXML Button addItemsToCart;
    
    Order currentlyShowingOrder;
    
    public HistoryController(UIController parentController) { this.parentController = parentController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyGuidePane.toFront();
        historyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        historyScrollPane.setStyle("-fx-font-size: 20px;");
    }
    
    public void populateHistoryOrder(Order order) {
        
        timeLabel.setText(backend.getCorrectDateFormat(order));
        HistoryItemFlowPane.getChildren().clear();
        double total = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            HistoryItemFlowPane.getChildren().add(new HistoryItem(shoppingItem));
            total += shoppingItem.getTotal();
        }
        totalprisLabel.setText("Totalt: " + backend.getProductPrice(total) + " kr");
        this.currentlyShowingOrder = order;
        enableAddButton();
    }
    
    public void addItemsFromHistory() {
        if (currentlyShowingOrder != null) {
            for (ShoppingItem shoppingItem : currentlyShowingOrder.getItems()) {
                for (ItemCard itemCard : parentController.shoppingController.itemCards) {
                    if (itemCard.getProduct().equals(shoppingItem.getProduct())) {
                        itemCard.add(shoppingItem.getAmount());
                    }
                }
            }
            disableAddButton();
        }
    }
    
    private void disableAddButton() {
    
        addItemsToCart.getStyleClass().removeAll("add_items_to_cart_button");
        addItemsToCart.getStyleClass().removeAll("add_items_to_cart_button_disabled");
        addItemsToCart.getStyleClass().add("add_items_to_cart_button_disabled");
        addItemsToCart.setMouseTransparent(true);
        addItemsToCart.setText("Varor tillagda!");
    }
    
    private void enableAddButton() {
        addItemsToCart.getStyleClass().removeAll("add_items_to_cart_button");
        addItemsToCart.getStyleClass().removeAll("add_items_to_cart_button_disabled");
        addItemsToCart.getStyleClass().add("add_items_to_cart_button");
        addItemsToCart.setMouseTransparent(false);
        addItemsToCart.setText("Lägg till i varukorgen");
    }
    
    
}
