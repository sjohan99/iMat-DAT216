package sample;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BackendController {
    
    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart = dataHandler.getShoppingCart();

    public List<Order> getOrdersSorted() {
        List<Order> orderList = new ArrayList<>();
        orderList = dataHandler.getOrders();
        Collections.sort(orderList, new Comparator<Order>() {
            public int compare(Order order, Order t1) {
                return order.getDate().compareTo(t1.getDate());
            }
        });
        Collections.reverse(orderList);
        return orderList;
    }

    public CartItem createFinalShoppingCartItem(Product product, double amount, UIController parentcontroller, int backgroundCount) {
        if (product.getUnitSuffix().equals("kg")) {
            parentcontroller.varor += 1;
            return new CartItem(amount + " kg " + product.getName(), product, parentcontroller, amount, backgroundCount++);
        }
        else {
            int intAmount = (int) amount;
            parentcontroller.varor += intAmount;
            return new CartItem(intAmount + " st " + product.getName(), product, parentcontroller, amount, backgroundCount++);
        }
    }
    
    public double roundTwoDecimals(double a) {
        return Math.round(a * 100.0) / 100.0;
    }
    
    public String getCorrectDateFormat(Order order) {
        int year = order.getDate().getYear() + 1900;
        int month = order.getDate().getMonth() + 1;
        int day = order.getDate().getDate();
        
        String yearString = String.valueOf(year);
        String monthString = String.valueOf(month);
        if (month < 10) {
            monthString = "0" + String.valueOf(month);
        }
        String dayString = String.valueOf(day);
        if (day < 10) {
            dayString = "0" + String.valueOf(day);
        }
        return yearString + "-" + monthString + "-" + dayString;
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
        if (arr[1].length() > 1) {
            arr[1] = arr[1].substring(0,2);
        }
        
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
    
    public String getProductPrice(double number) {
        String price;
        double val = number;
        String[] arr = String.valueOf(val).split("\\.");
        if (arr[1].length() > 1) {
            arr[1] = arr[1].substring(0,2);
        }
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
    }
}
