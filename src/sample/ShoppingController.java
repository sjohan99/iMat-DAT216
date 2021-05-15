package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {
    
    private UIController parentController;
    private BackendController backend;
    
    @FXML private FlowPane shoppingFlowPane;
    @FXML private ScrollPane shoppingScrollPane;
    @FXML private AnchorPane shoppingAnchorPane;
    @FXML private Label shoppingHeadline;
    
    public ShoppingController(UIController parentController, BackendController backend) {
        this.parentController = parentController;
        this.backend = backend;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    
    }
    
    public void addItems() {
        int count = 0;
        for (Product product : backend.dataHandler.getProducts()) {
            shoppingFlowPane.getChildren().add(new ItemCard(product, parentController, backend));
            if (count++ > 20) {
                break;
            }
        }
    }
}
