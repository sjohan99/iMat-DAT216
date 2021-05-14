package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CartItem extends AnchorPane {
    
    /**
     * Keeping the amount and name of the item as one Label, just construct correct string before creating CartItem.
     */
    @FXML Label itemAmountAndName;
    
    private UIController parentController;
    
    public CartItem(UIController parentController, String text) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.parentController = parentController;
        
        itemAmountAndName.setText(text);
    }
    
    /**
     * Changes the size of the item-name-label to remove "..." (ellipsis) at the end.
     */
    public void resizeNameLabel() {
        int expasionSize = 300;
        if (itemAmountAndName.getMaxWidth() == expasionSize) {
            itemAmountAndName.setMaxWidth(240);
        }
        else {
            itemAmountAndName.setMaxWidth(expasionSize);
        }
    }
}
