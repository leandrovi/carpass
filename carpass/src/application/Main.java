/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author leandro
 */
public class Main extends Application {
    
    private static Scene mainScene;
    private static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginForm.fxml"));
            ScrollPane scrollPane = loader.load();
            
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            
            mainScene = new Scene(scrollPane);
            
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("User Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Scene getMainScene() {
        return mainScene;
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
