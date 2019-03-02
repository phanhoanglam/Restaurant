/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class Restaurant extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Css/Sales.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        File file = new File("");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/iconTittle.jpg").toExternalForm()));
//        primaryStage.getIcons().add(new Image("file:///" + file.getAbsolutePath() + "/iconTittle.jpg"));
        primaryStage.setTitle("Restaurant APP");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
