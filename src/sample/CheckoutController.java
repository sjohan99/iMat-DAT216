package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    private UIController parentController;
    public BackendController backend = new BackendController();

    @FXML AnchorPane personalInfoAnchorPane, cardDetailsAnchorPane, deliveryTimeAnchorPane, adressAnchorPane, confirmOrderAnchorPane;
    @FXML Button nextStepButton1, backButton1, nextStepButton2, backButton2, nextStepButton3, backButton3, nextStepButton4, backButton4, backButton5;
    public CheckoutController(UIController parentController) { this.parentController = parentController; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();
    }

    private void initButtons() {
        List<Button> buttons = new ArrayList<>();
        nextStepButton1.setId("next_step1");
        backButton1.setId("back1");
        nextStepButton2.setId("next_step2");
        backButton2.setId("back2");
        nextStepButton3.setId("next_step3");
        backButton3.setId("back3");
        nextStepButton4.setId("next_step4");
        backButton4.setId("back4");
        backButton5.setId("back5");


        buttons.add(nextStepButton1);
        buttons.add(backButton1);
        buttons.add(nextStepButton2);
        buttons.add(backButton2);
        buttons.add(nextStepButton3);
        buttons.add(backButton3);
        buttons.add(nextStepButton4);
        buttons.add(backButton4);
        buttons.add(backButton5);

        for (Button button : buttons) { button.setOnAction(e -> changeStep(e)); }

    }

    private void changeStep(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        checkoutChangeWindow(id);
    }

    public void checkoutChangeWindow(String id) {
        switch(id) {
            case "next_step1":
                adressAnchorPane.toFront();
                break;
            case "back1":
                //TODO
                break;
            case "next_step2":
                deliveryTimeAnchorPane.toFront();
                break;
            case "back2":
                personalInfoAnchorPane.toFront();
                break;
            case "next_step3":
                cardDetailsAnchorPane.toFront();
                break;
            case "back3":
                adressAnchorPane.toFront();
                break;
            case "next_step4":
                confirmOrderAnchorPane.toFront();
                break;
            case "back4":
                deliveryTimeAnchorPane.toFront();
                break;
            case "back5":
                cardDetailsAnchorPane.toFront();
        }
    }


}
