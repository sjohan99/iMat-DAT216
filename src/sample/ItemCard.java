package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.io.IOException;

/**
 * Item card that shows the product which customers purchase.
 */
public class ItemCard extends AnchorPane {
    
    @FXML private ImageView itemImage;
    @FXML public Label itemCost, itemUnit, itemAmount, cardUnitSuffix;
    @FXML private Text itemNameText;
    @FXML public TextField cardAmountTextField, itemAmountTextField;
    @FXML private Circle itemAddButton, itemRemoveButton;
    @FXML private Rectangle boxRectangle;
    
    private UIController parentController;
    private BackendController backend;
    private Product product;
    public double amount;
    public double numberOfItems;
    
    public ItemCard(Product product, UIController parentController, BackendController backend) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("itemCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.product = product;
        this.backend = backend;
        this.parentController = parentController;
        
        itemImage.setImage(backend.getProductImage(product));
        itemNameText.setText(product.getName());
        itemCost.setText(backend.getCorrectFormatPrice(product));
        //itemUnit.setText(product.getUnitSuffix());
        // TODO item amount probably needs to be a textfield/area
        initTextField();
        this.amount = 0;
        setCardUnitSuffix();
    }
    
    private void setCardUnitSuffix() {
        if (product.getUnitSuffix().equals("kg")) {
            cardUnitSuffix.setText("kg");
            numberOfItems = 1;
        }
        else {
            cardUnitSuffix.setText("st");
        }
    }
    
    public String getCategory() {
        return product.getCategory().toString();
    }
    
    public Product getProduct() {
        return product;
    }
    
    private void initTextField() {
        boxRectangle.visibleProperty().bind(cardAmountTextField.hoverProperty());
        
        cardAmountTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                newInputInTextField();
                parentController.updateItemCardAmounts();
            }
        });
        
        cardAmountTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
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
                    parentController.updateItemCardAmounts();
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
            }
        }
        try {
            if (tooManyDecimals(cardAmountTextField.getText())) {
                cardAmountTextField.setText(limitDecimals(cardAmountTextField.getText()));
            }
            amount = Double.parseDouble(cardAmountTextField.getText());
            amountText = cardAmountTextField.getText();
        } catch (NumberFormatException e) {
            System.out.println("Empty string");
            backend.addItemToShoppingCart(product, 0);
            parentController.updateShoppingCart();
            cardAmountTextField.setText("0");
            this.amount = 0;
            return;
        }
        this.amount = amount;
        cardAmountTextField.setText(amountText);
        backend.addItemToShoppingCart(product, amount);
        parentController.updateShoppingCart();
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
    
    public void add() {
        amount += 1;
        backend.addItemToShoppingCart(product, amount);
        parentController.updateShoppingCart();
    }
    
    public void add(double amountToBeAdded) {
        amount += amountToBeAdded;
        backend.addItemToShoppingCart(product, amount);
        parentController.updateShoppingCart();
    }
    
    public void remove() {
        amount -= 1;
        if (amount < 0) {
            amount = 0;
        }
        backend.addItemToShoppingCart(product, amount);
        parentController.updateShoppingCart();
    }
}
