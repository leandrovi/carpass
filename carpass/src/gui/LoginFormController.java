/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.services.ClientService;
import model.services.ProviderService;

/**
 *
 * @author leandro
 */
public class LoginFormController implements Initializable { 
    
    private ClientService clientService;
   
    private ProviderService providerService;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtPassword;
    
    @FXML
    private Label labelErrorEmail;
    
    @FXML
    private Label labelErrorPassword;
    
    @FXML
    private Button btLogin;
    
    @FXML
    private Button btNewAccount;
    
    @FXML
    public void onBtLoginAction(ActionEvent event) {  
        try {      
        Stage stage = Main.getPrimaryStage();
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/DashboardClient.fxml"));
        ScrollPane scrollPane = loader.load();

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        stage.getScene().setRoot(scrollPane);
        stage.setTitle("Dashboard do Cliente");
        stage.show();                   
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onBtNewAccountAction(ActionEvent event) {
        loadView("/gui/RegistrationForm.fxml", (RegistrationFormController controller) -> {
            controller.setServices(new ClientService(), new ProviderService());
        });
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) { }
    
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            
            Scene mainScene = Main.getMainScene();
            VBox loginFormVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            loginFormVBox.getChildren().clear();
            loginFormVBox.getChildren().addAll(newVBox.getChildren());
            
            T controller = loader.getController();
            initializingAction.accept(controller);            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
}
