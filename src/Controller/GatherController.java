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
public class GatherController implements Initializable {

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;

    private HashMap<Integer, Integer> hasmapComboboxTables;
    private HashMap<Integer, Integer> hasmapTablesNo;
    private ObservableList<String> listComboboxTable = FXCollections.observableArrayList();

    @FXML
    private StackPane stackPaneGather;
    @FXML
    private AnchorPane anchorPaneGather;
    @FXML
    private Label lblTable;
    @FXML
    private JFXComboBox<String> cbbTable;

    @FXML
    private void clickCancelGather(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickAddGather(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (cbbTable.getValue() == null) {
            Notification.showMessageDialog(stackPaneGather, anchorPaneGather, "Please enter table you want to gather.");
        } else {
            hasMapTables();
            
            int old_order_id = 0;
            String[] split = lblTable.getText().split("\\s");
            String oldTable = split[1];

            int new_order_id = 0;
            String[] split1 = cbbTable.getValue().split("\\s");
            String newTable = split1[1];

            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Select order_id, bill_id, table_id, time_in From Orders Where table_id = ? and bill_id  = 0";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapTablesNo.get(Integer.parseInt(oldTable)));
            rs = pre.executeQuery();
            while (rs.next()) {
                old_order_id = rs.getInt("order_id");
            }

            sql = "Select order_id, bill_id, table_id, time_in From Orders Where table_id = ? and bill_id  = 0";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapTablesNo.get(Integer.parseInt(newTable)));
            rs = pre.executeQuery();
            while (rs.next()) {
                new_order_id = rs.getInt("order_id");
            }

            sql = "Update OrderStorage set order_id = ? Where order_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, new_order_id);
            pre.setInt(2, old_order_id);
            rss = pre.executeUpdate();

            sql = "Delete Orders Where order_id = ? and bill_id = 0";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, old_order_id);
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 0 Where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hasmapTablesNo.get(Integer.parseInt(oldTable)));
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
        sql = "Select table_id, table_no, status from Tables Where status = 1";
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
            Logger.getLogger(GatherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
