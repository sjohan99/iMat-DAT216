package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    private UIController parentController;
    public BackendController backend = new BackendController();
    private ButtonGrouper dateButtons;
    SideMenus sideMenus;
    List<String> monthName = new ArrayList<>();
    List<String> dayName = new ArrayList<>();
    List<Integer> dayNum = new ArrayList<>();
    @FXML AnchorPane personalInfoAnchorPane, cardDetailsAnchorPane, deliveryTimeAnchorPane, adressAnchorPane, confirmCartAnchorPane, confirmOrderAnchorPane, warningAnchorPane;
    @FXML Button nextStepButton1, backButton1, nextStepButton2, backButton2, nextStepButton3, backButton3, nextStepButton4, backButton4, backButton5, backToShoppingButton, confirmButton1, confirmButton2, dayButton1, dayButton2, dayButton3, timeButton;
    @FXML TextField adressTextField, postNumTextField, postalAreaTextField;
    @FXML TextField cardNumberTextField, securityCodeTextField, cardMonthTextField, cardYearTextField;
    @FXML TextField firstNameTextField, surnameTextField, emailTextField, phoneTextField;
    @FXML Label adressLabel, dateLabel, priceLabel, totalPriceLabel, monthLabel1, monthLabel2, monthLabel3, dayLabel1, dayLabel2, dayLabel3;
    @FXML ScrollPane checkoutScrollPane;
    @FXML FlowPane checkoutItemFlowPane;
    @FXML DatePicker datePicker;
    
    
    public CheckoutController(UIController parentController, SideMenus sideMenus) {
        this.parentController = parentController;
        this.sideMenus = sideMenus;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTime(LocalDate.now());
        initButtons();
        initMyPagesTextFields();
        initMyPagesTextFieldListeners();
        checkoutScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void timeButtons(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        dateButtons.activateDateButtons(id);
    }

    private void updateTime(LocalDate date) {

        for (int i = 0; i < 3; i++) {
            monthName.add(capitalize(date.plusDays(i).getMonth().getDisplayName(TextStyle.FULL, new Locale("sv"))));
            dayNum.add(date.plusDays(i).getDayOfMonth());
            dayName.add(capitalize(date.plusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("sv"))));
        }

        dateLabel.setText("12:00-16:00, " + dayName.get(2) + " " + dayNum.get(2) + " " + monthName.get(2));
        monthLabel1.setText(monthName.get(0));
        monthLabel2.setText(monthName.get(1));
        monthLabel3.setText(monthName.get(2));
        dayButton1.setText(String.valueOf(dayNum.get(0)));
        dayButton2.setText(String.valueOf(dayNum.get(1)));
        dayButton3.setText(String.valueOf(dayNum.get(2)));
        if (date.equals(LocalDate.now())) {
            dayLabel1.setText("Idag");
            dayLabel2.setText("Imorgon");
        } else {
            dayLabel1.setText(dayName.get(0));
            dayLabel2.setText(dayName.get(1));
        }
        dayLabel3.setText(dayName.get(2));

        monthName.clear();
        dayNum.clear();
        dayName.clear();
    }

    public String capitalize(String str) {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void populateItemsToBeBought() {
        checkoutItemFlowPane.getChildren().clear();
        double total = 0;
        for (ShoppingItem shoppingItem : backend.shoppingCart.getItems()) {
            checkoutItemFlowPane.getChildren().add(new HistoryItem(shoppingItem));
            //total += shoppingItem.getTotal();
        }
        System.out.println(backend.shoppingCart.getItems());
        totalPriceLabel.setText("Totalt: " + (backend.getProductPrice(backend.shoppingCart.getTotal())) + " kr");
        
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
        cardMonthTextField.setText(String.valueOf(backend.dataHandler.getCreditCard().getValidMonth()));
        cardYearTextField.setText(String.valueOf(backend.dataHandler.getCreditCard().getValidYear()));
        adressLabel.setText(backend.dataHandler.getCustomer().getAddress() + ", " + backend.dataHandler.getCustomer().getPostCode() + " " + backend.dataHandler.getCustomer().getPostAddress());
        priceLabel.setText(backend.shoppingCart.getTotal() + " kr");
    }

    private boolean isPersonNull() {
        if (firstNameTextField.getText().equals("") || surnameTextField.getText().equals("") || emailTextField.getText().equals("") || phoneTextField.getText().equals("")) { return true; }
        return false;
    }

    private boolean isAdressNull() {
        if (adressTextField.getText().equals("") || postNumTextField.getText().equals("") || postalAreaTextField.getText().equals("")) { return true; }
        return false;
    }

    private boolean isCardNull() {
        if (cardNumberTextField.getText().equals("") || cardMonthTextField.getText().equals("") || cardYearTextField.getText().equals("") || securityCodeTextField.getText().equals("")) { return true; }
        return false;
    }

    /**
     * Updates the information in backend immediately when the text in a textfield is changed
     */
    private void initMyPagesTextFieldListeners() {
        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                updateTime(t1);
            }
        });


        cardYearTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    backend.dataHandler.getCreditCard().setValidYear(Integer.parseInt(newValue));
                } catch (NumberFormatException e) {
                
                }
            }
        });
        
        cardMonthTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    backend.dataHandler.getCreditCard().setValidMonth(Integer.parseInt(newValue));
                } catch (NumberFormatException e) {
                
                }
            }
        });
        
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
                try {
                    backend.dataHandler.getCreditCard().setVerificationCode(Integer.parseInt(newValue));
                } catch (NumberFormatException e) {
                
                }
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

        backToShoppingButton.setId("go_back_to_shopping_button");
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

        dateButtons = new ButtonGrouper();
        dateButtons.addButtonToList(dayButton3);
        dateButtons.addButtonToList(timeButton);


        for (Button button : dateButtons.getButtons()) {
            button.setOnAction(e -> timeButtons(e));
        }
    }

    private void changeStep(ActionEvent e) {
        String id = ((Node) e.getSource()).getId();
        checkoutChangeWindow(id);
    }

    public void checkoutChangeWindow(String id) {
        warningAnchorPane.toBack();
        switch(id) {
            case "next_step1":
            case "back3":
                if (isPersonNull()) { warningAnchorPane.toFront(); break; }
                adressAnchorPane.toFront();
                sideMenus.changeIcon(2);
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton3", 2);
                break;
            case "back1":
                confirmCartAnchorPane.toFront();
                populateItemsToBeBought();
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton1", 0);
                sideMenus.changeIcon(0);
                break;
            case "next_step2":
            case "back4":
                if (isAdressNull()) { warningAnchorPane.toFront(); break; }
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
                if (isCardNull()) { warningAnchorPane.toFront(); break; }
                confirmOrderAnchorPane.toFront();
                sideMenus.changeIcon(5);
                sideMenus.checkoutButtonsGroup.activateCheckoutButtons("checkoutButton6", 5);
                break;
            case "confirm":
                parentController.changeMainView("shopping_button");
                parentController.purchaseDonePane.toFront();
                backend.dataHandler.placeOrder();
                parentController.updateShoppingCart();
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
