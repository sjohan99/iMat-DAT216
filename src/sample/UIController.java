package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    
    public BackendController backend = new BackendController();
    
    @FXML public Button shoppingButton, historyButton, myPagesButton, helpButton;
    @FXML private Label iMat;
    @FXML private ImageView testimage;
    @FXML private VBox shoppingCart;
    @FXML private FlowPane shoppingCartPane;
    @FXML public AnchorPane sideMenuParentAnchorPane, shopping_menuPane;
    
    private TopMenuBarButtons topMenuBarButtons;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMenuButtons();
        addPlaceholderCartItems();
        addSideMenus();
    }
    
    private void addSideMenus() {
        sideMenuParentAnchorPane.getChildren().clear();
        sideMenuParentAnchorPane.getChildren().add(new SideMenus(this));
    }
    
    /**
     * Placeholder method which just adds fake items in the shopping cart. Gives a bunch of processValue errors
     * (atleast for me) shouldn't do any harm though
     */
    private void addPlaceholderCartItems() {
        shoppingCartPane.getChildren().clear(); // Have to do this even if it's empty at launch for unknown reason
        for (int i = 0; i < 16; i++) {
            shoppingCartPane.getChildren().add(new CartItem(this, i + "x Test item"));
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
     * Reads the id of the pressed button and sends it topMenuBarButtons to handle the rest.
     * @param e triggered event
     */
    public void toggleOnButton(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        topMenuBarButtons.activate(id);
    }
    
    
}
