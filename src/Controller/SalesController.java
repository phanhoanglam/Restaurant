package Controller;

import Util.Currency;
import static Controller.ItemsController.isTextFieldtypeNumber;
import Model.BillsModel;
import Model.ItemsModel;
import Util.Notification;
import Model.OrderModel;
import Model.TableModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
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
import java.util.ResourceBundle;
import java.util.TimerTask;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private Label totalSales;
    @FXML
    private JFXTextField bonusFeeSales;
    @FXML
    private Label subTotalSales;
    @FXML
    private JFXTextField discountSales;

    @FXML
    private TableView<?> listBillCategoryListBill;

    @FXML
    private TableView<?> tablePassengersListBill;

    private ObservableList<BillsModel> listTableUserListBill = FXCollections.observableArrayList();
    @FXML
    private TableView<BillsModel> tableUserListBill;
    @FXML
    private TableColumn<BillsModel, String> userCol;
    @FXML
    private TableColumn<BillsModel, Integer> quantityOfBillCol;

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
            tableViewOrderDish.getItems().removeAll(selectedItem);
            ItemsModel itemsModel = new ItemsModel(selectedItem.getStorage_item_id(), selectedItem.getName(),
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

    @FXML
    private void clickLoadOrder(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (!tableNoSales.getText().isEmpty()) {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            OrderModel model = checkOrderTable(Integer.parseInt(tableNo));
            if (tableViewOrderDish.getItems().isEmpty()) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Vui long chon mon an");
            } else if (model != null) {
                for (int i = 0; i < listTableViewDishOrder.size(); i++) {
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
                    }
                }
            } else {
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
                }
            }
        } else {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Vui long chon ban");
        }
    }

    @FXML
    private void clickSepetateSales(ActionEvent event) {
    }

    @FXML
    private void clickGatherSales(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chon table truoc khi gop ban");
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            checkTablesStatus(Integer.parseInt(tableNo));
            if (status == 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Ban con trong khong the gop");
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
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void clickTransferSales(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chon table truoc khi chuyen ban");
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            checkTablesStatus(Integer.parseInt(tableNo));
            if (status == 0) {
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Ban con trong khong the chuyen");
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
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a table");
        } else if (!tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da order mon khong the Cancel");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da hoan thanh khong the cancel");
        } else {
            String[] split = tableNoSales.getText().split("\\s");
            String tableNo = split[1];
            Notification.showCancelDialog(stackPaneSales, anchorPaneSales, "Ban chac chan cancel", tableNoSales, intimeSales, tableNo);
        }
    }

    @FXML
    private void clickFinishSales(ActionEvent event
    ) {
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
    private void clickStartSales(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (tableNoSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Please select a table");
        } else if (!tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da goi mon khong the start intime");
        } else if (!outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Da hoan thanh khong thẻ start ");
        } else if (!intimeSales.getText().isEmpty()) {
            Notification.showTimeDialog(stackPaneSales, anchorPaneSales, "You want to reset intime?", intimeSales, null);
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
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua pay subtotal");
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
        if (tableViewOrderDish.getItems().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chon mon an");
        } else {
            float tong = 0;
            System.out.println(listTableViewDishOrder.size());
            for (int i = 0; i < listTableViewDishOrder.size(); i++) {
                tong += listTableViewDishOrder.get(i).getPrice();
            }
            subTotalSales.setText(String.valueOf(Currency.toMoney(tong)));
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
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua click subtotal");
        } else if (totalSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua click Pay");
        } else if (outTimeSales.getText().isEmpty()) {
            Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Chua outime");
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
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Them bills thanh cong");
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
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Them bills thanh cong");
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
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Them bills thanh cong");
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
                Notification.showMessageDialog(stackPaneSales, anchorPaneSales, "Them bills thanh cong");
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
        }

//            String source = "C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/BillsPrint/Bills.jrxml";
//          JasperReport jp = 
//                  JasperCompileManager.compileReport
//        (source);
//          HashMap para =  new HashMap();
//          JRBeanCollectionDataSource jcd = new JRBeanCollectionDataSource(data());
//          JasperPrint print = JasperFillManager.fillReport(jp, para, jcd);
//          
//          JasperViewer.viewReport(print, false);
    }

    private Collection data() {
        ArrayList<BillsModel> model = new ArrayList<>();
        String table = tableNoSales.getText();
        String timein = intimeSales.getText();
        String timeout = outTimeSales.getText();
        String storage = "";
        int quantity = 0;
        String price = "";
        for (int i = 0; i < listTableViewDishOrder.size(); i++) {
            storage = listTableViewDishOrder.get(i).getName();
            quantity = listTableViewDishOrder.get(i).getQuantity();
            price = Currency.toMoney(listTableViewDishOrder.get(i).getPrice());
        }
        String discount = discountSales.getText();
        String bonus = bonusFeeSales.getText();
        String subtotal = subTotalSales.getText();
        String total = totalSales.getText();
        BillsModel billsModel = new BillsModel(table, timein, timeout, storage, quantity, price, discount, bonus, subtotal, total);
        return model;
    }

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

    public void showTableUserListBill() throws ClassNotFoundException, SQLException {
        selectUser();
        userCol.setCellValueFactory(new PropertyValueFactory<>("storage"));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addValue_ComboboxCategory();
            showTablesItems();
            showDishSales();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickReload(ActionEvent event) {
        try {
            addValue_ComboboxCategory();
            showTablesItems();
            showDishSales();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
