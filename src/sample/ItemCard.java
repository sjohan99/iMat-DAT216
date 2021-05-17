package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;


import java.io.IOException;

/**
 * Item card that shows the product which customers purchase.
 */
public class ItemCard extends AnchorPane {
    
    @FXML private ImageView itemImage;
    @FXML private Label itemCost, itemUnit, itemAmount;
    @FXML private Text itemNameText;
    @FXML private TextField itemAmountTextField;
    @FXML private Circle itemAddButton, itemRemoveButton;
    
    private UIController parentController;
    private BackendController backend;
    private Product product;
    private double amount;
    
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
    }
    
    public String getCategory() {
        return product.getCategory().toString();
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void add() {
        amount += 1;
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
