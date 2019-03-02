/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.*;
import Controller.SalesController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Administrator
 */
public class Notification {

    public static void showMessageDialog(StackPane root, Node nodeToBeBlurred, String body) {
        BoxBlur boxBlur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton buttoncontrol = new JFXButton("OK");
        buttoncontrol.setPrefWidth(88);
        buttoncontrol.setPrefHeight(25);
        buttoncontrol.getStyleClass().add("dialog-button");

        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);
        buttoncontrol.setOnMouseClicked((event) -> {
            dialog.close();
        });

        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(buttoncontrol);
        dialog.show();
        dialog.setOnDialogClosed((event) -> {
            nodeToBeBlurred.setEffect(null);
        });
        nodeToBeBlurred.setEffect(boxBlur);

    }

    public static void showTimeDialog(StackPane root, Node nodeToBeBlurred, String body, Label label, TextField textField) {
        BoxBlur boxBlur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton yesButton = new JFXButton("YES");
        yesButton.setPrefWidth(88);
        yesButton.setPrefHeight(25);
        yesButton.getStyleClass().add("dialog-button");

        JFXButton noButton = new JFXButton("NO");
        noButton.setPrefWidth(88);
        noButton.setPrefHeight(25);
        noButton.getStyleClass().add("dialog-button");

        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);
        noButton.setOnMouseClicked((event) -> {
            textField.setDisable(false);
            dialog.close();
        });
        yesButton.setOnMouseClicked((event) -> {
            Date d = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            label.setText(s.format(d));
            textField.setText("");
            textField.setDisable(true);
            dialog.close();
        });

        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(yesButton, noButton);
        dialog.show();
        dialog.setOnDialogClosed((event) -> {
            nodeToBeBlurred.setEffect(null);
        });
        nodeToBeBlurred.setEffect(boxBlur);
    }
    
    public static void showCancelDialog(StackPane root, Node nodeToBeBlurred, String body, Label label1, Label label2, String table_no) {
        BoxBlur boxBlur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton yesButton = new JFXButton("YES");
        yesButton.setPrefWidth(88);
        yesButton.setPrefHeight(25);
        yesButton.getStyleClass().add("dialog-button");

        JFXButton noButton = new JFXButton("NO");
        noButton.setPrefWidth(88);
        noButton.setPrefHeight(25);
        noButton.getStyleClass().add("dialog-button");

        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);
        noButton.setOnMouseClicked((event) -> {
            dialog.close();
        });
        yesButton.setOnMouseClicked((event) -> {
            try {
                label1.setText("");
                label2.setText("");
                Connection conn = Connect.ConnectDB.connectSQLServer();
                String sql = "Update Tables set status = 0 Where table_no = ?";
                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setInt(1, Integer.parseInt(table_no));
                int rss = pre.executeUpdate();
                dialog.close();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(yesButton, noButton);
        dialog.show();
        dialog.setOnDialogClosed((event) -> {
            nodeToBeBlurred.setEffect(null);
        });
        nodeToBeBlurred.setEffect(boxBlur);
    }
    
}
