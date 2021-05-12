package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for "mina sidor"
 */
public class MyPagesController implements Initializable {
    
    public BackendController backend = new BackendController();
    
    @FXML TextField adressTextField, apartmentNumTextField, postNumTextField, postalAreaTextField;
    @FXML TextField cardNumberTextField, securityCodeTextField;
    @FXML TextField firstNameTextField, surnameTextField, emailTextField, phoneTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
