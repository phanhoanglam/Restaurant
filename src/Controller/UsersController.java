/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Util.MD5;
import Util.Notification;
import Model.UsersModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class UsersController implements Initializable {

    // biến dùng truyền dữ liệu
    public String userName;
    public String roleName;

    @FXML
    private StackPane stackPaneUsers;
    @FXML
    private AnchorPane AnchorPaneUser;

    private RadioButton buttonGender, buttonRole;
    private ToggleGroup toggleGender, toggleRole;
    private int maxIDUsers;

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;

    private ObservableList<UsersModel> listUser = FXCollections.observableArrayList();

    @FXML
    private TableView<UsersModel> tableUser;
    @FXML
    private TableColumn<UsersModel, Integer> noCol;
    @FXML
    private TableColumn<UsersModel, String> nameCol;
    @FXML
    private TableColumn<UsersModel, String> passCol;
    @FXML
    private TableColumn<UsersModel, String> fullnameCol;
    @FXML
    private TableColumn<UsersModel, String> addressCol;
    @FXML
    private TableColumn<UsersModel, Integer> ageCol;
    @FXML
    private TableColumn<UsersModel, String> genderCol;
    @FXML
    private TableColumn<UsersModel, String> positionCol;
    @FXML
    private TableColumn<UsersModel, Date> dateCol;

    @FXML
    private JFXRadioButton rdMaleUser;
    @FXML
    private JFXRadioButton rdFamaleUser;
    @FXML
    private JFXRadioButton rdAdminUser;
    @FXML
    private JFXRadioButton rdEmployeeUser;

    @FXML
    private JFXTextField txtUser;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtAge;
    @FXML
    private JFXPasswordField txtPass;
    @FXML
    private JFXTextField txtFullname;

    @FXML
    private Button btnEditUser;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnDeleteUser;
    @FXML
    private Button btnResetUser;
    @FXML
    private Button btnCancelUser;

    public void receiveDataNameManager(String name, String role) {
        userName = name;
        roleName = role;
    }

    @FXML
    private void clickComeBackManage(ActionEvent event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackPaneUsers);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event1) -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/Manage.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                scene.getStylesheets().add(getClass().getResource("/Css/Manage.css").toExternalForm());
                ManageController manageController = loader.getController();
                manageController.decentralizationUserLogin(roleName, userName);
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeTransition.play();
    }

    public void group_RadioGenderUsers() {
        toggleGender = new ToggleGroup();
        rdMaleUser.setToggleGroup(toggleGender);
        rdFamaleUser.setToggleGroup(toggleGender);
        rdMaleUser.setSelected(true);
    }

    public void group_RadioRoleUsers() {
        toggleRole = new ToggleGroup();
        rdAdminUser.setToggleGroup(toggleRole);
        rdEmployeeUser.setToggleGroup(toggleRole);
        rdEmployeeUser.setSelected(true);
    }

    public UsersModel checkUsername(String username) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select user_name from Users where user_name = ?";
        pre = conn.prepareStatement(sql);
        pre.setString(1, username);
        rs = pre.executeQuery();
        if (rs.next()) {
            UsersModel model = new UsersModel(rs.getString("user_name"));
            pre.close();
            conn.close();
            return model;
        } else {
            pre.close();
            conn.close();
            return null;
        }
    }

    public int maxIDUser() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(user_id) as user_id from Users";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDUsers = rs.getInt("user_id");
        }
        return maxIDUsers;
    }

    @FXML
    public void clickRowTable() {
        UsersModel selectedItem = tableUser.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnCancelUser.setVisible(true);
            btnEditUser.setVisible(true);
            btnDeleteUser.setVisible(true);
            btnAddUser.setVisible(false);
            btnResetUser.setVisible(false);

            txtUser.setText(selectedItem.getUser_name());
            txtUser.setDisable(true);
            txtFullname.setText(selectedItem.getFullname());
            txtAddress.setText(selectedItem.getAddress());
            txtAge.setText(String.valueOf(selectedItem.getAge()));

            if (selectedItem.getGender().equals("Male")) {
                rdMaleUser.setSelected(true);
            } else {
                rdFamaleUser.setSelected(true);
            }
            if (selectedItem.getRole().equals("Admin")) {
                rdAdminUser.setSelected(true);
            } else {
                rdEmployeeUser.setSelected(true);
            }
        }
    }

    public static boolean isTextFieldtypeNumber(JFXTextField tf) {
        boolean b = false;
        if (tf.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            b = true;
        }
        return b;
    }

    @FXML
    private void clickAddUsers(ActionEvent event) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, ParseException {
        UsersModel model = checkUsername(txtUser.getText());

        if (txtUser.getText().trim().isEmpty() || txtPass.getText().trim().isEmpty() || txtFullname.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty() || txtAge.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "This field can not be empty.");
        } else if (!isTextFieldtypeNumber(txtAge)) {
            Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "Age must be number.");
            txtAge.setText("");
        } else if (model != null) {
            Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "Duplicate name!");
            txtUser.setText("");
        } else {
            maxIDUser();
            Date d = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into Users values(?,?,?,?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);

            pre.setInt(1, maxIDUsers + 1);
            pre.setString(2, txtUser.getText());
            pre.setString(3, MD5.md5(txtPass.getText()));
            pre.setString(4, txtFullname.getText());
            buttonRole = (RadioButton) toggleRole.getSelectedToggle();
            pre.setString(5, buttonRole.getText());
            pre.setString(6, txtAddress.getText());
            pre.setInt(7, Integer.parseInt(txtAge.getText()));
            int gender = 0;
            if (rdMaleUser.isSelected()) {
                gender = 0;
            }
            if (rdFamaleUser.isSelected()) {
                gender = 1;
            }
            pre.setInt(8, gender);
            pre.setString(9, s.format(d));
            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "Insert successfully.");
            }
            txtUser.setText("");
            txtFullname.setText("");
            txtPass.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            rdMaleUser.setSelected(true);
            rdEmployeeUser.setSelected(true);
            showTableUser();
        }
    }

    @FXML
    private void clickEditUsers(ActionEvent event) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        if (txtUser.getText().trim().isEmpty() || txtFullname.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty() || txtAge.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "This field can not be empty.");
        } else if (!isTextFieldtypeNumber(txtAge)) {
            Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "Age must be number.");
            txtAge.setText("");
        } else if (txtPass.getText().trim().isEmpty() == true) {
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Update Users Set fullname = ?, role = ?, address = ?, age = ?, gender = ?  where user_name = ?";
            pre = conn.prepareStatement(sql);

            pre.setString(1, txtFullname.getText());
            buttonRole = (RadioButton) toggleRole.getSelectedToggle();
            pre.setString(2, buttonRole.getText());
            pre.setString(3, txtAddress.getText());
            pre.setInt(4, Integer.parseInt(txtAge.getText()));
            int gender = 0;
            if (rdMaleUser.isSelected()) {
                gender = 0;
            }
            if (rdFamaleUser.isSelected()) {
                gender = 1;
            }
            pre.setInt(5, gender);
            pre.setString(6, txtUser.getText());
            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "Update successfully.");
            }
            txtUser.setDisable(false);

            btnCancelUser.setVisible(false);
            btnEditUser.setVisible(false);
            btnDeleteUser.setVisible(false);
            btnAddUser.setVisible(true);
            btnResetUser.setVisible(true);

            txtUser.setText("");
            txtFullname.setText("");
            txtPass.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            rdMaleUser.setSelected(true);
            rdEmployeeUser.setSelected(true);

            showTableUser();
        } else {
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Update Users Set user_pass = ?, fullname = ?, role = ?, address = ?, age = ?, gender = ?  where user_name = ?";
            pre = conn.prepareStatement(sql);

            pre.setString(1, MD5.md5(txtPass.getText()));
            pre.setString(2, txtFullname.getText());
            buttonRole = (RadioButton) toggleRole.getSelectedToggle();
            pre.setString(3, buttonRole.getText());
            pre.setString(4, txtAddress.getText());
            pre.setInt(5, Integer.parseInt(txtAge.getText()));
            int gender = 0;
            if (rdMaleUser.isSelected()) {
                gender = 0;
            }
            if (rdFamaleUser.isSelected()) {
                gender = 1;
            }
            pre.setInt(6, gender);
            pre.setString(7, txtUser.getText());
            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneUsers, AnchorPaneUser, "Update successfully.");
            }
            btnCancelUser.setVisible(false);
            btnEditUser.setVisible(false);
            btnDeleteUser.setVisible(false);
            btnAddUser.setVisible(true);
            btnResetUser.setVisible(true);

            txtUser.setText("");
            txtFullname.setText("");
            txtPass.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            rdMaleUser.setSelected(true);
            rdEmployeeUser.setSelected(true);

            showTableUser();
        }
    }

    @FXML
    private void clickDeleteUsers(ActionEvent event) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Delete Users where user_name = ?";
        pre = conn.prepareStatement(sql);
        pre.setString(1, txtUser.getText());
        rss = pre.executeUpdate();

        txtUser.setDisable(false);

        btnCancelUser.setVisible(false);
        btnEditUser.setVisible(false);
        btnDeleteUser.setVisible(false);
        btnAddUser.setVisible(true);
        btnResetUser.setVisible(true);

        txtUser.setText("");
        txtFullname.setText("");
        txtPass.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        rdMaleUser.setSelected(true);
        rdEmployeeUser.setSelected(true);

        showTableUser();
    }

    @FXML
    private void clickResetUsers(ActionEvent event) {
        txtUser.setText("");
        txtFullname.setText("");
        txtPass.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        rdMaleUser.setSelected(true);
        rdEmployeeUser.setSelected(true);
    }

    @FXML
    private void clickCancelUsers(ActionEvent event) {
        btnCancelUser.setVisible(false);
        btnEditUser.setVisible(false);
        btnDeleteUser.setVisible(false);
        btnAddUser.setVisible(true);
        btnResetUser.setVisible(true);

        txtUser.setDisable(false);
        txtUser.setText("");
        txtFullname.setText("");
        txtPass.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        rdMaleUser.setSelected(true);
        rdEmployeeUser.setSelected(true);
    }

    public void showTableUser() throws ClassNotFoundException, SQLException {
        listUser.clear();
        noCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("user_pass"));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("datetime"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select user_id, user_name, user_pass, fullname, role, address, age, gender, date_start from Users";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        int i = 1;
        String gender;
        while (rs.next()) {
            if (rs.getInt("gender") == 0) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            UsersModel model = new UsersModel(i++, rs.getString("user_name"), rs.getString("user_pass"), rs.getString("fullname"), rs.getString("role"), rs.getString("address"), rs.getInt("age"), gender, rs.getDate("Date_start"));
            listUser.add(model);
        }
        tableUser.setItems(listUser);
    }

    @FXML
    private void inputAge(KeyEvent event) {
        String text = txtAge.getText();
        text = text.replaceAll("[^\\d.]", "");
        txtAge.setText(text);
        int length = text.length();
        txtAge.positionCaret(length);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        lblDate.textProperty().bind(taskDate.messageProperty());
//        lblTime.textProperty().bind(taskTime.messageProperty());
//        new Thread(taskDate).start();
//        new Thread(taskTime).start();
        group_RadioGenderUsers();
        group_RadioRoleUsers();

        try {
            showTableUser();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Task<Time> taskDate = new Task<Time>() {
//        @Override
//        protected Time call() throws Exception {
//            while (true) {
//                Date d = new Date();
//                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//                updateMessage(s.format(d));
//                Thread.sleep(1000);
//            }
//        }
//
//        @Override
//        protected void updateMessage(String message) {
//            super.updateMessage(message);
//        }
//
//    };
//
//    Task<Time> taskTime = new Task<Time>() {
//        @Override
//        protected Time call() throws Exception {
//            while (true) {
//                Date d = new Date();
//                SimpleDateFormat h = new SimpleDateFormat("HH:mm:ss");
//                updateMessage(h.format(d));
//                Thread.sleep(1000);
//            }
//        }
//
//        @Override
//        protected void updateMessage(String message) {
//            super.updateMessage(message);
//        }
//    };

}
