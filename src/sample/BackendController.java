package sample;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

public class BackendController {
    
    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    
    public CartItem createFinalShoppingCartItem(Product product, double amount) {
        if (product.getUnitSuffix().equals("kg")) {
            return new CartItem(amount + " kg " + product.getName());
        }
        else {
            int intAmount = (int) amount;
            return new CartItem(intAmount + "x " + product.getName());
        }
    }
    
    
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
    
    public void addItemToShoppingCart(Product product, double amount) {
        if (amount < 0.01) {
            shoppingCart.getItems().removeIf(existingItems -> existingItems.getProduct().equals(product));
            return;
        }
        for (ShoppingItem existingItems : shoppingCart.getItems()) {
            if (existingItems.getProduct().equals(product)) {
                existingItems.setAmount(amount);
                return;
            }
        }
        ShoppingItem shoppingItem = new ShoppingItem(product);
        shoppingItem.setAmount(amount);
        shoppingCart.addItem(shoppingItem);
        System.out.println(shoppingCart.getItems());
    }
    
}
