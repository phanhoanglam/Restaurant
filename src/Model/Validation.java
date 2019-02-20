/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Administrator
 */
public class Validation {
    public static boolean isTextFieldisEmpty(JFXTextField tf, Label lb, String message) {
        boolean b = true;
        if (tf.getText().trim().isEmpty()) {
            lb.setStyle("-fx-text-fill:red");
            lb.setText(message);
            Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/Close-icon.png");
            lb.setGraphic(new ImageView(image));
            b = false;
        }
        return b;
    }

    public static boolean isTextFieldtypeNumber(JFXTextField tf, Label lb, String message) {
        boolean b = true;
        if (!tf.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            lb.setStyle("-fx-text-fill:red");
            lb.setText(message);
            Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/Close-icon.png");
            lb.setGraphic(new ImageView(image));
            b = false;
        }
        return b;
    }

    public static boolean isPassFieldisEmpty(JFXPasswordField tf, Label lb, String message) {
        boolean b = true;
        if (tf.getText().trim().isEmpty()) {
            lb.setStyle("-fx-text-fill:red");
            lb.setText(message);
            Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/Close-icon.png");
            lb.setGraphic(new ImageView(image));
            b = false;
        }
        return b;
    }
    

    public static void isTextFieldHandleisEmpty(JFXTextField tf, Label lb, String message) {
        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (tf.getText().trim().isEmpty()) {
                    lb.setStyle("-fx-text-fill:red");
                    lb.setText(message);
                    Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/Close-icon.png");
                    lb.setGraphic(new ImageView(image));
                } else {
                    lb.setText(message);
                    lb.setStyle("-fx-text-fill:green");
                    Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/checked.png");
                    lb.setGraphic(new ImageView(image));
                }
            }
        });
    }

    public static void isTextFieldHandleTypeNumber(JFXTextField tf, Label lb, String message) {
        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (tf.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
                    lb.setText(message);
                    lb.setStyle("-fx-text-fill:green");
                    Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/checked.png");
                    lb.setGraphic(new ImageView(image));
                } else {
                    lb.setStyle("-fx-text-fill:red");
                    lb.setText(message);
                    Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/Close-icon.png");
                    lb.setGraphic(new ImageView(image));
                }
            }
        });
    }

    public static void isPassFieldHandleisEmpty(JFXPasswordField tf, Label lb, String message) {
        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (tf.getText().trim().isEmpty()) {
                    lb.setStyle("-fx-text-fill:red");
                    lb.setText(message);
                    Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/Close-icon.png");
                    lb.setGraphic(new ImageView(image));
                } else {
                    lb.setText(message);
                    lb.setStyle("-fx-text-fill:green");
                    Image image = new Image("file:///C:/Users/Administrator/Documents/NetBeansProjects/Restaurant_Project/src/images/checked.png");
                    lb.setGraphic(new ImageView(image));
                }
            }
        });
    }

}
