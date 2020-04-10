/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import application.Main;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Vehicle;
import model.services.BrandService;
import model.services.ClientService;
import model.services.ModelService;
import model.services.VehicleService;

/**
 *
 * @author leandro
 */
public class VehicleListController implements Initializable, DataChangeListener {
    
    private VehicleService service;
    
    //private BrandService brandService;
    
    //private ModelService brandService;
    
    //private ClientService brandService;
    
    @FXML
    private TableView<Vehicle> tableViewVehicle;
    
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnId;
    
    @FXML
    private TableColumn<Vehicle, String> tableColumnPlate;
    
    //@FXML
    //private TableColumn<Vehicle, Integer> tableColumnBrand;
    
    //@FXML
    //private TableColumn<Vehicle, Integer> tableColumnModel;
    
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnYear;
    
    @FXML
    private TableColumn<Vehicle, String> tableColumnRenavam;
    
    @FXML
    private TableColumn<Vehicle, String> tableColumnColor;
            
    @FXML
    private TableColumn<Vehicle, Vehicle> tableColumnEDIT;
    
    @FXML
    private TableColumn<Vehicle, Vehicle> tableColumnREMOVE;
    
    @FXML
    private Button btNew;
    
    private ObservableList<Vehicle> obsList;
    
    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        
        Vehicle obj = new Vehicle();
        
        createDialogForm(obj, "/gui/VehicleForm.fxml", parentStage);
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
        //tableColumnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        //tableColumnModel.setCellValueFactory(new PropertyValueFactory<>("model"));
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
        
        initEditButtons();
        initRemoveButtons();
    }
    
    private void createDialogForm(Vehicle obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            
            VehicleFormController controller = loader.getController();
            
            controller.setVehicle(obj);
            controller.setServices(
                new VehicleService(),
                new ClientService(),
                new BrandService(),
                new ModelService()
            );
            controller.loadAssociatedObjects();
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();
            
            Stage dialogStage = new Stage();
            
            dialogStage.setTitle("Insira os dados do veículo");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
    
    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Vehicle, Vehicle>() {
            private final Button button = new Button("Editar");
            
            @Override
            protected void updateItem(Vehicle obj, boolean empty) {
                super.updateItem(obj, empty);
                
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                
                setGraphic(button);
                
                button.setOnAction(
                    event -> createDialogForm(
                    obj, "/gui/VehicleForm.fxml",Utils.currentStage(event))
                );
            }
        });
    }
    
    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Vehicle, Vehicle>() {
            private final Button button = new Button("Deletar");
            
            @Override
            protected void updateItem(Vehicle obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);

                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }
    
    private void removeEntity(Vehicle obj) {
        Optional<ButtonType> result =
            Alerts.showConfirmation("Confirmação", "Você realmente deseja deletar?");
        
        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            
            try {
                service.remove(obj);
                
                updateTableView();
            }
            catch (DbException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
            }
        }
    }
    
}
