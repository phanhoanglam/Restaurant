/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.UsersModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ManageController implements Initializable {

    @FXML
    private StackPane stackPaneManage;
    @FXML
    private Button btnLoadScondItems;
    @FXML
    private Button btnLoadScondUsers;
    @FXML
    private Button btnLoadScondSales;
    @FXML
    private Label lockItems;
    @FXML
    private Label lockUser;
    
    @FXML
    private Label lblUser;
    @FXML
    private Label lblRole;
    
    public String nameUser;

    public void decentralizationUserLogin(String role, String name) {
        nameUser = name;
        
        lblUser.setText(name);
        lblRole.setText(role);
        
        if (role.equalsIgnoreCase("Admin")) {
            btnLoadScondItems.setDisable(false);
            btnLoadScondSales.setDisable(false);
            btnLoadScondUsers.setDisable(false);
        } else {
            btnLoadScondItems.setDisable(true);
            btnLoadScondSales.setDisable(false);
            btnLoadScondUsers.setDisable(true);
            lockUser.setVisible(true);
            lockItems.setVisible(true);
        }
    }

    @FXML
    private void loadScondSales(ActionEvent event) throws IOException {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackPaneManage);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event1) -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/Sales.fxml"));                
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                scene.getStylesheets().add(getClass().getResource("/Css/Sales.css").toExternalForm());
                SalesController salesController = loader.getController();
                
                salesController.receiveDataNameManager(nameUser);
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeTransition.play();
    }

    @FXML
    private void loadScondItems(ActionEvent event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackPaneManage);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event1) -> {
            try {
                Parent root = (StackPane) FXMLLoader.load(getClass().getResource("/FXML/Items.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/Css/Sales.css").toExternalForm());
                Stage stage = (Stage) stackPaneManage.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeTransition.play();
    }

    @FXML
    private void clickExitManager(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Login.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    private void loadScondUsers(ActionEvent event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackPaneManage);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event1) -> {
            try {
                Parent root = (StackPane) FXMLLoader.load(getClass().getResource("/FXML/Users.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/Css/Sales.css").toExternalForm());
                Stage stage = (Stage) stackPaneManage.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
