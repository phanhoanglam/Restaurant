package Controller;

import Util.Currency;
import static Controller.ItemsController.isTextFieldtypeNumber;
import Model.BillsModel;
import Model.CustomerModel;
import Model.OutcomeModel;
import Model.ItemsModel;
import Util.Notification;
import Model.OrderModel;
import Model.TableModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class SalesController implements Initializable {

    // biến dùng truyền dữ liệu
    public String userName;
    public String roleName;

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
    private Button btnLoadOrder;

    private int maxIDOrder;
    private int maxIDOrderStorage;
    private int maxIDBills;

    private int status;

    private HashMap<Integer, String> hashmapStorage = new HashMap<>();
    private HashMap<Integer, Integer> hashmapTables = new HashMap<>();
    private HashMap<Integer, Integer> hashmapTablesNo = new HashMap<>();
    private HashMap<String, Integer> hashmapUser = new HashMap<>();
    private HashMap<Integer, String> hashmapUserName = new HashMap<>();
    private HashMap<Integer, String> hashmapCategory = new HashMap<>();

    @FXML
    private Label totalSales;
    @FXML
    private JFXTextField bonusFeeSales;
    @FXML
    private Label subTotalSales;
    @FXML
    private JFXTextField discountSales;

    private ObservableList<ItemsModel> listTableCategoryListBill = FXCollections.observableArrayList();
    @FXML
    private TableView<ItemsModel> tableCategoryListBill;
    @FXML
    private TableColumn<ItemsModel, String> categoryBillCol;
    @FXML
    private TableColumn<ItemsModel, String> itemBillCol;
    @FXML
    private TableColumn<ItemsModel, Integer> quantityBillCol;

    private ObservableList<BillsModel> listTablepassengers = FXCollections.observableArrayList();
    @FXML
    private TableView<BillsModel> tablePassengersListBill;
    @FXML
    private TableColumn<BillsModel, String> tableBillsCol;
    @FXML
    private TableColumn<BillsModel, Integer> passengersCol;

    private ObservableList<BillsModel> listTableUserListBill = FXCollections.observableArrayList();
    @FXML
    private TableView<BillsModel> tableUserListBill;
    @FXML
    private TableColumn<BillsModel, String> userBillCol;
    @FXML
    private TableColumn<BillsModel, Integer> quantityOfBillCol;

    @FXML
    private JFXTextField txtOutcomeReason;
    @FXML
    private JFXTextField txtOutcomeAmount;
    @FXML
    private TableView<OutcomeModel> tableViewInAndOut;
    @FXML
    private TableColumn<OutcomeModel, String> dateCol;
    @FXML
    private TableColumn<OutcomeModel, String> outcomeReasonCol;
    @FXML
    private TableColumn<OutcomeModel, Integer> OutcomeIDCol;
    @FXML
    private TableColumn<OutcomeModel, Float> amountCol;
    @FXML
    private TableColumn<OutcomeModel, String> userCol;
    private ObservableList<OutcomeModel> listTableAmount = FXCollections.observableArrayList();

    @FXML
    private Button btnAddOutcome;
    @FXML
    private Button btnEditOutcome;
    @FXML
    private Button btnDeleteOutcome;
    @FXML
    private Button btnResetOutcome;
    @FXML
    private Button btnCancelOutcome;
    @FXML
    private Label lblamountIDOutcome;

    @FXML
    private void clickListTable(ActionEvent event) {
        stackPaneListTable.setVisible(true);
        stackPaneListBill.setVisible(false);
        stackPaneInOut.setVisible(false);
    }

    @FXML
    private void clickListBill(ActionEvent event) throws ClassNotFoundException, SQLException {
        listTableUserListBill.clear();
        showTableUserListBill();
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

    public void receiveDataNameManager(String name, String role) {
        userSales.setText(name);
        userName = name;
        roleName = role;
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

    public void selectStorage() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select storage_item_id, name From Storage";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapStorage.put(rs.getInt("storage_item_id"), rs.getString("name"));
        }
    }

    public void selectTable() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, table_no From Tables";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapTables.put(rs.getInt("table_id"), rs.getInt("table_no"));
            hashmapTablesNo.put(rs.getInt("table_no"), rs.getInt("table_id"));
        }
    }

    public void selectCategoryStorage() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select category_id, name From Category";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapCategory.put(rs.getInt("category_id"), rs.getString("name"));
        }
    }

    public void selectUser() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select user_id, user_name From Users";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapUser.put(rs.getString("user_name"), rs.getInt("user_id"));
            hashmapUserName.put(rs.getInt("user_id"), rs.getString("user_name"));
        }
    }

    public int checkTablesStatus(int table_no) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select status from Tables where table_no = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, table_no);
        rs = pre.executeQuery();
        if (rs.next()) {
            status = rs.getInt("status");
        }
        return status;
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

        listTables.sort((o1, o2) -> {
            return o1.getTableID() - o2.getTableID();
        });

        for (int i = 0; i < listTables.size(); i++) {
            File file = new File("");
            Image image = new Image(getClass().getResource("/images/table_icon.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(80);
            Button button = new Button("Table " + listTables.get(i).getTableNo(), imageView);
            button.setPrefSize(180, 96);
            FlowPane.setMargin(button, new Insets(10, 5, 10, 5));

            switch (listTables.get(i).getStatus()) {
                case 0:
                    button.getStyleClass().add("buttonTables");
                    break;
                case 1:
                    button.getStyleClass().add("buttonTablesStatus");
                    break;
                case 2:
                    button.getStyleClass().add("buttonTablesReseve");
                    break;
                default:
                    break;
            }

            button.setOnMouseClicked((event) -> {
                try {
                    txtQuantitySales.setText("");
                    txtQuantitySales.setDisable(true);

                    subTotalSales.setText("");
                    discountSales.setText("");
                    bonusFeeSales.setText("");
                    totalSales.setText("");
                    selectTable();
                    selectStorage();

                    listTableViewDishOrder.clear();
                    outTimeSales.setText("");
                    intimeSales.setText("");
                    tableNoSales.setText(button.getText());

                    String[] split = tableNoSales.getText().split("\\s");
                    String tableNo = split[1];
                    checkTablesStatus(Integer.parseInt(tableNo));

                    if (status == 1) {
                        try {
                            nameColOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
                            quantityColOder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                            priceColOder.setCellValueFactory(new PropertyValueFactory<>("price"));
                            actionColOder.setCellValueFactory(new PropertyValueFactory<>("delete"));

                            conn = Connect.ConnectDB.connectSQLServer();
                            int order_id = 0;
                            sql = "select order_id, bill_id, table_id, time_in from Orders Where table_id = ? and bill_id = 0";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, hashmapTables.get(Integer.parseInt(tableNo)));
                            rs = pre.executeQuery();
                            if (rs.next()) {
                                String[] splitTime = rs.getString("time_in").split("\\.");
                                String intime = splitTime[0];

                                intimeSales.setText(intime);

                                order_id = rs.getInt("order_id");
                            }

                            sql = "select OrderStorage_id, storage_item_id, quantity, price, order_id from OrderStorage where order_id = ?";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, order_id);
                            rs = pre.executeQuery();
                            while (rs.next()) {
                                ItemsModel itemsModel = new ItemsModel(hashmapStorage.get(rs.getInt("storage_item_id")),
                                        rs.getFloat("price"),
                                        rs.getInt("quantity"), null);
                                listTableViewDishOrder.add(itemsModel);
                            }
                            tableViewOrderDish.setItems(listTableViewDishOrder);

                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (status == 2) {
                        try {
                            nameColOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
                            quantityColOder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                            priceColOder.setCellValueFactory(new PropertyValueFactory<>("price"));
                            actionColOder.setCellValueFactory(new PropertyValueFactory<>("delete"));
                            
                            int customer_id = 0;
                            sql = "Select reservation_id, table_id, customer_id, time_start From Reservation Where table_id = ?";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, hashmapTables.get(Integer.parseInt(tableNo)));
                            rs = pre.executeQuery();
                            if(rs.next()){
                                customer_id = rs.getInt("customer_id");
                            }
                            
                            conn = Connect.ConnectDB.connectSQLServer();
                            sql = "Delete Reservation Where table_id = ?";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, hashmapTables.get(Integer.parseInt(tableNo)));
                            rss = pre.executeUpdate();
                            
                            sql = "Delete Customer Where customer_id = ?";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, customer_id);
                            rss = pre.executeUpdate();
                            
                            int order_id = 0;
                            sql = "select order_id, bill_id, table_id, time_in from Orders Where table_id = ? and bill_id = 0";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, hashmapTables.get(Integer.parseInt(tableNo)));
                            rs = pre.executeQuery();
                            if (rs.next()) {
                                String[] splitTime = rs.getString("time_in").split("\\.");
                                String intime = splitTime[0];

                                intimeSales.setText(intime);

                                order_id = rs.getInt("order_id");
                            }

                            sql = "select OrderStorage_id, storage_item_id, quantity, price, order_id from OrderStorage where order_id = ?";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, order_id);
                            rs = pre.executeQuery();
                            while (rs.next()) {
                                ItemsModel itemsModel = new ItemsModel(hashmapStorage.get(rs.getInt("storage_item_id")),
                                        rs.getFloat("price"),
                                        rs.getInt("quantity"), null);
                                listTableViewDishOrder.add(itemsModel);
                            }
                            tableViewOrderDish.setItems(listTableViewDishOrder);

                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
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

//    private float tong;
//
//    public float tong() {
//        for (int i = 0; i < listTableViewDishOrder.size(); i++) {
//            tong += listTableViewDishOrder.get(i).getPrice();
//        }
//        return tong;
//
//    }
    int quantity_Dish;

    public void dataTransmissionQuantity(int quantity) {
        quantity_Dish = quantity;
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
            tooltip.setText(String.valueOf("Price : " + Currency.toMoney(listDish.get(i).getPrice()) + "\n" + "Quantity : " + listDish.get(i).getQuantity()));
            fXButton.setTooltip(tooltip);
            fXButton.setOnMouseClicked((event) -> {
                if (tableNoSales.getText().isEmpty()) {
                    Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose a table.");
                } else if (intimeSales.getText().isEmpty()) {
                    Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please click Start button.");
                } else if (!outTimeSales.getText().isEmpty()) {
                    Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Can not order.");
                } else {
                    try {
                        txtQuantitySales.setText("");
                        txtQuantitySales.setDisable(true);

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

                        conn = Connect.ConnectDB.connectSQLServer();
                        sql = "Select storage_item_id, name, price, quantity from Storage where name = ?";
                        pre = conn.prepareStatement(sql);
                        pre.setString(1, fXButton.getText());
                        rs = pre.executeQuery();
                        if (rs.next()) {
                            ItemsModel itemsModel = new ItemsModel(rs.getInt("storage_item_id"), rs.getString("name"), rs.getFloat("price"), 1, button);
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
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a category.");
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
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please input quantity.");
        } else if (!isTextFieldtypeNumber(txtQuantitySales)) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please enter number.");
            txtQuantitySales.setText("");
        } else {
            txtQuantitySales.setText("");
            txtQuantitySales.setDisable(true);
            ItemsModel selectedItem = tableViewOrderDish.getSelectionModel().getSelectedItem();
            checkQuantityDish(selectedItem.getStorage_item_id());
            if (!(quantityDishLeft < (Integer.parseInt(txtQuantitySales.getText()) + selectedItem.getQuantity()))) {
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

                tableViewOrderDish.getItems().removeAll(selectedItem);
                ItemsModel itemsModel = new ItemsModel(selectedItem.getStorage_item_id(), selectedItem.getName(),
                        selectedItem.getPrice() * Integer.parseInt(txtQuantitySales.getText()),
                        Integer.parseInt(txtQuantitySales.getText()) + selectedItem.getQuantity(), button);
                listTableViewDishOrder.add(itemsModel);
                tableViewOrderDish.setItems(listTableViewDishOrder);
                txtQuantitySales.setText("");
            } else {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, quantityDishLeft + "left");
            }
        }
    }

    @FXML
    private void clickSearchDishAll(ActionEvent event) throws ClassNotFoundException, SQLException {
        cbbCategorySales.setValue(null);
        showDishSales();
    }

    public int maxIDOrder() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(order_id) as order_id from Orders";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDOrder = rs.getInt("order_id");
        }
        return maxIDOrder;
    }

    public int maxIDOrderStorage() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(OrderStorage_id) as OrderStorage_id from OrderStorage";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDOrderStorage = rs.getInt("OrderStorage_id");
        }
        return maxIDOrderStorage;
    }

    public OrderModel checkOrderTable(int table_id) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select order_id, bill_id, table_id, time_in From Orders Where table_id = ? and bill_id = 0";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, table_id);
        rs = pre.executeQuery();
        if (rs.next()) {
            OrderModel orderModel = new OrderModel(rs.getInt("order_id"), rs.getInt("bill_id"), rs.getInt("table_id"), rs.getString("time_in"));
            pre.close();
            conn.close();
            return orderModel;
        } else {
            pre.close();
            conn.close();
            return null;
        }
    }

    int quantityDish;

    public void deleteQuantityDishDB(int storage_item_id, int quantity) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select quantity From Storage Where storage_item_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, storage_item_id);
        rs = pre.executeQuery();
        if (rs.next()) {
            quantityDish = rs.getInt("quantity");
        }

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Update Storage set quantity = ? - ? Where storage_item_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, quantityDish);
        pre.setInt(2, quantity);
        pre.setInt(3, storage_item_id);
        rss = pre.executeUpdate();
        showDishSales();
    }

    int quantityDishLeft;

    public void checkQuantityDish(int storage_item_id) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select quantity From Storage Where storage_item_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, storage_item_id);
        rs = pre.executeQuery();
        if (rs.next()) {
            quantityDishLeft = rs.getInt("quantity");
        }
    }

    @FXML
    private void clickLoadOrder(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (!tableNoSales.getText().isEmpty()) {
            txtQuantitySales.setText("");
            txtQuantitySales.setDisable(true);
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            OrderModel model = checkOrderTable(Integer.parseInt(tableNo));
            if (tableViewOrderDish.getItems().isEmpty()) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose food or drink.");
            } else if (model != null) {
                for (int i = 0; i < listTableViewDishOrder.size(); i++) {
                    checkQuantityDish(status);
                    if (!(quantityDishLeft < listTableViewDishOrder.get(i).getQuantity())) {
                        if (listTableViewDishOrder.get(i).getStorage_item_id() != 0) {
                            maxIDOrderStorage();
                            conn = Connect.ConnectDB.connectSQLServer();
                            sql = "Insert into OrderStorage values(?,?,?,?,?)";
                            pre = conn.prepareStatement(sql);
                            pre.setInt(1, maxIDOrderStorage + 1);
                            pre.setInt(2, listTableViewDishOrder.get(i).getStorage_item_id());
                            pre.setInt(3, listTableViewDishOrder.get(i).getQuantity());
                            pre.setFloat(4, listTableViewDishOrder.get(i).getPrice());
                            pre.setInt(5, model.getOrder_id());
                            rss = pre.executeUpdate();

                            listTableViewDishOrder.get(i).getDelete().setVisible(false);
                            deleteQuantityDishDB(listTableViewDishOrder.get(i).getStorage_item_id(), listTableViewDishOrder.get(i).getQuantity());
                        }
                    } else {
                        Notification.showMessageDialog(stackPaneSales, anchorPaneSales, quantityDishLeft + "left");
                    }
                }
            } else {
                txtQuantitySales.setText("");
                txtQuantitySales.setDisable(true);
                maxIDOrder();
                conn = Connect.ConnectDB.connectSQLServer();
                sql = "Insert into Orders values(?,?,?,?)";
                pre = conn.prepareStatement(sql);
                int order_id = maxIDOrder + 1;
                pre.setInt(1, order_id);
                pre.setInt(2, 0);
                pre.setInt(3, Integer.parseInt(tableNo));
                pre.setString(4, intimeSales.getText());
                rss = pre.executeUpdate();

                for (int i = 0; i < listTableViewDishOrder.size(); i++) {
                    maxIDOrderStorage();
                    conn = Connect.ConnectDB.connectSQLServer();
                    sql = "Insert into OrderStorage values(?,?,?,?,?)";
                    pre = conn.prepareStatement(sql);
                    pre.setInt(1, maxIDOrderStorage + 1);
                    pre.setInt(2, listTableViewDishOrder.get(i).getStorage_item_id());
                    pre.setInt(3, listTableViewDishOrder.get(i).getQuantity());
                    pre.setFloat(4, listTableViewDishOrder.get(i).getPrice());
                    pre.setInt(5, order_id);
                    rss = pre.executeUpdate();

                    listTableViewDishOrder.get(i).getDelete().setVisible(false);
                    deleteQuantityDishDB(listTableViewDishOrder.get(i).getStorage_item_id(), listTableViewDishOrder.get(i).getQuantity());
                }
            }
        } else {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose table.");
        }
    }

    @FXML
    private void clickSepetateSales(ActionEvent event) {
    }

    @FXML
    private void clickGatherSales(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose table before gathering.");
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            checkTablesStatus(Integer.parseInt(tableNo));
            if (status == 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Empty table can not be gathered.");
            } else {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/Gather.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                GatherController gatherController = loader.getController();

                String table = tableNo;
                gatherController.dataTransmissionReserve(table);
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    private void clickReserveSales(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Reserve.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/Css/Sales.css").toExternalForm());
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void clickTransferSales(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose table before transfering.");
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            checkTablesStatus(Integer.parseInt(tableNo));
            if (status == 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Empty table can not be transfered.");
            } else {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/Transfer.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                TransferController transferController = loader.getController();

                String table = tableNo;
                transferController.dataTransmissionReserve(table);
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    private void clickCancelSales(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose a table.");
        } else if (!tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Can not cancel after ordering.");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Can not cancel after finishing.");
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            Notification.showCancelDialog(stackPaneSales, anchorPaneSales, "Do you want to cancel?", tableNoSales, intimeSales, tableNo);
        }
    }

    @FXML
    private void clickFinishSales(ActionEvent event
    ) {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose a table.");
        } else if (tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please order food or drink.");
        } else if (intimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please click Start button.");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "Do you want to reset outtime?", outTimeSales, txtQuantitySales);
            txtQuantitySales.setDisable(true);
        } else {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "Do you want to finish?", outTimeSales, txtQuantitySales);
            txtQuantitySales.setDisable(true);
        }
    }

    @FXML
    private void clickStartSales(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose a table.");
        } else if (!tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Can not click Start button after ordering.");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Can not click Start button after finishing.");
        } else if (!intimeSales.getText().isEmpty()) {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "Do you want to reset intime?", intimeSales, null);
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];

            conn = Connect.ConnectDB.connectSQLServer();
            sql = "update Tables Set status = 1 Where table_no = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, Integer.parseInt(tableNo));
            rss = pre.executeUpdate();

            Date d = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            intimeSales.setText(s.format(d));

            showTablesItems();
        }
    }

    @FXML
    private void clickPaySales(ActionEvent event
    ) {
        if (subTotalSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please pay subtotal.");
        } else if (discountSales.getText().isEmpty() && bonusFeeSales.getText().isEmpty()) {
            String[] split = subTotalSales.getText().split(",");
            String str = "";
            for (String string : split) {
                str += string;
            }
            float total = Float.parseFloat(str);
            totalSales.setText(String.valueOf(Currency.toMoney(total)));
        } else if (discountSales.getText().isEmpty()) {
            String[] split = subTotalSales.getText().split(",");
            String str = "";
            for (String string : split) {
                str += string;
            }

            String[] split1 = bonusFeeSales.getText().split(",");
            String bonus = "";
            for (String string : split1) {
                bonus += string;
            }
            float total = Float.parseFloat(str) + Float.parseFloat(bonus);
            totalSales.setText(String.valueOf(Currency.toMoney(total)));
        } else if (bonusFeeSales.getText().isEmpty()) {
            String[] split = subTotalSales.getText().split(",");
            String str = "";
            for (String string : split) {
                str += string;
            }

            String[] split1 = discountSales.getText().split(",");
            String discount = "";
            for (String string : split1) {
                discount += string;
            }
            float total = Float.parseFloat(str) + Float.parseFloat(discount);
            totalSales.setText(String.valueOf(Currency.toMoney(total)));
        } else {
            String[] split = subTotalSales.getText().split(",");
            String str = "";
            for (String string : split) {
                str += string;
            }

            String[] split1 = bonusFeeSales.getText().split(",");
            String bonus = "";
            for (String string : split1) {
                bonus += string;
            }

            String[] split2 = discountSales.getText().split(",");
            String discount = "";
            for (String string : split2) {
                discount += string;
            }
            float total = Float.parseFloat(str) + Float.parseFloat(discount) + Float.parseFloat(bonus);
            totalSales.setText(String.valueOf(Currency.toMoney(total)));
        }
    }

    @FXML
    private void clickPaySubtotalSales(ActionEvent event
    ) {
        if (outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Pleaes click Finish button.");
        } else {
            if (tableViewOrderDish.getItems().isEmpty()) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please choose food or drink.");
            } else {
                float tong = 0;
                System.out.println(listTableViewDishOrder.size());
                for (int i = 0; i < listTableViewDishOrder.size(); i++) {
                    tong += listTableViewDishOrder.get(i).getPrice();
                }
                subTotalSales.setText(String.valueOf(Currency.toMoney(tong)));
            }
        }
    }

    public int maxIDBills() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(bill_id) as bill_id from Bills";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDBills = rs.getInt("bill_id");
        }
        return maxIDBills;
    }

    @FXML
    private void clickBillPrint(ActionEvent event) throws ClassNotFoundException, SQLException, JRException {
        if (subTotalSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please click subtotal.");
        } else if (totalSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please click Pay.");
        } else if (outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please click Finish.");
        } else if (discountSales.getText().isEmpty() && bonusFeeSales.getText().isEmpty()) {
            selectUser();
            selectTable();
            maxIDBills();
            int maxIDBill = maxIDBills + 1;
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into Bills values(?,?,?,?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            pre.setInt(2, hashmapUser.get(userSales.getText()));
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            pre.setInt(3, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            pre.setString(4, intimeSales.getText());
            pre.setString(5, outTimeSales.getText());
            pre.setFloat(6, 0);
            pre.setFloat(7, 0);

            String[] str3 = subTotalSales.getText().split(",");
            String subtotal = "";
            for (String string : str3) {
                subtotal += string;
            }
            pre.setFloat(8, Float.parseFloat(subtotal));

            String[] str4 = totalSales.getText().split(",");
            String total = "";
            for (String string : str4) {
                total += string;
            }
            pre.setFloat(9, Float.parseFloat(total));

            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Bill added successfully.");
            }

            sql = "Update Orders set bill_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 0 where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            rss = pre.executeUpdate();

            showTablesItems();
            listTableViewDishOrder.clear();
            tableNoSales.setText("");
            intimeSales.setText("");
            outTimeSales.setText("");
            subTotalSales.setText("");
            discountSales.setText("");
            bonusFeeSales.setText("");
            totalSales.setText("");
        } else if (bonusFeeSales.getText().isEmpty()) {
            selectUser();
            selectTable();
            maxIDBills();
            int maxIDBill = maxIDBills + 1;
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into Bills values(?,?,?,?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            pre.setInt(2, hashmapUser.get(userSales.getText()));
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            pre.setInt(3, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            pre.setString(4, intimeSales.getText());
            pre.setString(5, outTimeSales.getText());

            String[] str1 = discountSales.getText().split(",");
            String discount = "";
            for (String string : str1) {
                discount += string;
            }
            pre.setFloat(6, Float.parseFloat(discount));
            pre.setFloat(7, 0);

            String[] str3 = subTotalSales.getText().split(",");
            String subtotal = "";
            for (String string : str3) {
                subtotal += string;
            }
            pre.setFloat(8, Float.parseFloat(subtotal));

            String[] str4 = totalSales.getText().split(",");
            String total = "";
            for (String string : str4) {
                total += string;
            }
            pre.setFloat(9, Float.parseFloat(total));

            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Bill added successfully.");
            }

            sql = "Update Orders set bill_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 0 where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            rss = pre.executeUpdate();

            showTablesItems();
            listTableViewDishOrder.clear();
            tableNoSales.setText("");
            intimeSales.setText("");
            outTimeSales.setText("");
            subTotalSales.setText("");
            discountSales.setText("");
            bonusFeeSales.setText("");
            totalSales.setText("");
        } else if (discountSales.getText().isEmpty()) {
            selectUser();
            selectTable();
            maxIDBills();
            int maxIDBill = maxIDBills + 1;
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into Bills values(?,?,?,?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            pre.setInt(2, hashmapUser.get(userSales.getText()));
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            pre.setInt(3, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            pre.setString(4, intimeSales.getText());
            pre.setString(5, outTimeSales.getText());
            pre.setFloat(6, 0);

            String[] str2 = bonusFeeSales.getText().split(",");
            String bonus = "";
            for (String string : str2) {
                bonus += string;
            }
            pre.setFloat(7, Float.parseFloat(bonus));

            String[] str3 = subTotalSales.getText().split(",");
            String subtotal = "";
            for (String string : str3) {
                subtotal += string;
            }
            pre.setFloat(8, Float.parseFloat(subtotal));

            String[] str4 = totalSales.getText().split(",");
            String total = "";
            for (String string : str4) {
                total += string;
            }
            pre.setFloat(9, Float.parseFloat(total));

            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Bill added successfully.");
            }

            sql = "Update Orders set bill_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 0 where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            rss = pre.executeUpdate();

            showTablesItems();
            listTableViewDishOrder.clear();
            tableNoSales.setText("");
            intimeSales.setText("");
            outTimeSales.setText("");
            subTotalSales.setText("");
            discountSales.setText("");
            bonusFeeSales.setText("");
            totalSales.setText("");
        } else {
            selectUser();
            selectTable();
            maxIDBills();
            int maxIDBill = maxIDBills + 1;
            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into Bills values(?,?,?,?,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            pre.setInt(2, hashmapUser.get(userSales.getText()));
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            pre.setInt(3, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            pre.setString(4, intimeSales.getText());
            pre.setString(5, outTimeSales.getText());

            String[] str1 = discountSales.getText().split(",");
            String discount = "";
            for (String string : str1) {
                discount += string;
            }
            pre.setFloat(6, Float.parseFloat(discount));

            String[] str2 = bonusFeeSales.getText().split(",");
            String bonus = "";
            for (String string : str2) {
                bonus += string;
            }
            pre.setFloat(7, Float.parseFloat(bonus));

            String[] str3 = subTotalSales.getText().split(",");
            String subtotal = "";
            for (String string : str3) {
                subtotal += string;
            }
            pre.setFloat(8, Float.parseFloat(subtotal));

            String[] str4 = totalSales.getText().split(",");
            String total = "";
            for (String string : str4) {
                total += string;
            }
            pre.setFloat(9, Float.parseFloat(total));

            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Bill added successfully.");
            }

            sql = "Update Orders set bill_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDBill);
            rss = pre.executeUpdate();

            sql = "Update Tables set status = 0 where table_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, hashmapTablesNo.get(Integer.parseInt(tableNo)));
            rss = pre.executeUpdate();

            showTablesItems();
            listTableViewDishOrder.clear();
            tableNoSales.setText("");
            intimeSales.setText("");
            outTimeSales.setText("");
            subTotalSales.setText("");
            discountSales.setText("");
            bonusFeeSales.setText("");
            totalSales.setText("");

//            String targetFolder = "D:/StudentMarks/";
//            int table_id = hashmapTablesNo.get(Integer.parseInt(tableNo));
//            int bill_id = maxIDBill;
//            exportStudentMarkToPdf(table_id, bill_id, targetFolder);
        }

    }

//    public void exportStudentMarkToPdf(int table_id, int bill_id, String targetFolder) {
//        try {
//            String source = getClass().getResource("/BillsPrint/report.jrxml").toExternalForm();
//            JasperReport jr = JasperCompileManager.compileReport(source);
//
//            Map<String, Object> params = new HashMap<>();
//            params.put("Table_id", table_id);
//            params.put("Bill_id", bill_id);
//
//            conn = Connect.ConnectDB.connectSQLServer();
//            JasperPrint jp = JasperFillManager.fillReport(jr, params, conn);
//
//            OutputStream os = new FileOutputStream(targetFolder + "Restaurant" + table_id + ".pdf");
//            JasperExportManager.exportReportToPdfStream(jp, os);
//            System.out.println(os);
//
//            os.flush();
//            os.close();
//        } catch (IOException | ClassNotFoundException | SQLException | JRException e) {
//        } finally {
//            try {
//                if (conn != null && !conn.isClosed()) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//            }
//        }
//    }
    @FXML
    private void inputBonus(KeyEvent event) {
        String parseToCurrency = Currency.parseToCurrency(bonusFeeSales.getText());
        bonusFeeSales.setText(parseToCurrency);
        int length = parseToCurrency.length();
        bonusFeeSales.positionCaret(length);
    }

    @FXML
    private void inputDiscount(KeyEvent event) {
        String parseToCurrency = Currency.parseToCurrency(discountSales.getText());
        discountSales.setText(parseToCurrency);
        int length = parseToCurrency.length();
        discountSales.positionCaret(length);
    }

    public void showTableItemListBill() throws ClassNotFoundException, SQLException {
        selectCategoryStorage();
        selectStorage();
        categoryBillCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        itemBillCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityBillCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select COUNT(OrderStorage.OrderStorage_id) as quantity , OrderStorage.storage_item_id, Storage.category_id as category_id\n"
                + "From OrderStorage inner join Storage\n"
                + "on OrderStorage.storage_item_id = Storage.storage_item_id \n"
                + "GROUP BY OrderStorage.storage_item_id, Storage.category_id";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            ItemsModel itemsModel = new ItemsModel(hashmapCategory.get(rs.getInt("category_id")), hashmapStorage.get(rs.getInt("storage_item_id")), rs.getInt("quantity"));
            listTableCategoryListBill.add(itemsModel);
        }
        tableCategoryListBill.setItems(listTableCategoryListBill);
    }

    public void showTableUserListBill() throws ClassNotFoundException, SQLException {
        selectUser();
        userBillCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityOfBillCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select user_id, Count(bill_id) as quantity From Bills GROUP BY user_id";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            BillsModel billsModel = new BillsModel(hashmapUserName.get(rs.getInt("user_id")), rs.getInt("quantity"));
            listTableUserListBill.add(billsModel);
        }
        tableUserListBill.setItems(listTableUserListBill);
    }

    @FXML
    private void clickRowTableUserListBill(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
        BillsModel billsModel = tableUserListBill.getSelectionModel().getSelectedItem();
        if (billsModel != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/showBillByEmployee.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ShowBillByEmployeeController controller = loader.getController();

            String user = billsModel.getName();
            controller.showBillByEmployee(user);
            scene.getStylesheets().add(getClass().getResource("/Css/Sales.css").toExternalForm());
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void showTablePassengers() throws ClassNotFoundException, SQLException {
        selectTable();
        tableBillsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        passengersCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select table_id, COUNT(bill_id) as Passengers From Bills GROUP BY table_id";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            BillsModel billsModel = new BillsModel("Table" + hashmapTables.get(rs.getInt("table_id")), rs.getInt("Passengers"));
            listTablepassengers.add(billsModel);
        }
        tablePassengersListBill.setItems(listTablepassengers);
    }

    // Out come
    public void showTableAmount() throws ClassNotFoundException, SQLException {
        listTableAmount.clear();
        selectUser();
        OutcomeIDCol.setCellValueFactory(new PropertyValueFactory<>("amount_id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        outcomeReasonCol.setCellValueFactory(new PropertyValueFactory<>("amountReason"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select amount_id, amount_reason, amount, user_id, time From OutAmount";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            OutcomeModel outcomeModel = new OutcomeModel(rs.getInt("amount_id"), rs.getFloat("amount"), rs.getString("amount_reason"), hashmapUserName.get(rs.getInt("user_id")), rs.getString("time"));
            listTableAmount.add(outcomeModel);
        }
        tableViewInAndOut.setItems(listTableAmount);
    }

    @FXML
    private void clickRowTableOutcome(MouseEvent event) {
        OutcomeModel outcomeModel = tableViewInAndOut.getSelectionModel().getSelectedItem();
        if (outcomeModel != null) {
            btnAddOutcome.setVisible(false);
            btnResetOutcome.setVisible(false);
            btnDeleteOutcome.setVisible(true);
            btnEditOutcome.setVisible(true);
            btnCancelOutcome.setVisible(true);
            txtOutcomeAmount.setText(Currency.toMoney(outcomeModel.getAmount()));
            txtOutcomeReason.setText(outcomeModel.getAmountReason());
            lblamountIDOutcome.setText(String.valueOf(outcomeModel.getAmount_id()));
        }
    }

    private int maxIDInandOut;

    public int maxIDInandOut() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(amount_id) as amount_id from OutAmount";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDInandOut = rs.getInt("amount_id");
        }
        return maxIDInandOut;
    }

    @FXML
    private void clickAddInAndOut(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (txtOutcomeReason.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please enter outcome amount.");
        } else if (txtOutcomeAmount.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please enter outcome reason.");
        } else {
            selectUser();
            maxIDInandOut();
            Date d = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            String amount = "";
            String[] split = txtOutcomeAmount.getText().split(",");

            for (String string : split) {
                amount += string;
            }

            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Insert into OutAmount values(?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, maxIDInandOut + 1);
            pre.setString(2, txtOutcomeReason.getText());
            pre.setFloat(3, Float.parseFloat(amount));
            pre.setInt(4, hashmapUser.get(userName));
            pre.setString(5, s.format(d));
            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Outcome amount added successfully.");
            }
            showTableAmount();
            txtOutcomeReason.setText("");
            txtOutcomeReason.setText("");
        }
    }

    @FXML
    private void clickEditInAndOut(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (txtOutcomeReason.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please enter outcome amount.");
        } else if (txtOutcomeAmount.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please enter outcome reason.");
        } else {
            selectUser();
            maxIDInandOut();
            String amount = "";
            String[] split = txtOutcomeAmount.getText().split(",");

            for (String string : split) {
                amount += string;
            }

            conn = Connect.ConnectDB.connectSQLServer();
            sql = "Update OutAmount set amount_reason = ?, amount = ? Where amount_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, txtOutcomeReason.getText());
            pre.setInt(2, Integer.parseInt(amount));
            pre.setInt(3, Integer.parseInt(lblamountIDOutcome.getText()));
            rss = pre.executeUpdate();
            if (rss > 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Outcome successfully updated.");
            }
            showTableAmount();
            txtOutcomeReason.setText("");
            txtOutcomeAmount.setText("");
            btnAddOutcome.setVisible(true);
            btnResetOutcome.setVisible(true);
            btnDeleteOutcome.setVisible(false);
            btnEditOutcome.setVisible(false);
            btnCancelOutcome.setVisible(false);
        }
    }

    @FXML
    private void clickDeleteInAndOut(ActionEvent event) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Delete OutAmount Where amount_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, Integer.parseInt(lblamountIDOutcome.getText()));
        rss = pre.executeUpdate();
        if (rss > 0) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "successfully delete.");
        }
        showTableAmount();
        txtOutcomeReason.setText("");
        txtOutcomeAmount.setText("");
        btnAddOutcome.setVisible(true);
        btnResetOutcome.setVisible(true);
        btnDeleteOutcome.setVisible(false);
        btnEditOutcome.setVisible(false);
        btnCancelOutcome.setVisible(false);
    }

    @FXML
    private void clickResetOutcome(ActionEvent event) {
        txtOutcomeReason.setText("");
        txtOutcomeAmount.setText("");
    }

    @FXML
    private void clickCancelOutcome(ActionEvent event) {
        txtOutcomeReason.setText("");
        txtOutcomeAmount.setText("");
        btnAddOutcome.setVisible(true);
        btnResetOutcome.setVisible(true);
        btnDeleteOutcome.setVisible(false);
        btnEditOutcome.setVisible(false);
        btnCancelOutcome.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addValue_ComboboxCategory();
            showTablesItems();
            showDishSales();
            showTableAmount();
            showTablePassengers();
            showTableUserListBill();
            showTableItemListBill();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void inputAmout(KeyEvent event) {
        String parseToCurrency = Currency.parseToCurrency(txtOutcomeAmount.getText());
        txtOutcomeAmount.setText(parseToCurrency);
        int length = parseToCurrency.length();
        txtOutcomeAmount.positionCaret(length);
    }

    @FXML
    private void clickReload(ActionEvent event) {
        try {
            addValue_ComboboxCategory();
            showTablesItems();
            showDishSales();
            showTableAmount();
            showTableItemListBill();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
