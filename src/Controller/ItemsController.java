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
import com.jfoenix.controls.JFXTextField;
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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ItemsController implements Initializable {

    @FXML
    private StackPane stackPaneItems;
    @FXML
    private AnchorPane anchorPaneItems;

    @FXML
    private JFXButton btnImagesItems;
    @FXML
    private ImageView imageViewItems;
    @FXML
    private StackPane stackPaneTable;
    @FXML
    private StackPane stackPaneStatistical;
    @FXML
    private StackPane stackPaneItem;
    private JFXTextField txtNumberTable;

    private int maxIDTable;
    private int maxNameTable;
    private int maxStorage_item_id;

    @FXML
    private Button btnAddTable;
    @FXML
    private Button btnDeleteTable;

    @FXML
    private FlowPane flowPaneTable;

    private Connection conn;
    private Statement st;
    private PreparedStatement pre;
    private ResultSet rs;
    private int rss;
    private String sql;
    private ArrayList<TableModel> listTables = new ArrayList<>();
    private ObservableList listTableUser = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<String> cbbItemCategory;
    @FXML
    private JFXTextField txtItemName;
    @FXML
    private Button btnAddItems;
    @FXML
    private JFXTextField txtAmountItems;
    @FXML
    private JFXTextField txtItemQuantity;

    private HashMap<String, Integer> hashmapComboboxCategory;
    private HashMap<Integer, String> hashmapTableItemsCategory;
    private ObservableList<String> listComboboxCategory = FXCollections.observableArrayList();

    private ObservableList<ItemsModel> listItems = FXCollections.observableArrayList();

    @FXML
    private TableView<ItemsModel> tableItems;
    @FXML
    private TableColumn<ItemsModel, String> categoryCol;
    @FXML
    private TableColumn<ItemsModel, String> nameCol;
    @FXML
    private TableColumn<ItemsModel, Integer> quantityCol;
    @FXML
    private TableColumn<ItemsModel, Integer> storageIdCol;
    @FXML
    private TableColumn<ItemsModel, Float> priceCol;
    @FXML
    private TableColumn<ItemsModel, String> imagesCol;
    @FXML
    private Button btnDeleteItems;
    @FXML
    private Button btnEditItems;
    @FXML
    private Button btnResetItems;
    @FXML
    private Button btnCancelItems;
    @FXML
    private TextField txtStorageID;
    @FXML
    private JFXTextField linkImages;
    @FXML
    private JFXTextField txtSearchItemName;
    @FXML
    private JFXComboBox<String> cbbSearchCategory;

    @FXML
    private void clickItems(ActionEvent event) {
        stackPaneItem.setVisible(true);
        stackPaneTable.setVisible(false);
        stackPaneStatistical.setVisible(false);
    }

    @FXML
    private void clickStatistical(ActionEvent event) {
        stackPaneItem.setVisible(false);
        stackPaneTable.setVisible(false);
        stackPaneStatistical.setVisible(true);
    }

    @FXML
    private void clickTable(ActionEvent event) {
        stackPaneItem.setVisible(false);
        stackPaneTable.setVisible(true);
        stackPaneStatistical.setVisible(false);
    }

    @FXML
    private void clickComeBackManage(ActionEvent event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackPaneItems);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((event1) -> {
            try {
                Parent root = (StackPane) FXMLLoader.load(getClass().getResource("/FXML/Manage.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/Css/Manage.css").toExternalForm());
                Stage stage = (Stage) stackPaneItems.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fadeTransition.play();
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void addValue_ComboboxCategory() throws ClassNotFoundException, SQLException {
        hashmapComboboxCategory = new HashMap<>();
        hashmapTableItemsCategory = new HashMap<>();
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select category_id, name from Category";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            hashmapComboboxCategory.put(rs.getString("name"), rs.getInt("category_id"));
            hashmapTableItemsCategory.put(rs.getInt("category_id"), rs.getString("name"));
        }
        for (String key : hashmapComboboxCategory.keySet()) {
            listComboboxCategory.add(key);
        }
        cbbItemCategory.setItems(listComboboxCategory);
        cbbSearchCategory.setItems(listComboboxCategory);
    }

    @FXML
    private void clickCategorySearchNull(ActionEvent event) {
        cbbSearchCategory.setValue(null);
    }

    private void showTableItems() throws ClassNotFoundException, SQLException {
        listItems.clear();
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        storageIdCol.setCellValueFactory(new PropertyValueFactory<>("storage_item_id"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        imagesCol.setCellValueFactory(new PropertyValueFactory<>("images"));

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select storage_item_id, category_id, name, quantity, price, images from Storage";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println(hashmapTableItemsCategory.get(3));
            ItemsModel model = new ItemsModel(rs.getInt("storage_item_id"), hashmapTableItemsCategory.get(rs.getInt("category_id")), rs.getString("name"), rs.getFloat("price"), rs.getInt("quantity"), rs.getString("images"));
            listItems.add(model);
        }
        tableItems.setItems(listItems);

        FilteredList<ItemsModel> filteredItems = new FilteredList<>(listItems, d -> true);
        SortedList<ItemsModel> sortedItems = new SortedList<>(filteredItems);

        txtSearchItemName.textProperty().addListener((observable, oldValue, newValue) -> {
            cbbSearchCategory.setValue(null);
            filteredItems.setPredicate((Predicate<? super ItemsModel>) (model) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (model.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        sortedItems.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedItems);

        cbbSearchCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtSearchItemName.setText("");
            filteredItems.setPredicate((Predicate<? super ItemsModel>) (model) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (model.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        sortedItems.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedItems);
    }

    public int maxIDStorage() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(storage_item_id) as storage_item_id from Storage";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxStorage_item_id = rs.getInt("storage_item_id");
        }
        return maxStorage_item_id;
    }

    @FXML
    private void clickImagesItems(ActionEvent event) throws ClassNotFoundException, SQLException {
        maxIDStorage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageViewItems.setImage(image);
            linkImages.setText(file.getAbsolutePath());
        }
    }

    private ItemsModel checkName(String username) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select name from Storage where name = ?";
        pre = conn.prepareStatement(sql);
        pre.setString(1, username);
        rs = pre.executeQuery();
        if (rs.next()) {
            ItemsModel model = new ItemsModel(rs.getString("name"));
            pre.close();
            conn.close();
            return model;
        } else {
            pre.close();
            conn.close();
            return null;
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
    private void clickAddItems(ActionEvent event) throws ClassNotFoundException, SQLException {
        ItemsModel model = checkName(txtItemName.getText());
        if (txtItemName.getText().trim().isEmpty() || txtItemQuantity.getText().trim().isEmpty() || txtAmountItems.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Cannot be isEmpty");
        } else if (cbbItemCategory.getValue() == null) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Please select a category name");
        } else if (imageViewItems.getImage() == null) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Please select images");
        } else if (model != null) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Duplicate name");
            txtItemName.setText("");
        } else if (!isTextFieldtypeNumber(txtItemQuantity)) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Quantity must be number");
            txtItemQuantity.setText("");
        } else if (!isTextFieldtypeNumber(txtAmountItems)) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Amount must be number");
            txtAmountItems.setText("");
        } else {
            try {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
                File file = new File("");
                File f = new File(file.getAbsolutePath() + "/src/imagesDish/" + s.format(d) + ".jpg");
                ImageIO.write(SwingFXUtils.fromFXImage(imageViewItems.getImage(), null), "jpg", f);

                conn = Connect.ConnectDB.connectSQLServer();
                sql = "Insert into Storage values(?,?,?,?,?,?)";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, maxStorage_item_id + 1);
                pre.setInt(2, hashmapComboboxCategory.get(cbbItemCategory.getValue()));
                pre.setString(3, txtItemName.getText());
                pre.setFloat(4, Float.parseFloat(txtAmountItems.getText()));
                pre.setInt(5, Integer.parseInt(txtItemQuantity.getText()));
                pre.setString(6, f.getAbsolutePath());
                rss = pre.executeUpdate();
                if (rss > 0) {
                    System.out.println("ok");
                }
                showTableItems();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            linkImages.setText("");
            txtItemName.setText("");
            txtItemQuantity.setText("");
            txtAmountItems.setText("");
            imageViewItems.setImage(null);
            cbbItemCategory.setValue(null);
        }

    }

    @FXML
    private void clickRowTableItems() {
        ItemsModel selectedItems = tableItems.getSelectionModel().getSelectedItem();
        if (selectedItems != null) {
            btnAddItems.setVisible(false);
            btnCancelItems.setVisible(true);
            btnDeleteItems.setVisible(true);
            btnEditItems.setVisible(true);
            btnResetItems.setVisible(false);

            txtStorageID.setText(String.valueOf(selectedItems.getStorage_item_id()));
            txtItemName.setText(selectedItems.getName());
            txtItemQuantity.setText(String.valueOf(selectedItems.getQuantity()));
            txtAmountItems.setText(String.valueOf(selectedItems.getPrice()));
            linkImages.setText(selectedItems.getImages());

            Image image = new Image("file:///" + selectedItems.getImages());
            imageViewItems.setImage(image);

            cbbItemCategory.setValue(selectedItems.getCategory());
        }
    }

    @FXML
    private void clickDeleteItems(ActionEvent event) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Delete Storage where storage_item_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, Integer.parseInt(txtStorageID.getText()));
        rss = pre.executeUpdate();

        File file = new File(linkImages.getText());
        file.delete();

        showTableItems();

        btnAddItems.setVisible(true);
        btnCancelItems.setVisible(false);
        btnDeleteItems.setVisible(false);
        btnEditItems.setVisible(false);
        btnResetItems.setVisible(true);

        linkImages.setText("");
        txtItemName.setText("");
        txtItemQuantity.setText("");
        txtAmountItems.setText("");
        imageViewItems.setImage(null);
        cbbItemCategory.setValue(null);
    }

    private ItemsModel checkLinkImages(int storageID) throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select images from Storage where storage_item_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, storageID);
        rs = pre.executeQuery();
        if (rs.next()) {
            ItemsModel model = new ItemsModel(rs.getString("images"));
            pre.close();
            conn.close();
            return model;
        } else {
            pre.close();
            conn.close();
            return null;
        }
    }

    @FXML
    private void clickEditItems(ActionEvent event) throws ClassNotFoundException, SQLException {
        ItemsModel itemsModel = checkLinkImages(Integer.parseInt(txtStorageID.getText()));
        ItemsModel model = checkName(txtItemName.getText());
        if (txtItemName.getText().trim().isEmpty() || txtItemQuantity.getText().trim().isEmpty() || txtAmountItems.getText().trim().isEmpty()) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Cannot be isEmpty");
        } else if (cbbItemCategory.getValue() == null) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Please select a category name");
        } else if (imageViewItems.getImage() == null) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Please select images");
        } else if (model != null) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Duplicate name");
            txtItemName.setText("");
        } else if (!isTextFieldtypeNumber(txtItemQuantity)) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Quantity must be number");
            txtItemQuantity.setText("");
        } else if (!isTextFieldtypeNumber(txtAmountItems)) {
            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, "Amount must be number");
            txtAmountItems.setText("");
        } else if (itemsModel.getName().equals(linkImages.getText())) {
            try {
                conn = Connect.ConnectDB.connectSQLServer();
                sql = "Update Storage set category_id = ? ,name = ? ,price = ? ,quantity = ? Where storage_item_id = ?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, hashmapComboboxCategory.get(cbbItemCategory.getValue()));
                pre.setString(2, txtItemName.getText());
                pre.setFloat(3, Float.parseFloat(txtAmountItems.getText()));
                pre.setInt(4, Integer.parseInt(txtItemQuantity.getText()));
                pre.setInt(5, Integer.parseInt(txtStorageID.getText()));
                rss = pre.executeUpdate();
                if (rss > 0) {
                    System.out.println("ok");
                }
                showTableItems();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            linkImages.setText("");
            txtItemName.setText("");
            txtItemQuantity.setText("");
            txtAmountItems.setText("");
            imageViewItems.setImage(null);
            cbbItemCategory.setValue(null);
        } else {
            try {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
                File file = new File("");
                File f = new File(file.getAbsolutePath() + "/src/imagesDish/" + s.format(d) + ".jpg");
                ImageIO.write(SwingFXUtils.fromFXImage(imageViewItems.getImage(), null), "jpg", f);

                conn = Connect.ConnectDB.connectSQLServer();
                sql = "Update Storage set category_id = ? ,name = ? ,price = ? ,quantity = ? , images = ? Where storage_item_id = ?";
                pre = conn.prepareStatement(sql);
                pre.setInt(1, hashmapComboboxCategory.get(cbbItemCategory.getValue()));
                pre.setString(2, txtItemName.getText());
                pre.setFloat(3, Float.parseFloat(txtAmountItems.getText()));
                pre.setInt(4, Integer.parseInt(txtItemQuantity.getText()));
                pre.setString(5, f.getAbsolutePath());
                pre.setInt(6, Integer.parseInt(txtStorageID.getText()));
                rss = pre.executeUpdate();
                if (rss > 0) {
                    System.out.println("ok");
                }
                showTableItems();
            } catch (ClassNotFoundException | SQLException | IOException ex) {
                Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            File file = new File(itemsModel.getName());
            file.delete();
            linkImages.setText("");
            txtItemName.setText("");
            txtItemQuantity.setText("");
            txtAmountItems.setText("");
            imageViewItems.setImage(null);
            cbbItemCategory.setValue(null);
        }
    }

    @FXML
    private void clickResetItems(ActionEvent event) {
        linkImages.setText("");
        txtItemName.setText("");
        txtItemQuantity.setText("");
        txtAmountItems.setText("");
        imageViewItems.setImage(null);
        cbbItemCategory.setValue(null);
    }

    @FXML
    private void clickCancelItems(ActionEvent event) {
        btnAddItems.setVisible(true);
        btnCancelItems.setVisible(false);
        btnDeleteItems.setVisible(false);
        btnEditItems.setVisible(false);
        btnResetItems.setVisible(true);

        linkImages.setText("");
        txtItemName.setText("");
        txtItemQuantity.setText("");
        txtAmountItems.setText("");
        imageViewItems.setImage(null);
        cbbItemCategory.setValue(null);
    }

    public int maxIDTable() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(table_id) as table_id from Tables";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxIDTable = rs.getInt("table_id");
        }
        return maxIDTable;
    }

    public int maxNameTable() throws ClassNotFoundException, SQLException {
        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Select Max(table_no) as table_no from Tables";
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            maxNameTable = rs.getInt("table_no");
        }
        return maxNameTable;
    }

    public void showTablesItems() throws ClassNotFoundException, SQLException {
        listTables.removeAll(listTables);
        listTableUser.clear();

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
            Image image = new Image("file:///"+file.getAbsolutePath() + "/src/images/table_icon.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(80);
            Button button = new Button("Table " + listTables.get(i).getTableNo(), imageView);
            button.setPrefSize(200, 107);
            FlowPane.setMargin(button, new Insets(30, 15, 30, 30));
            if (listTables.get(i).getStatus() == 0) {
//                button.getStyleClass().add("buttonTables");
            } else {
                button.setStyle("-fx-background-color: red;-fx-border-color: black;\n"
                        + "    -fx-border-width: 3;");
//                button.getStyleClass().add("buttonTablesStatus");
            }
            listTableUser = flowPaneTable.getChildren();
            listTableUser.add(button);
        }
    }

    @FXML
    private void clickAddNumberTable(ActionEvent event) throws ClassNotFoundException, SQLException {
        maxIDTable();
        maxNameTable();

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Insert into Tables values(?,?,?)";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, maxIDTable + 1);
        pre.setInt(2, maxNameTable + 1);
        pre.setInt(3, 0);
        rss = pre.executeUpdate();
        if (rss > 0) {
//            Notification.showMessageDialog(stackPaneItems, anchorPaneItems, txtNumberTable.getText(), "Insert thanh cong ");
            System.out.println("OK");
        }
        showTablesItems();
    }

    @FXML
    private void clickDeleteNumberTable(ActionEvent event) throws ClassNotFoundException, SQLException {
        maxIDTable();
        maxNameTable();

        conn = Connect.ConnectDB.connectSQLServer();
        sql = "Delete Tables where table_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, maxIDTable);
        rss = pre.executeUpdate();
        if (rss > 0) {
            System.out.println("Delete Ok");
        }
        showTablesItems();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addValue_ComboboxCategory();
            showTableItems();   // Items
            showTablesItems();  // Tables
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
