package sample.controllers;

/**
 * Created by Rynkovoy on 10/31/2016.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.classes.MyStage;

import java.io.IOException;
import java.sql.SQLException;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.classes.MyDataBase;
import sample.classes.MyStage;
import sample.tables.Assortment;

import java.io.IOException;
import java.sql.*;


public class Enter {

    MyStage stage = new MyStage();
    //MyDataBase mdb;

    public void mainStage(Stage primaryStage) throws IOException {
        stage.setStage(primaryStage);
        stage.getStage().setTitle("Продажа автомобилей");
        stage.getStage().setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("../UI/enter.fxml"));
        Scene enterScene = new Scene(root);

        stage.getStage().setScene(enterScene);

        stage.getStage().show();
    }

    public void enter(ActionEvent actionEvent) throws IOException, SQLException {
        Assortment assortmentTable = new Assortment();
        stage.getStage().setScene(assortmentTable.getScene());
        stage.getStage().show();

    }
}































