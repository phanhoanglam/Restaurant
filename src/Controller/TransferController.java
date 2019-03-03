/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Util.Notification;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class TransferController implements Initializable {

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;

    @FXML
    private StackPane stackPaneTransfer;
    @FXML
    private AnchorPane anchorPaneTransfer;

    private HashMap<Integer, Integer> hasmapComboboxTables;
    private HashMap<Integer, Integer> hasmapTablesNo;
    private ObservableList<String> listComboboxTable = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<String> cbbTable;
    @FXML
    private Label lblTable;

    @FXML
    private void clickCancelTransfer(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickAddTransfer(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (cbbTable.getValue() == null) {
            Notification.showMessageDialog(stackPaneTransfer, anchorPaneTransfer, "Please choose table you want to transfer.");
        } else {
            hasMapTables();
            String[] split = cbbTable.getValue().split("\\s");
            String newTable = split[1];

            String[] split1 = lblTable.getText().split("\\s");
            String oldTable = split1[1];
            
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Update Orders set table_id = ? where table_id = ? and bill_id = 0";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapComboboxTables.get(Integer.parseInt(newTable)));
            pre.setInt(2, hasmapTablesNo.get(Integer.parseInt(oldTable)));
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 0 where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapTablesNo.get(Integer.parseInt(oldTable)));
            rss = pre.executeUpdate();
            
            sql = "Update Tables set status = 1 where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapTablesNo.get(Integer.parseInt(newTable)));
            rss = pre.executeUpdate();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void dataTransmissionReserve(String table_no) {
        lblTable.setText("Table " + table_no);
    }

    private void addValue_ComboboxTables() throws ClassNotFoundException, SQLException {
        hasmapComboboxTables = new HashMap<>();
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, table_no, status from Tables Where status = 0";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hasmapComboboxTables.put(rs.getInt("table_no"), rs.getInt("table_id"));
        }

        for (Integer key : hasmapComboboxTables.keySet()) {
            listComboboxTable.add("Table " + key);
        }
        cbbTable.setItems(listComboboxTable);
    }
    
    private void hasMapTables() throws ClassNotFoundException, SQLException {
        hasmapTablesNo = new HashMap<>();
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, table_no, status from Tables";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hasmapTablesNo.put(rs.getInt("table_no"), rs.getInt("table_id"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addValue_ComboboxTables();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
