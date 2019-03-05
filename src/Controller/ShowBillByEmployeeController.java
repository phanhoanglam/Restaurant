/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BillsModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ShowBillByEmployeeController implements Initializable {

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;

    @FXML
    private StackPane stackPaneShowBill;
    @FXML
    private TableView<BillsModel> tableViewshowBill;
    @FXML
    private TableColumn<BillsModel, Integer> tableCol;
    @FXML
    private TableColumn<BillsModel, String> timeinCol;
    @FXML
    private TableColumn<BillsModel, String> timeoutCol;
    @FXML
    private TableColumn<BillsModel, Float> subtotalCol;
    @FXML
    private TableColumn<BillsModel, Float> discountCol;
    @FXML
    private TableColumn<BillsModel, Float> bonusfeeCol;
    @FXML
    private TableColumn<BillsModel, Float> totalCol;
    private ObservableList<BillsModel> listViewBill = FXCollections.observableArrayList();
    private HashMap<Integer, Integer> hashmapTables = new HashMap<>();
    private HashMap<String, Integer> hashmapUser = new HashMap<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void selectTable() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, table_no From Tables";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapTables.put(rs.getInt("table_id"), rs.getInt("table_no"));
        }
    }

    public void selectUser() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select user_id, user_name From Users";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapUser.put(rs.getString("user_name"), rs.getInt("user_id"));
        }
    }

    public void showBillByEmployee(String user) throws ClassNotFoundException, SQLException {
        selectUser();
        selectTable();
        tableCol.setCellValueFactory(new PropertyValueFactory<>("table"));
        timeinCol.setCellValueFactory(new PropertyValueFactory<>("timein"));
        timeoutCol.setCellValueFactory(new PropertyValueFactory<>("timeout"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        bonusfeeCol.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, time_in, time_out, discount, bonus_fee, subtotal, total From Bills Where user_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, hashmapUser.get(user));
        rs = pre.executeQuery();
        while (rs.next()) {
            BillsModel billsModel = new BillsModel(hashmapTables.get(rs.getInt("table_id")), rs.getString("time_in"), rs.getString("time_out"), rs.getFloat("discount"), rs.getFloat("bonus_fee"), rs.getFloat("subtotal"), rs.getFloat("total"));
            listViewBill.add(billsModel);
        }
        tableViewshowBill.setItems(listViewBill);
    }

    @FXML
    private void clickCancelShowBills(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
