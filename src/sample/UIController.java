package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;


import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    
    public BackendController backend = new BackendController();
    public MyPagesController myPagesController = new MyPagesController(this);
    public HistoryController historyController = new HistoryController(this);
    private AnimationTimer timer = new MyTimer();
    
    @FXML public Button shoppingButton, historyButton, myPagesButton, helpButton, expandButton;
    @FXML private Label iMat;
    @FXML private ImageView testimage;
    @FXML private VBox shoppingCart;
    @FXML private FlowPane shoppingCartPane;
    @FXML public AnchorPane sideMenuParentAnchorPane, parentView, shoppingCartAnchorPane;
    @FXML private Line cartLineDivider;
    @FXML private ScrollPane shoppingCartScrollPane;
    
    private TopMenuBarButtons topMenuBarButtons;
    private SideMenus sideMenus = new SideMenus(this);
    private boolean shoppingCartExpanded;

    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCartExpanded = false;
        initMenuButtons();
        addPlaceholderCartItems();
        addSideMenus();
        addMainViews();
        shoppingCartScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
    
    
    /**
     * Method is called when expanding shopping cart.
     * Resizes necessary elements and changes the button-text.
     */
    public void expandShoppingCart() {
        if (shoppingCartAnchorPane.getMaxWidth() < 500) {
            shoppingCartExpanded = false;
            //shoppingCartAnchorPane.setMaxWidth(500); uncomment for instant expansion
            timer.start();
            cartLineDivider.setEndX(cartLineDivider.getEndX() + 225);
            expandButton.setText("Minska varukorg");
        }
        else {
            shoppingCartExpanded = true;
            //shoppingCartAnchorPane.setMaxWidth(275);
            timer.start();
            cartLineDivider.setEndX(245);
            expandButton.setText("Ändra i varukorgen");
        }
        for (Node item : shoppingCartPane.getChildren()) {
            ((CartItem) item).resizeNameLabel();
        }
    }
    
    private void addMainViews() {
        parentView.getChildren().clear();
        parentView.getChildren().add(new MyPages(myPagesController));
        //parentView.getChildren().add(new History(historyController)); // uncomment to test historyView
    }

    private void addSideMenus() {
        sideMenuParentAnchorPane.getChildren().clear();
        sideMenuParentAnchorPane.getChildren().add(sideMenus);
    }
    
    /**
     * Placeholder method which just adds fake items in the shopping cart. Gives a bunch of processValue errors
     * (atleast for me) shouldn't do any harm though
     */
    private void addPlaceholderCartItems() {
        shoppingCartPane.getChildren().clear(); // Have to do this even if it's empty at launch for unknown reason
        shoppingCartPane.getChildren().add(new CartItem(this, "1x Långt ooooooooord"));
        for (int i = 0; i < 16; i++) {
            shoppingCartPane.getChildren().add(new CartItem(this, i + "x Test Item"));
        }
    }
    
    /**
     * Initializes buttons to be "toggled" by setting their id's (has to be the same as their css id
     * to not remove their individual styling) and adding an actionevent which reads the id of the pressed button.
     */
    private void initMenuButtons() {
        topMenuBarButtons = new TopMenuBarButtons();
        topMenuBarButtons.addButtonToList(shoppingButton);
        topMenuBarButtons.addButtonToList(helpButton);
        topMenuBarButtons.addButtonToList(historyButton);
        topMenuBarButtons.addButtonToList(myPagesButton);
        
        shoppingButton.setId("shopping_button");
        helpButton.setId("help_button");
        historyButton.setId("history_button");
        myPagesButton.setId("my_pages_button");
        
        for (Button button : topMenuBarButtons.getButtons()) {
            button.setOnAction(e -> toggleOnButton(e));
        }
    }
    
    /**
     * Reads the id of the pressed button and sends it topMenuBarButtons to handle the rest. Also tells SideMenus to update view
     * @param e triggered event
     */
    public void toggleOnButton(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        topMenuBarButtons.activate(id);
        sideMenus.changeSideMenu(id);
    }
    
    /**
     * Animation timer for sliding out the cart when expanded.
     */
    private class MyTimer extends AnimationTimer {
    
        double speed = 20;
        
        @Override
        public void handle(long l) {
            slideOut();
        }
    
        /**
         * Method is called every frame, starts with high speed then slows down at the end
         */
        private void slideOut() {
            if (!shoppingCartExpanded) {
                if (speed >= 3) {
                    speed -= 1;
                }
    
                if (shoppingCartAnchorPane.getMaxWidth() > 485) {
                    speed = 1;
                }
                shoppingCartAnchorPane.setMaxWidth(shoppingCartAnchorPane.getMaxWidth() + speed);
    
                if (shoppingCartAnchorPane.getMaxWidth() >= 500) {
                    stop();
                    speed = 20;
                    shoppingCartAnchorPane.setMaxWidth(500);
                }
            }
            else {
                if (speed >= 3) {
                    speed -= 1;
                }
    
                if (shoppingCartAnchorPane.getMaxWidth() < 290) {
                    speed = 1;
                }
                shoppingCartAnchorPane.setMaxWidth(shoppingCartAnchorPane.getMaxWidth() - speed);
                
    
                if (shoppingCartAnchorPane.getMaxWidth() <= 275) {
                    stop();
                    speed = 20;
                    shoppingCartAnchorPane.setMaxWidth(275);
                }
            }
        }
    }
}
