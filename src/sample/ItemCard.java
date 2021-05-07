package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;


import java.io.IOException;

/**
 * Item card that shows the product which customers purchase.
 */
public class ItemCard extends AnchorPane {
    
    @FXML private ImageView itemImage;
    @FXML private Label itemName, itemCost, itemUnit, itemAmount;
    
    private UIController parentController;
    private BackendController backend;
    private Product product;
    
    public ItemCard(Product product, UIController uiController, BackendController backend) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("itemcard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.product = product;
        this.backend = backend;
        
        itemImage.setImage(backend.getProductImage(product));
        itemName.setText(product.getName());
        itemCost.setText(backend.getProductPrice(product));
        itemUnit.setText(product.getUnitSuffix());
        // TODO item amount probably needs to be a textfield/area
    }
    
    
    
}
