/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.org.apache.xml.internal.serializer.SerializerTrace;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Brand;
import model.entities.Client;
import model.entities.Model;
import model.entities.Vehicle;
import model.exceptions.ValidationException;
import model.services.BrandService;
import model.services.ClientService;
import model.services.ModelService;
import model.services.VehicleService;

/**
 *
 * @author leandro
 */
public class VehicleFormController implements Initializable {
    
    private Vehicle entity;
    
    private VehicleService service;
    
    private ClientService clientService;
    
    private BrandService brandService;
    
    private ModelService modelService;
    
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    
    @FXML
    private TextField txtId;
    
    @FXML 
    private TextField txtPlate;
    
    //@FXML 
    //private TextField txtBrand;
    
    //@FXML 
    //private TextField txtModel;
    
    @FXML 
    private TextField txtYear;
    
    @FXML 
    private TextField txtRenavam;
    
    @FXML 
    private TextField txtColor;
    
    @FXML
    private ComboBox<Brand> comboBoxBrand;
    
    @FXML
    private ComboBox<Model> comboBoxModel;
    
    @FXML
    private Label labelErrorPlate;
    
    @FXML
    private Button btSave;
    
    @FXML
    private Button btCancel;
    
    private ObservableList<Brand> brandObsList;
    
    private ObservableList<Model> modelObsList;
    
    public void setVehicle(Vehicle entity) {
        this.entity = entity;
    }
    
    public void setServices(
        VehicleService service,
        ClientService clientService,
        BrandService brandService,
        ModelService modelService
    ) {
        this.service = service;
        this.clientService = clientService;
        this.brandService = brandService;
        this.modelService = modelService;
    }
    
    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        
        try {        
            entity = getFormData();

            service.saveOrUpdate(entity);
            
            notifyDataChangeListeners();
            
            Utils.currentStage(event).close();
        }
        catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
        catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
        }
    }
    
    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }
    
    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtPlate, 7);
        Constraints.setTextFieldInteger(txtYear);
        Constraints.setTextFieldMaxLength(txtRenavam, 11);
        Constraints.setTextFieldMaxLength(txtColor, 45);
        
        initializeComboBoxBrand();
        initializeComboBoxModel();    
    }

    private Vehicle getFormData() {
        Vehicle obj = new Vehicle();
        
        // For now, client is hardcoded, until we develop the session
        Client client = new Client(1, "Leandro Vieira", "leandro@gmail.com", "123456");
        
        ValidationException exception = new ValidationException("Validation error");
        
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        
        // Validations
        if (txtPlate.getText() == null || txtPlate.getText().trim().equals(""))
            exception.addError("plate", "Field can't be empty");
        
        if (exception.getErrors().size() > 0) {
            throw exception;
        }
        
        obj.setPlate(txtPlate.getText());    
        obj.setYear(Utils.tryParseToInt(txtYear.getText()));
        obj.setRenavam(txtRenavam.getText());
        obj.setColor(txtColor.getText());   
        
        obj.setClient(client);
        obj.setBrand(comboBoxBrand.getValue());
        obj.setModel(comboBoxModel.getValue());    
        
        return obj;
    }
    
    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        
        txtId.setText(String.valueOf(entity.getId()));
        txtPlate.setText(entity.getPlate());
        txtYear.setText(String.valueOf(entity.getYear()));
        txtRenavam.setText(entity.getRenavam());
        txtColor.setText(entity.getColor());
        
        if (entity.getBrand() == null) {
            comboBoxBrand.getSelectionModel().selectFirst();
        }
        else {
            comboBoxBrand.setValue(entity.getBrand());
        }
        
        if (entity.getModel() == null) {
            comboBoxModel.getSelectionModel().selectFirst();
        }
        else {
            comboBoxModel.setValue(entity.getModel());
        }
    }
    
    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }
    
    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        
        if (fields.contains("plate")) {
            labelErrorPlate.setText(errors.get("plate"));
        }
    }
    
    public void loadAssociatedObjects() {
        if (clientService == null) {
            throw new IllegalStateException("ClientService was null");
        }
        
        if (brandService == null) {
            throw new IllegalStateException("BrandService was null");
        }
        
        if (modelService == null) {
            throw new IllegalStateException("ModelService was null");
        }
        
        // For now, id = 1 is hardcoded
        Client client = clientService.findById(1);
        
        List<Brand> brandList = brandService.findAll();
        brandObsList = FXCollections.observableArrayList(brandList);
        comboBoxBrand.setItems(brandObsList);
        
        // Fix: need to condition the models by the selected brand
        List<Model> modelList = modelService.findAll();
        modelObsList = FXCollections.observableArrayList(modelList);
        comboBoxModel.setItems(modelObsList);
    }
    
    private void initializeComboBoxBrand() {
        Callback<ListView<Brand>, ListCell<Brand>> factory = lv -> new ListCell<Brand>() {
            
            @Override
            protected void updateItem(Brand item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        
        comboBoxBrand.setCellFactory(factory);
        comboBoxBrand.setButtonCell(factory.call(null));
    }
    
    private void initializeComboBoxModel() {
        Callback<ListView<Model>, ListCell<Model>> factory = lv -> new ListCell<Model>() {
            
            @Override
            protected void updateItem(Model item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        
        comboBoxModel.setCellFactory(factory);
        comboBoxModel.setButtonCell(factory.call(null));
    }
    
}
