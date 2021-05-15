package sample;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class BackendController {
    
    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    
    /**
     * Gets the FXImage from backend
     * @param product
     * @return Image of product
     */
    public Image getProductImage(Product product) {
        return dataHandler.getFXImage(product);
    }
    
    /**
     * Edits the price to correct form.
     * before: 25.0
     * after: 25:-
     * @param product
     * @return converted price as String
     */
    public String getProductPrice(Product product) {
        String price;
        double val = product.getPrice();
        String[] arr = String.valueOf(val).split("\\.");
        int kr = Integer.parseInt(arr[0]);
        int ore = Integer.parseInt(arr[1]);
        
        if (ore == 0) {
            price = kr + ":-";
        }
        else {
            if (ore < 10) {
                ore *= 10;
            }
            price = kr + ":" + ore;
        }
        return price;
    }
    
    public String getCorrectFormatPrice(Product product) {
        String correct = getProductPrice(product) + " / " + product.getUnitSuffix();
        return correct;
    }
    
}
