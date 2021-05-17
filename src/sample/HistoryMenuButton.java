package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Date;

public class HistoryMenuButton extends AnchorPane {
    
    BackendController backend = new BackendController();
    HistoryController historyParentController;
    Order order;
    
    @FXML Label dateLabel;
    
    public HistoryMenuButton(Order order, HistoryController historyController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receipt_menu_button.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.order = order;
        this.historyParentController = historyController;
        dateLabel.setText(backend.getCorrectDateFormat(order));
    }
    
    public void showOrder() {
        historyParentController.populateHistoryOrder(order);
    }
    
}
