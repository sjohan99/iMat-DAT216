package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for "mina sidor"
 */
public class MyPagesController implements Initializable {
    
    private UIController parentController;
    public BackendController backend = new BackendController();
    
    @FXML TextField adressTextField, apartmentNumTextField, postNumTextField, postalAreaTextField;
    @FXML TextField cardNumberTextField, securityCodeTextField, cardMonthTextField, cardYearTextField;
    @FXML TextField firstNameTextField, surnameTextField, emailTextField, phoneTextField;
    @FXML AnchorPane personalInfoAnchorPane, cardDetailsAnchorPane, deliveryAnchorPane;
    
    public MyPagesController(UIController parentController) {
        this.parentController = parentController;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMyPagesTextFields();
        initMyPagesTextFieldListeners();
    }
    
    public void myPagesChangeWindow(String id) {
        switch(id) {
            case "personal_info":
                personalInfoAnchorPane.toFront();
                break;
            case "card_details":
                cardDetailsAnchorPane.toFront();
                break;
            case "delivery_details":
                deliveryAnchorPane.toFront();
                break;
        }
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
    }
    
    /**
     * Updates the information in backend immediately when the text in a textfield is changed
     */
    private void initMyPagesTextFieldListeners() {
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
                backend.dataHandler.getCreditCard().setVerificationCode(Integer.parseInt(newValue));
            }
        });
        
    }
}
