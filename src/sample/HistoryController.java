package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML Label totalprisLabel, timeLabel;
    @FXML FlowPane HistoryItemFlowPane;
    
    public HistoryController(UIController parentController) { this.parentController = parentController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyItemPane.toFront();
    }
    
    public void populateHistoryOrder(Order order) {
        timeLabel.setText(backend.getCorrectDateFormat(order));
        HistoryItemFlowPane.getChildren().clear();
        double total = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            HistoryItemFlowPane.getChildren().add(new HistoryItem(shoppingItem));
            total += shoppingItem.getTotal();
        }
        totalprisLabel.setText("Totalt: " + String.valueOf(total) + " kr");
    }
}
