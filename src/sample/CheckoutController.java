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
    SideMenus sideMenus;

    @FXML AnchorPane personalInfoAnchorPane, cardDetailsAnchorPane, deliveryTimeAnchorPane, adressAnchorPane, confirmCartAnchorPane, confirmOrderAnchorPane;
    @FXML Button nextStepButton1, backButton1, nextStepButton2, backButton2, nextStepButton3, backButton3, nextStepButton4, backButton4, backButton5, backToShoppingButton, confirmButton1, confirmButton2;
    public CheckoutController(UIController parentController, SideMenus sideMenus) {
        this.parentController = parentController;
        this.sideMenus = sideMenus;
    }

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
        confirmButton1.setId("back2");
        confirmButton2.setId("confirm");

        backToShoppingButton.setId("shopping_button");
        backToShoppingButton.setOnAction(e -> parentController.toggleOnButton(e));

        buttons.add(nextStepButton1);
        buttons.add(backButton1);
        buttons.add(nextStepButton2);
        buttons.add(backButton2);
        buttons.add(nextStepButton3);
        buttons.add(backButton3);
        buttons.add(nextStepButton4);
        buttons.add(backButton4);
        buttons.add(backButton5);
        buttons.add(confirmButton1);
        buttons.add(confirmButton2);

        for (Button button : buttons) { button.setOnAction(e -> changeStep(e)); }

    }

    private void changeStep(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        checkoutChangeWindow(id);
    }

    public void checkoutChangeWindow(String id) {
        switch(id) {
            case "next_step1":
            case "back3":
                adressAnchorPane.toFront();
                sideMenus.changeIcon(2);
                break;
            case "back1":
                confirmCartAnchorPane.toFront();
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton1");
                sideMenus.changeIcon(0);
                break;
            case "next_step2":
            case "back4":
                deliveryTimeAnchorPane.toFront();
                sideMenus.changeIcon(3);
                break;
            case "back2":
                personalInfoAnchorPane.toFront();
                sideMenus.changeIcon(1);
                break;
            case "next_step3":
            case "back5":
                cardDetailsAnchorPane.toFront();
                sideMenus.changeIcon(4);
                break;
            case "next_step4":
                confirmOrderAnchorPane.toFront();
                sideMenus.changeIcon(5);
                break;
            case "confirm":
                parentController.changeMainView("shopping_button");
                parentController.purchaseDonePane.toFront();
                sideMenus.changeIcon(6);
                break;
        }
    }


}
