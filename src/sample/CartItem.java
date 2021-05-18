package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class CartItem extends AnchorPane {
    
    /**
     * Keeping the amount and name of the item as one Label, just construct correct string before creating CartItem.
     */
    @FXML Label itemAmountAndName;
    @FXML TextField amountTextField;
    
    private BackendController backend = new BackendController();
    private UIController parentController;
    private Product product;
    
    public CartItem(String text, Product product, UIController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.parentController = parentController;
        this.product = product;
        itemAmountAndName.setText(text);
        initTextField();
    }
    
    private void initTextField() {
        amountTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                newInputInTextField();
            }
        });
        
        amountTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue)
                {
                
                }
                else
                {
                    newInputInTextField();
                }
            }
        });
    }
    
    private void newInputInTextField() {
        String amountText = "";
        double amount = 0;
        for (ShoppingItem existingItems : backend.shoppingCart.getItems()) {
            if (existingItems.getProduct().equals(product)) {
                amount = existingItems.getAmount();
                try {
                    amount = Double.parseDouble(amountTextField.getText());
                    amountText = amountTextField.getText();
                } catch (NumberFormatException e) {
                    System.out.println("Empty string");
                }
            }
        }
        amountTextField.setText(amountText);
        backend.addItemToShoppingCart(product, amount);
        parentController.updateShoppingCart();
        
        if (product.getUnitSuffix().equals("kg")) {
            amountTextField.setText(amountText);
        }
        else {
            amountTextField.setText(amountText);
        }
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
    
    public void addFromCart() {
        double amount = 0;
        for (ShoppingItem existingItems : backend.shoppingCart.getItems()) {
            if (existingItems.getProduct().equals(product)) {
                amount = existingItems.getAmount() + 1;
                backend.addItemToShoppingCart(product, existingItems.getAmount() + 1);
            }
        }
        amountTextField.setText(String.valueOf(amount));
        System.out.println(String.valueOf(amount));
        parentController.updateShoppingCart();
        System.out.println("add called");
    }
    
    public void removeFromCart() {
        double amountToBeRemoved = 0;
        for (ShoppingItem existingItems : backend.shoppingCart.getItems()) {
            if (existingItems.getProduct().equals(product)) {
                amountToBeRemoved = existingItems.getAmount() - 1;
            }
        }
        backend.addItemToShoppingCart(product, amountToBeRemoved);
        amountTextField.setText(String.valueOf(amountToBeRemoved));
        parentController.updateShoppingCart();
        System.out.println("remove called");
    }
}
