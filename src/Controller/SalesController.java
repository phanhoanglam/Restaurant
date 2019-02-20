/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ItemsModel;
import Model.Notification;
import Model.TableModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class SalesController implements Initializable {

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;

    @FXML
    private StackPane stackPaneSales;
    @FXML
    private StackPane stackPaneListTable;
    @FXML
    private StackPane stackPaneListBill;
    @FXML
    private StackPane stackPaneInOut;
    @FXML
    private JFXButton btnListTable;
    @FXML
    private JFXButton btnInOut;
    @FXML
    private JFXButton btnListBill;

    private ArrayList<TableModel> listTables = new ArrayList<>();
    private ObservableList listViewTablesSales = FXCollections.observableArrayList();

    private ArrayList<ItemsModel> listDish = new ArrayList<>();
    private ObservableList listViewDish = FXCollections.observableArrayList();

    private HashMap<String, Integer> hashmapComboboxCategory;
    private HashMap<Integer, String> hashmapNameItemsCategory;
    private ObservableList<String> listComboboxCategory = FXCollections.observableArrayList();

    @FXML
    private FlowPane folowPaneTableSales;
    @FXML
    private Label userSales;
    @FXML
    private Label tableNoSales;
    @FXML
    private Label intimeSales;
    @FXML
    private Label outTimeSales;
    @FXML
    private Button btnSepetateSales;
    @FXML
    private Button btnGatherSales;
    @FXML
    private Button btnReserveSales;
    @FXML
    private Button btnCancelSales;
    @FXML
    private Button btnFinishSales;
    @FXML
    private Button btnStartSales;
    @FXML
    private Button btnTransferSales;
    @FXML
    private AnchorPane anchorPaneSales;
    @FXML
    private JFXComboBox<String> cbbCategorySales;
    @FXML
    private FlowPane flowPaneDish;
    @FXML
    private Button btnSearchDish;
    @FXML
    private Button btnSearchDishAll;
    @FXML
    private TableView<ItemsModel> tableViewOrderDish;
    @FXML
    private TableColumn<ItemsModel, String> nameColOrder;
    @FXML
    private TableColumn<ItemsModel, Integer> quantityColOder;
    @FXML
    private TableColumn<ItemsModel, Float> priceColOder;
    @FXML
    private TableColumn<ItemsModel, Button> actionColOder;
    private ObservableList<ItemsModel> listTableViewDishOrder = FXCollections.observableArrayList();
    @FXML
    private TextField txtQuantitySales;

    @FXML
    private void clickListTable(ActionEvent event) {
        stackPaneListTable.setVisible(true);
        stackPaneListBill.setVisible(false);
        stackPaneInOut.setVisible(false);
    }

    @FXML
    private void clickListBill(ActionEvent event) {
        stackPaneListTable.setVisible(false);
        stackPaneListBill.setVisible(true);
        stackPaneInOut.setVisible(false);
    }

    @FXML
    private void clickInOut(ActionEvent event) {
        stackPaneListTable.setVisible(false);
        stackPaneListBill.setVisible(false);
        stackPaneInOut.setVisible(true);
    }

    @FXML
    private void clickComeBackManage(ActionEvent event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackPaneSales);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event1) -> {
            try {
                Parent root = (StackPane) FXMLLoader.load(getClass().getResource("/FXML/Manage.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/Css/Manage.css").toExternalForm());
                Stage stage = (Stage) stackPaneSales.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeTransition.play();
    }

    public void showTablesItems() throws ClassNotFoundException, SQLException {
        listTables.removeAll(listTables);
        listViewTablesSales.clear();

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, table_no, status from Tables";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            TableModel model = new TableModel(rs.getInt("table_id"), rs.getInt("table_no"), rs.getInt("status"));
            listTables.add(model);
        }

        for (int i = 0; i < listTables.size(); i++) {
            File file = new File("");
            Image image = new Image("file:///" + file.getAbsolutePath() + "/src/images/table_icon.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(80);
            Button button = new Button("Table " + listTables.get(i).getTableNo(), imageView);
            button.setPrefSize(180, 96);
            FlowPane.setMargin(button, new Insets(10, 5, 10, 5));
            button.setOnMouseClicked((event) -> {
                listTableViewDishOrder.clear();
                outTimeSales.setText("");
                intimeSales.setText("");
                tableNoSales.setText(button.getText());

            });
            if (listTables.get(i).getStatus() == 0) {
//                button.getStyleClass().add("buttonTables");
            } else {
                button.setStyle("-fx-background-color: red;-fx-border-color: black;\n"
                        + "    -fx-border-width: 3;");
//                button.getStyleClass().add("buttonTablesStatus");
            }
            folowPaneTableSales.setPadding(new Insets(10, 10, 10, 10));
            listViewTablesSales = folowPaneTableSales.getChildren();
            listViewTablesSales.add(button);

        }
    }

    private void addValue_ComboboxCategory() throws ClassNotFoundException, SQLException {
        hashmapComboboxCategory = new HashMap<>();
        hashmapNameItemsCategory = new HashMap<>();
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select category_id, name from Category";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapComboboxCategory.put(rs.getString("name"), rs.getInt("category_id"));
            hashmapNameItemsCategory.put(rs.getInt("category_id"), rs.getString("name"));
        }
        for (String key : hashmapComboboxCategory.keySet()) {
            listComboboxCategory.add(key);
        }
        cbbCategorySales.setItems(listComboboxCategory);
    }

    public void showDishSales() throws ClassNotFoundException, SQLException {
        listDish.removeAll(listDish);
        listViewDish.clear();

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select storage_item_id, category_id, name, price, quantity, images from Storage";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            ItemsModel itemsModel = new ItemsModel(rs.getInt("storage_item_id"), hashmapNameItemsCategory.get(rs.getInt("category_id")), rs.getString("name"), rs.getFloat("price"), rs.getInt("quantity"), rs.getString("images"));
            listDish.add(itemsModel);
        }

        for (int i = 0; i < listDish.size(); i++) {
            File file = new File("");
            Image image = new Image("file:///" + listDish.get(i).getImages());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(168);
            imageView.setFitHeight(166);
            JFXButton fXButton = new JFXButton(listDish.get(i).getName(), imageView);
            fXButton.setPrefSize(176, 167);
            Tooltip tooltip = new Tooltip();
            tooltip.setStyle("-fx-background-color: green");
            tooltip.setText(String.valueOf("Price : " + listDish.get(i).getPrice() + "\n" + "Quantity : " + listDish.get(i).getQuantity()));
            fXButton.setTooltip(tooltip);
            fXButton.setOnMouseClicked((event) -> {
                if (tableNoSales.getText().isEmpty()) {
                    Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a table");
                } else if (intimeSales.getText().isEmpty()) {
                    Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chưa click intime");
                } else if (!outTimeSales.getText().isEmpty()) {
                    Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da hoan thanh khong the goi mon");
                } else {
                    try {
                        nameColOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
                        quantityColOder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                        priceColOder.setCellValueFactory(new PropertyValueFactory<>("price"));
                        actionColOder.setCellValueFactory(new PropertyValueFactory<>("delete"));

                        Button button = new Button("Delete");
                        button.setPrefWidth(109);
                        button.getStyleClass().add("deletebtn");
                        button.setOnMouseClicked((event1) -> {
                            System.out.println(tableViewOrderDish.getItems());;
                            tableViewOrderDish.getItems().removeAll(tableViewOrderDish.getSelectionModel().getSelectedItems());
                        });

                        conn = Connect.ConnectDB.connectSQLServer();
                        sql = "Select name, price, quantity from Storage where name = ?";
                        pre = conn.prepareStatement(sql);
                        pre.setString(1, fXButton.getText());
                        rs = pre.executeQuery();
                        if (rs.next()) {
                            ItemsModel itemsModel = new ItemsModel(rs.getString("name"), rs.getFloat("price"), 1, button);
                            listTableViewDishOrder.add(itemsModel);
                        }
                        tableViewOrderDish.setItems(listTableViewDishOrder);

                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            FlowPane.setMargin(fXButton, new Insets(10, 0, 10, 5));
            fXButton.setContentDisplay(ContentDisplay.TOP);
            listViewDish = flowPaneDish.getChildren();
            listViewDish.add(fXButton);
        }
    }

    @FXML
    private void clickSearchDish(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (cbbCategorySales.getValue() == null) {

        } else {
            listDish.removeAll(listDish);
            listViewDish.clear();

            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Select storage_item_id, category_id, name, price, quantity, images from Storage where category_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hashmapComboboxCategory.get(cbbCategorySales.getValue()));
            rs = pre.executeQuery();
            while (rs.next()) {
                ItemsModel itemsModel = new ItemsModel(rs.getInt("storage_item_id"), hashmapNameItemsCategory.get(rs.getInt("category_id")), rs.getString("name"), rs.getFloat("price"), rs.getInt("quantity"), rs.getString("images"));
                listDish.add(itemsModel);
            }

            for (int i = 0; i < listDish.size(); i++) {
                File file = new File("");
                Image image = new Image("file:///" + listDish.get(i).getImages());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(168);
                imageView.setFitHeight(166);
                JFXButton fXButton = new JFXButton(listDish.get(i).getName(), imageView);
                fXButton.setPrefSize(176, 167);
                FlowPane.setMargin(fXButton, new Insets(10, 0, 10, 5));
                fXButton.setContentDisplay(ContentDisplay.TOP);
                listViewDish = flowPaneDish.getChildren();
                listViewDish.add(fXButton);
            }
        }
    }

    @FXML
    public void clickRowTableViewOrder() {
        ItemsModel selectedItem = tableViewOrderDish.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtQuantitySales.setDisable(false);
            txtQuantitySales.setText(String.valueOf(selectedItem.getQuantity()));
        }
    }

    public static boolean isTextFieldtypeNumber(TextField tf) {
        boolean b = false;
        if (tf.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            b = true;
        }
        return b;
    }

    @FXML
    private void clickAddQuantitySales(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (txtQuantitySales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua nhapp gia tri quantity");
        } else if (!isTextFieldtypeNumber(txtQuantitySales)) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Vui long nhap so");
            txtQuantitySales.setText("");
        } else {
            nameColOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
            quantityColOder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            priceColOder.setCellValueFactory(new PropertyValueFactory<>("price"));
            actionColOder.setCellValueFactory(new PropertyValueFactory<>("delete"));

            Button button = new Button("Delete");
            button.setPrefWidth(109);
            button.getStyleClass().add("deletebtn");
            button.setOnMouseClicked((event1) -> {
                tableViewOrderDish.getItems().removeAll(tableViewOrderDish.getSelectionModel().getSelectedItems());
            });

            ItemsModel selectedItem = tableViewOrderDish.getSelectionModel().getSelectedItem();
            tableViewOrderDish.getItems().removeAll(tableViewOrderDish.getSelectionModel().getSelectedItem());
            ItemsModel itemsModel = new ItemsModel(selectedItem.getName(),
                    selectedItem.getPrice() * Integer.parseInt(txtQuantitySales.getText()),
                    Integer.parseInt(txtQuantitySales.getText()), button);
            listTableViewDishOrder.add(itemsModel);
            tableViewOrderDish.setItems(listTableViewDishOrder);
            txtQuantitySales.setText("");
        }
    }

    @FXML
    private void clickSearchDishAll(ActionEvent event) throws ClassNotFoundException, SQLException {
        cbbCategorySales.setValue(null);
        showDishSales();
    }

    @FXML
    private void clickSepetateSales(ActionEvent event) {
    }

    @FXML
    private void clickGatherSales(ActionEvent event) {
    }

    @FXML
    private void clickReserveSales(ActionEvent event) {
    }

    @FXML
    private void clickTransferSales(ActionEvent event) {
    }

    @FXML
    private void clickCancelSales(ActionEvent event) {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a table");
        } else if (!tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da order mon khong the Cancel");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da hoan thanh khong the cancel");
        } else {
            Notification.showCancelDialog(stackPaneSales, anchorPaneSales, "Ban chac chan cancel", tableNoSales, intimeSales);
        }
    }

    @FXML
    private void clickFinishSales(ActionEvent event) {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a table");
        } else if (tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua goi mon");
        } else if (intimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua click intime");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "You want to reset outtime?", outTimeSales, txtQuantitySales);
            txtQuantitySales.setDisable(true);
        } else {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "Ban co muon hoan thanh", outTimeSales, txtQuantitySales);
            txtQuantitySales.setDisable(true);
        }
    }

    @FXML
    private void clickStartSales(ActionEvent event) {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a table");
        } else if (!tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da goi mon khong the start intime");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da hoan thanh khong thẻ start ");
        } else if (!intimeSales.getText().isEmpty()) {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "You want to reset intime?", intimeSales, null);
        } else {
            Date d = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            intimeSales.setText(s.format(d));
        }
    }

    public void receiveDataNameManager(String name) {
        userSales.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receiveDataNameManager(sql);
        try {
            addValue_ComboboxCategory();
            showTablesItems();
            showDishSales();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
