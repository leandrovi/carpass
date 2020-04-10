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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.VehicleService;

/**
 *
 * @author leandro
 */
public class DashboardClientController implements Initializable {
    
    @FXML
    private MenuItem menuItemVehicles;
    
    @FXML
    private MenuItem menuItemAppointments;
    
    @FXML
    private MenuItem menuItemProviders;
    
    @FXML
    private MenuItem menuItemAbout;

    @FXML 
    public void onMenuItemVehiclesAction() {
        loadView2("/gui/VehicleList.fxml");
    }

    @FXML 
    public void onMenuItemAppointmentsAction() {
        System.out.println("onMenuItemAppointmentsAction");
    }

    @FXML 
    public void onMenuItemProvidersAction() {
        System.out.println("onMenuItemProvidersAction");
    }

    @FXML 
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml");
    }
    
    @Override
    public void initialize(URL uri, ResourceBundle rb) { }
    
    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            
            Scene mainScene = Main.getMainScene();
            VBox dashboardClientVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            Node dashbaordClientMenu = dashboardClientVBox.getChildren().get(0);
            dashboardClientVBox.getChildren().clear();
            dashboardClientVBox.getChildren().add(dashbaordClientMenu);
            dashboardClientVBox.getChildren().addAll(newVBox.getChildren());
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
    
    private synchronized void loadView2(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            
            Scene mainScene = Main.getMainScene();
            VBox dashboardClientVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            
            Node dashbaordClientMenu = dashboardClientVBox.getChildren().get(0);
            dashboardClientVBox.getChildren().clear();
            dashboardClientVBox.getChildren().add(dashbaordClientMenu);
            dashboardClientVBox.getChildren().addAll(newVBox.getChildren());
            
            VehicleListController controller = loader.getController();
            controller.setVehicleService(new VehicleService());
            controller.updateTableView();
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
    
}
