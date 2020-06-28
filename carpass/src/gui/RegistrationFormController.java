/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import gui.util.Alerts;
import gui.util.Constraints;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Client;
import model.entities.User;
import model.services.ClientService;
import model.services.ProviderService;

/**
 *
 * @author leandro
 */
public class RegistrationFormController implements Initializable { 
    
    private User entity;
    
    private ClientService clientService;
   
    private ProviderService providerService;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private CheckBox cbProvider;
    
    @FXML
    private Label labelErrorEmail;
    
    @FXML
    private Label labelErrorPassword;
    
    @FXML
    private Button btCreateNewAccount;
    
    @FXML
    private Button btCancel;
    
    public void setUser(User entity) {
        this.entity = entity;
    }
    
    public void setServices(
        ClientService clientService,
        ProviderService providerService
    ) {
        this.clientService = clientService;
        this.providerService = providerService;
    }
    
    @FXML
    public void onBtCreateNewAccountAction(ActionEvent event) {
        System.out.println("onBtCreateNewAccountAction");
    }
    
    @FXML
    public void onBtCancelAction(ActionEvent event) {
        System.out.println("onBtCancelAction");
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldMaxLength(txtName, 45);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Constraints.setTextFieldMaxLength(txtPassword, 100);
        
        Stage stage = (Stage) Main.getMainScene().getWindow();
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        
        if (fields.contains("email")) {
            labelErrorEmail.setText(errors.get("email"));
        }
        
        if (fields.contains("password")) {
            labelErrorEmail.setText(errors.get("password"));
        }
    }
    
}
