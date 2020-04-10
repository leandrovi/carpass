/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Vehicle;

/**
 *
 * @author leandro
 */
public class VehicleListController implements Initializable {
    
    @FXML
    private TableView<Vehicle> tableViewVehicle;
    
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnId;
    
    @FXML
    private TableColumn<Vehicle, String> tableColumnPlate;
    
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnBrand;
    
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnModel;
    
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnYear;
    
    @FXML
    private TableColumn<Vehicle, String> tableColumnRenavam;
    
    @FXML
    private TableColumn<Vehicle, String> tableColumnColor;
    
    @FXML
    private Button btNew;
    
    @FXML
    public void onBtNewAction() {
        System.out.println("onBtNewAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle db) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        tableColumnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tableColumnModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tableColumnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableColumnRenavam.setCellValueFactory(new PropertyValueFactory<>("renavam"));
        tableColumnColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewVehicle.prefHeightProperty().bind(stage.heightProperty());
    }
    
}
