/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Vehicle;
import model.services.VehicleService;

/**
 *
 * @author leandro
 */
public class VehicleListController implements Initializable {
    
    private VehicleService service;
    
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
    
    private ObservableList<Vehicle> obsList;
    
    @FXML
    public void onBtNewAction() {
        System.out.println("onBtNewAction");
    }
    
    // Dependency injection
    public void setVehicleService(VehicleService service) {
        this.service = service;
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
    
    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        
        List<Vehicle> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        
        tableViewVehicle.setItems(obsList);
    }
    
}
