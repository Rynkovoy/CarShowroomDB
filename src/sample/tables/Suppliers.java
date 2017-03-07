package sample.tables;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.classes.MyDataBase;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Rynkovoy on 10/31/2016.
 */
public class Suppliers {
    String supplyID;
    String name;
    String country;
    String address;
    String city;
    String deadLine;
    String deliveryTime;

    public Suppliers() {
    }

    public Suppliers(String supplyID, String name, String country, String address, String city, String deadLine, String deliveryTime) {
        this.supplyID = supplyID;
        this.name = name;
        this.country = country;
        this.address = address;
        this.city = city;
        this.deadLine = deadLine;
        this.deliveryTime = deliveryTime;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getSupplier(int id) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Название FROM Поставщики WHERE КодПоставщика="+id);
        rs.next();
        return rs.getString("Название");
    }

    public String getSupplierID(String name) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT КодПоставщика FROM Поставщики WHERE Название=\""+name+"\"");
        rs.next();
        return rs.getString("КодПоставщика");
    }

    private Scene scene;
    private Parent root;
    public Scene getScene() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../UI/suppliers.fxml"));
        scene = new Scene(root);
        return scene;
    }
}
