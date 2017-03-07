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
public class Assortment {

    int id;
    String name;
    String model;
    String bodyType;
    String supplier;
    double cost;
    String gearbox;


    public Assortment() {}

    public Assortment(int id,String name, String model, String bodyType, String supplier, double cost, String gearbox) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.bodyType = bodyType;
        this.supplier = supplier;
        this.cost = cost;
        this.gearbox = gearbox;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getSupplier() {
        return supplier;
    }

    public double getCost() {
        return cost;
    }

    public String getGearbox() {
        return gearbox;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getCarName(int id) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Марка FROM Ассортимент WHERE Код="+id);
        rs.next();
        return rs.getString("Марка");
    }
    public String getCarModel(int id) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Модель FROM Ассортимент WHERE Код="+id);
        rs.next();
        return rs.getString("Модель");
    }
    public String getCarID(String model) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Код FROM Ассортимент WHERE Модель=\""+model+"\"");
        rs.next();
        return rs.getString("Код");
    }

    private Scene scene;
    private Parent root;
    public Scene getScene() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../UI/assortment.fxml"));
        scene = new Scene(root);
        return scene;
    }
}
