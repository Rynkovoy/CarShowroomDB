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
public class Managers {
    String managerID;
    String name;
    String phone;

    public Managers(){}

    public Managers(String managerID,String name, String phone) {
        this.managerID = managerID;
        this.name = name;
        this.phone = phone;
    }

    public String getManagerID() {
        return managerID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getManager(int id) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Фамилия FROM Менеджеры WHERE ТабельныйНомер="+id);
        rs.next();
        return rs.getString("Фамилия");
    }
    public String getManagerID(String name) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT ТабельныйНомер FROM Менеджеры WHERE Фамилия=\""+name+"\"");
        rs.next();
        return rs.getString("ТабельныйНомер");
    }

    private Scene scene;
    private Parent root;
    public Scene getScene() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../UI/managers.fxml"));
        scene = new Scene(root);
        return scene;
    }
}
