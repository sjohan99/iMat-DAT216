package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    
    BackendController backend = new BackendController();
    
    @FXML public Button shoppingButton, historyButton, myPagesButton, helpButton;
    @FXML private Label iMat;
    @FXML private ImageView testimage;
    
    private ToggleGroup menuToggleGroup;
    private TopMenuBarButtons topMenuBarButtons;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTopBar();
        initMenuButtons();
    }
    
    private void initTopBar() {
        shoppingButton.setOnAction(event -> {
            shoppingButton.getStyleClass().add(".round_button_toggled");
        });
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
