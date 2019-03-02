/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Util.Notification;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ReserveController implements Initializable {

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;

    @FXML
    private JFXComboBox<String> cbbTable;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtCustomerPhone;
    @FXML
    private JFXTextField txtCustomerAddress;
    @FXML
    private DatePicker customerDate;
    @FXML
    private JFXTextField txtCustomerTime;

    private int maxIDReservation;
    private int maxIDCustomer;

    private ObservableList<String> listComboboxTable = FXCollections.observableArrayList();
    private HashMap<Integer, Integer> hasmapComboboxTable;
    @FXML
    private AnchorPane anchorPaneReserve;
    @FXML
    private StackPane stackPaneReserve;

    private int maxIDReservation() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(reservation_id) as reservation_id from Reservation";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDReservation = rs.getInt("reservation_id");
        }
        return maxIDReservation;
    }

    private int maxIDCustomer() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(customer_id) as customer_id from Customer";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDCustomer = rs.getInt("customer_id");
        }
        return maxIDCustomer;
    }

    @FXML
    private void clickCancelReserve(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickAddReserve(ActionEvent event) throws ClassNotFoundException, SQLException {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String a = customerDate.getValue() + " " + txtCustomerTime.getText();
        String b = s.format(d);
       
        Date date = new Date();
        SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd");
        if (txtCustomerName.getText().trim().isEmpty() || txtCustomerPhone.getText().trim().isEmpty() || txtCustomerAddress.getText().trim().isEmpty() || txtCustomerTime.getText().trim().isEmpty() || customerDate.getValue() == null) {
            Notification.showMessageDialog(stackPaneReserve, anchorPaneReserve, "Vui long nhap day du");
        } else if (a.compareTo(b) < 0) {
            Notification.showMessageDialog(stackPaneReserve, anchorPaneReserve, "Nhap ngay sai ");
            customerDate.setValue(null);
            txtCustomerTime.setText("");
        } else {
            maxIDReservation();
            maxIDCustomer();
            int maxID = maxIDCustomer + 1;
            String[] split = cbbTable.getValue().split("\\s");
            String tableNo = split[1];

            System.out.println(tableNo);
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into Customer values(?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxID);
            pre.setString(2, txtCustomerName.getText());
            pre.setInt(3, Integer.parseInt(txtCustomerPhone.getText()));
            pre.setString(4, txtCustomerAddress.getText());
            rss = pre.executeUpdate();

            sql = "Insert into Reservation values(?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDReservation + 1);
            pre.setInt(2, hasmapComboboxTable.get(Integer.parseInt(tableNo)));
            pre.setInt(3, maxID);
            pre.setString(4, String.valueOf(customerDate.getValue() + " " + txtCustomerTime.getText()));
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 2 Where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapComboboxTable.get(Integer.parseInt(tableNo)));
            rss = pre.executeUpdate();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private void addValue_ComboboxTables() throws ClassNotFoundException, SQLException {
        hasmapComboboxTable = new HashMap<>();
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, table_no, status from Tables Where status = 0";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hasmapComboboxTable.put(rs.getInt("table_no"), rs.getInt("table_id"));
        }

        for (Integer key : hasmapComboboxTable.keySet()) {
            listComboboxTable.add("Table " + key);
        }
        cbbTable.setItems(listComboboxTable);
    }

    @FXML
    private void inputPhone(KeyEvent event) {
        String text = txtCustomerPhone.getText();
        text = text.replaceAll("[^\\d.]", "");
        txtCustomerPhone.setText(text);
        int length = text.length();
        txtCustomerPhone.positionCaret(length);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addValue_ComboboxTables();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReserveController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
