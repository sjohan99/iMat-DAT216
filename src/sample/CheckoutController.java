package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    private UIController parentController;
    public BackendController backend = new BackendController();

    @FXML
    AnchorPane personalInfoAnchorPane, cardDetailsAnchorPane, deliveryTimeAnchorPane, adressAnchorPane, confirmOrderAnchorPane;

    public CheckoutController(UIController parentController) { this.parentController = parentController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void checkoutChangeWindow(ActionEvent e, String id) {
        switch(id) {
            case "personal_info":
                personalInfoAnchorPane.toFront();
                break;
            case "card_details":
                cardDetailsAnchorPane.toFront();
                break;
            case "delivery_details":
                deliveryTimeAnchorPane.toFront();
                break;
        }
    }
}
