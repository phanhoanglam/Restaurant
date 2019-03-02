/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Util.MD5;
import Model.UsersModel;
import Util.Notification;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class LoginController implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPass;

    public ArrayList<UsersModel> listUser;
    @FXML
    private StackPane stackPaneLogin;
    @FXML
    private AnchorPane anchorPaneLogin;

    public ArrayList<UsersModel> listUser() throws ClassNotFoundException, SQLException {
        listUser = new ArrayList<>();
        Connection conn = Connect.ConnectDB.connectSQLServer();
        String sql = "Select user_id, user_name, user_pass, fullname, role, address, age, gender, date_start from Users";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            UsersModel userModel = new UsersModel(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_pass"),
                    rs.getString("fullname"), rs.getString("role"), rs.getString("address"), rs.getInt("age"), rs.getString("gender"), rs.getDate("date_start"));
            listUser.add(userModel);
        }
        return listUser;
    }

    @FXML
    private void clickSubmitLogin(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String username = txtUser.getText();
        String password = txtPass.getText();
        boolean loginStatus = false;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getUser_name().equals(username) && listUser.get(i).getUser_pass().equals(MD5.md5(password))) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/Manage.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                scene.getStylesheets().add(getClass().getResource("/Css/Manage.css").toExternalForm());
                ManageController controller = loader.getController();
                String role = listUser.get(i).getRole();
                String name = listUser.get(i).getUser_name();
                controller.decentralizationUserLogin(role, name);
                stage.setScene(scene);
                stage.centerOnScreen();
                loginStatus = true;
                return;
            }
        }
        if (!loginStatus) {
            Notification.showMessageDialog(stackPaneLogin, anchorPaneLogin, "login fail");
        }
    }

    @FXML
    private void clickCancelLogin(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Media media = new Media(getClass().getResource("/images/videoView1.mp4").toExternalForm());
        MediaPlayer player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        player.setVolume(0);
        player.play();

        try {
            listUser();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
