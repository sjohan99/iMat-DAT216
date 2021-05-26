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
    @FXML Label itemAmountAndName, priceLabel, unitSuffixLabel;
    @FXML TextField amountTextField;
    @FXML AnchorPane backgroundAnchorPane;
    
    private BackendController backend = new BackendController();
    private UIController parentController;
    private Product product;
    
    public CartItem(String text, Product product, UIController parentController, double amount, int background) {
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
        priceLabel.setText(backend.getProductPrice(product.getPrice() * amount));
        if (product.getUnitSuffix().equals("kg")) {
            unitSuffixLabel.setText("kg");
            amountTextField.setText(String.valueOf(amount));
        }
        else {
            unitSuffixLabel.setText("st");
            amountTextField.setText(String.valueOf((int) amount));
        }
        if (background % 2 == 0) {
            backgroundAnchorPane.setStyle("-fx-background-color: #FFFFFF;");
        }
        if (parentController.shoppingCartExpanded) {
            itemAmountAndName.setMaxWidth(270);
        }
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
                    if (tooManyDecimals(amountTextField.getText())) {
                        amountTextField.setText(limitDecimals(amountTextField.getText()));
                    }
                    amount = Double.parseDouble(amountTextField.getText());
                    amountText = amountTextField.getText();
                } catch (NumberFormatException e) {
                    System.out.println("Empty string");
                }
            }
        }
        backend.addItemToShoppingCart(product, amount);
        parentController.updateShoppingCart();
        parentController.updateItemCardAmounts();
    }
    
    private boolean tooManyDecimals(String input) {
        if (input.contains(".")) {
            double val = Double.parseDouble(input);
            String[] arr = String.valueOf(val).split("\\.");
            if (arr[1].length() > 2) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    private String limitDecimals(String input) {
        if (input.contains(".")) {
            double val = Double.parseDouble(input);
            String[] arr = String.valueOf(val).split("\\.");
            if (arr[1].length() > 1) {
                arr[1] = arr[1].substring(0,2);
            }
            
            String integers = arr[0];
            String decimals = arr[1];
            String result = integers + "." + decimals;
            return result;
        }
        return null;
    }
    
    /**
     * Changes the size of the item-name-label to remove "..." (ellipsis) at the end.
     */
    public void resizeNameLabel() {
        int expasionSize = 270;
        if (itemAmountAndName.getMaxWidth() == expasionSize) {
            itemAmountAndName.setMaxWidth(250);
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
        parentController.updateShoppingCart();
        parentController.updateItemCardAmounts();
    }
    
    public void removeFromCart() {
        double amountToBeRemoved = 0;
        for (ShoppingItem existingItems : backend.shoppingCart.getItems()) {
            if (existingItems.getProduct().equals(product)) {
                amountToBeRemoved = existingItems.getAmount() - 1;
            }
        }
        parentController.updateItemCardAmounts();
        backend.addItemToShoppingCart(product, amountToBeRemoved);
        amountTextField.setText(String.valueOf(amountToBeRemoved));
        parentController.updateShoppingCart();
    }
}
