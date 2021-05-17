package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class HistoryItem extends AnchorPane {
    
    BackendController backend = new BackendController();
    
    @FXML Label itemName, itemAmount, itemPrice, itemCost;
    
    public HistoryItem(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history_labels.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        
        itemName.setText(shoppingItem.getProduct().getName());
        itemAmount.setText(getCorrectAmountFormat(shoppingItem));
        itemPrice.setText(backend.getCorrectFormatPrice(shoppingItem.getProduct()));
        itemCost.setText(String.valueOf(shoppingItem.getTotal()));
    }
    
    private String getCorrectAmountFormat(ShoppingItem shoppingItem) {
        if (shoppingItem.getProduct().getUnitSuffix().equals("kg")) {
            return shoppingItem.getAmount() + " kg";
        }
        else return (int) shoppingItem.getAmount() + " st";
    }
}
