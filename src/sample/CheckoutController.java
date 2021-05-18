package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML TextField adressTextField, postNumTextField, postalAreaTextField;
    @FXML TextField cardNumberTextField, securityCodeTextField;
    @FXML TextField firstNameTextField, surnameTextField, emailTextField, phoneTextField;
    @FXML Label adressLabel, dateLabel, priceLabel;
    public CheckoutController(UIController parentController, SideMenus sideMenus) {
        this.parentController = parentController;
        this.sideMenus = sideMenus;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();
        initMyPagesTextFields();
        initMyPagesTextFieldListeners();
    }

    /**
     * Gets the saved information from backend and loads it upon starting the program
     */
    private void initMyPagesTextFields() {
        adressTextField.setText(backend.dataHandler.getCustomer().getAddress());
        postNumTextField.setText(backend.dataHandler.getCustomer().getPostCode());
        postalAreaTextField.setText(backend.dataHandler.getCustomer().getPostAddress());
        cardNumberTextField.setText(backend.dataHandler.getCreditCard().getCardNumber());
        securityCodeTextField.setText(String.valueOf(backend.dataHandler.getCreditCard().getVerificationCode()));
        firstNameTextField.setText(backend.dataHandler.getCustomer().getFirstName());
        surnameTextField.setText(backend.dataHandler.getCustomer().getLastName());
        emailTextField.setText(backend.dataHandler.getCustomer().getEmail());
        phoneTextField.setText(backend.dataHandler.getCustomer().getPhoneNumber());
        adressLabel.setText(backend.dataHandler.getCustomer().getAddress() + ", " + backend.dataHandler.getCustomer().getPostCode() + " " + backend.dataHandler.getCustomer().getPostAddress());
        dateLabel.setText("12:00-16:00, 2 Maj 2021");
        priceLabel.setText("100 kr");
    }

    /**
     * Updates the information in backend immediately when the text in a textfield is changed
     */
    private void initMyPagesTextFieldListeners() {
        firstNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setFirstName(newValue);
            }
        });

        surnameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setLastName(newValue);
            }
        });

        emailTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setEmail(newValue);
            }
        });

        phoneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setPhoneNumber(newValue);
            }
        });


        adressTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setAddress(newValue);
            }
        });

        postNumTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setPostCode(newValue);
            }
        });

        postalAreaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCustomer().setPostAddress(newValue);
            }
        });


        cardNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCreditCard().setCardNumber(newValue);
            }
        });

        securityCodeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                backend.dataHandler.getCreditCard().setVerificationCode(Integer.parseInt(newValue));
            }
        });

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
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton3", 2);
                break;
            case "back1":
                confirmCartAnchorPane.toFront();
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton1", 0);
                sideMenus.changeIcon(0);
                break;
            case "next_step2":
            case "back4":
                deliveryTimeAnchorPane.toFront();
                sideMenus.changeIcon(3);
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton4", 3);
                break;
            case "back2":
                personalInfoAnchorPane.toFront();
                sideMenus.changeIcon(1);
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton2", 1);
                break;
            case "next_step3":
            case "back5":
                cardDetailsAnchorPane.toFront();
                sideMenus.changeIcon(4);
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton5", 4);
                break;
            case "next_step4":
                confirmOrderAnchorPane.toFront();
                sideMenus.changeIcon(5);
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton6", 5);
                break;
            case "confirm":
                parentController.changeMainView("shopping_button");
                parentController.purchaseDonePane.toFront();
                backend.dataHandler.placeOrder();
                parentController.sideMenus.updateHistory();
                sideMenus.changeIcon(6);
                sideMenus.checkoutButton6.getStyleClass().removeAll("sidebar_button_pressed");
                sideMenus.checkoutButton6.getStyleClass().add("sidebar_buttonNH");
                parentController.adressLabel.setText(backend.dataHandler.getCustomer().getAddress() + ", " + backend.dataHandler.getCustomer().getPostCode() + " " + backend.dataHandler.getCustomer().getPostAddress());
                System.out.println(backend.dataHandler.getCustomer().getPostCode());
                break;
        }
    }


}
