package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {
    
    private UIController parentController;
    private BackendController backend;
    public List<ItemCard> itemCards, allItems, mejeriItems, meatItems, skafferiItems, dryckItems,
            ekoItems, fruitItems, snackItems, greensItems, searchItems;
    
    @FXML public FlowPane shoppingFlowPane;
    @FXML private ScrollPane shoppingScrollPane;
    @FXML private AnchorPane shoppingAnchorPane;
    @FXML public Label shoppingHeadline;
    
    public int loadedItems = 12;
    public boolean allItemsLoaded = false;
    public boolean scrollBarInitiated = false;
    public boolean inAllItemsCategory = false;
    
    public ShoppingController(UIController parentController, BackendController backend) {
        this.parentController = parentController;
        this.backend = backend;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        shoppingScrollPane.setStyle("-fx-font-size: 40px;");
        createItemCards();
        allItems = new ArrayList<>();
        mejeriItems = new ArrayList<>();
        meatItems = new ArrayList<>();
        skafferiItems = new ArrayList<>();
        dryckItems = new ArrayList<>();
        ekoItems = new ArrayList<>();
        fruitItems = new ArrayList<>();
        snackItems = new ArrayList<>();
        greensItems = new ArrayList<>();
        searchItems = new ArrayList<>();
        populateItemCards();
        parentController.updateItemCardAmounts();
        addMouseScrollingActivator(shoppingScrollPane);
    }
    
    public void addMouseScrollingActivator(ScrollPane shoppingScrollPane) {
        shoppingScrollPane.setOnScroll((ScrollEvent event) -> {
            initScrollbar();
        });
    }
    
    public void resetScrollbar() {
        loadedItems = 12;
        allItemsLoaded = false;
        resetScrollbarPos();
        shoppingFlowPane.getChildren().clear();
    }
    
    public void resetScrollbarPos() {
        ScrollBar scrollbar = getScrollBar();
        if (scrollbar != null) {
            scrollbar.setValue(scrollbar.getMin());
        }
    }
    
    public ScrollBar getScrollBar() {
        ScrollBar scrollbar = null;
        for (Node node : shoppingScrollPane.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) node;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    scrollbar = bar;
                }
            }
        }
        return scrollbar;
    }
    
    public void initScrollbar() {
        if (!scrollBarInitiated) {
            ScrollBar scrollBar = getScrollBar();
            if (scrollBar != null) {
                scrollBarInitiated = true;
                scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
                    double position = newValue.doubleValue();
                    if (position == scrollBar.getMax()) {
                        loadMoreItems();
                    }
                });
            }
        }
    }
    
    private void loadMoreItems() {
        if (!allItemsLoaded && inAllItemsCategory) {
            if (loadedItems + 12 > 120) {
                addItems(itemCards.subList(loadedItems, itemCards.size()));
                allItemsLoaded = true;
                return;
            }
            try {
                addItems(itemCards.subList(loadedItems, loadedItems + 12));
            } catch (Exception e) {
            
            }
            loadedItems = loadedItems + 12;
        }
    }
   
    
    private void createItemCards() {
        itemCards = new ArrayList<>();
        int i = 1;
        for (Product product : backend.dataHandler.getProducts()) {
            itemCards.add(new ItemCard(product, parentController, backend));
            System.out.println("Creating item card " + i + " out of " + backend.dataHandler.getProducts().size());
            i++;
        }
    }
    
    public void search(String searchInput) {
        searchItems.clear();
        shoppingFlowPane.getChildren().clear();
        for (Product product : backend.dataHandler.findProducts(searchInput)) {
            for (ItemCard itemCard : itemCards) {
                if (itemCard.getProduct().equals(product)) {
                    searchItems.add(itemCard);
                }
            }
        }
        addItems(searchItems);
        shoppingHeadline.setText("Sökresultat för '" + searchInput + "':");
        if (searchInput.equals("")) { shoppingHeadline.setText("Alla"); }
    }
    
    private void populateItemCards() {
        for (ItemCard itemCard : itemCards) {
            allItems.add(itemCard);
            switch (itemCard.getCategory()) {
                case "DAIRIES":
                    mejeriItems.add(itemCard);
                    break;
                case "MEAT":
                case "FISH":
                    meatItems.add(itemCard);
                    break;
                case "VEGETABLE_FRUIT":
                case "CABBAGE":
                case "ROOT_VEGETABLE":
                case "HERB":
                case "FRUIT":
                    greensItems.add(itemCard);
                    break;
                case "BERRY":
                case "CITRUS_FRUIT":
                case "EXOTIC_FRUIT":
                case "MELONS":
                    fruitItems.add(itemCard);
                    break;
                case "SWEET":
                case "NUTS_AND_SEEDS":
                    snackItems.add(itemCard);
                    break;
                case "HOT_DRINKS":
                case "COLD_DRINKS":
                    dryckItems.add(itemCard);
                    break;
                case "POD":
                case "PASTA":
                case "FLOUR_SUGAR_SALT":
                case "POTATO_RICE":
                case "BREAD":
                    skafferiItems.add(itemCard);
            }
        }
    }
    
    
    
    public void ShoppingChangeWindow(String id) {
        switch(id) {
            case "all":
                shoppingFlowPane.getChildren().clear();
                addItems(allItems);
                shoppingHeadline.setText("Alla");
                break;
            case "mejeri":
                shoppingFlowPane.getChildren().clear();
                addItems(mejeriItems);
                shoppingHeadline.setText("Mejeri");
                break;
            case "meat":
                shoppingFlowPane.getChildren().clear();
                addItems(meatItems);
                shoppingHeadline.setText("Kött & Fisk");
                break;
            case "skafferi":
                shoppingFlowPane.getChildren().clear();
                addItems(skafferiItems);
                shoppingHeadline.setText("Skafferi");
                break;
            case "frukt":
                shoppingFlowPane.getChildren().clear();
                addItems(fruitItems);
                shoppingHeadline.setText("Frukt");
                break;
            case "greens":
                shoppingFlowPane.getChildren().clear();
                addItems(greensItems);
                shoppingHeadline.setText("Grönsaker");
                break;
            case "snacks":
                shoppingFlowPane.getChildren().clear();
                addItems(snackItems);
                shoppingHeadline.setText("Snacks");
                break;
            case "dryck":
                shoppingFlowPane.getChildren().clear();
                addItems(dryckItems);
                shoppingHeadline.setText("Dryck");
                break;
            case "eko":
                shoppingFlowPane.getChildren().clear();
                if (ekoItems.size() == 0) {
                    for (Product product : backend.dataHandler.findProducts("ekologi")) {
                        for (ItemCard itemCard : itemCards) {
                            if (itemCard.getProduct().equals(product)) {
                                ekoItems.add(itemCard);
                            }
                        }
                    }
                }
                addItems(ekoItems);
                shoppingHeadline.setText("Ekologiskt");
                break;
        }
        inAllItemsCategory = false;
        resetScrollbarPos();
    }
    
    public void addItems(List<ItemCard> items) {
        for (ItemCard itemCard : items)
            shoppingFlowPane.getChildren().add(itemCard);
        }
}
