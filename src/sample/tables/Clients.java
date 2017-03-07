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
public class Clients {

    String id;
    String name;
    String address;
    String category;
    String country;
    String city;
    String phone;

    public Clients(){}

    public Clients(String id, String name, String address, String category, String country, String city, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.country = country;
        this.city = city;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getName(int id) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Название FROM Клиенты WHERE КодКлиента="+id);
        rs.next();
        return rs.getString("Название");
    }

    public String getClientID(String name) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT КодКлиента FROM Клиенты WHERE Название=\""+name+"\"");
        rs.next();
        return rs.getString("КодКлиента");
    }

    private Scene scene;
    private Parent root;
    public Scene getScene() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../UI/clients.fxml"));
        scene = new Scene(root);
        return scene;
    }
}
