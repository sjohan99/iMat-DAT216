package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    SideMenus sideMenus;
    
    @FXML Label dateLabel;
    @FXML AnchorPane historyMenuButtonPane;
    public HistoryMenuButton(Order order, HistoryController historyController, SideMenus sideMenus) {
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
        this.sideMenus = sideMenus;
        dateLabel.setText(backend.getCorrectDateFormat(order));
    }
    
    public void showOrder() {
        historyParentController.historyItemPane.toFront();
        historyParentController.populateHistoryOrder(order);
        for (Node node : sideMenus.historyButtonList) {
            node.getStyleClass().removeAll("sidebar_button");
            node.getStyleClass().removeAll("sidebar_button_pressed");
            node.getStyleClass().add("sidebar_button");
        }
        historyMenuButtonPane.getStyleClass().removeAll("sidebar_button");
        historyMenuButtonPane.getStyleClass().removeAll("sidebar_button_pressed");
        historyMenuButtonPane.getStyleClass().add("sidebar_button_pressed");
    }
    
}
