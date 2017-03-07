package sample.tables;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.classes.MyDataBase;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Rynkovoy on 10/31/2016.
 */
public class Orders {
    int ordersID;
    String clientID;
    String carID;
    String color;
    String count;
    Date conclusionDate;
    String managerID;
    String payment;
    Date dispatchDate;
    Date deliveryDate;
    Date notificationDate;


    public Orders(){}

    public Orders(int ordersID, String clientID, String carID, String color, String count, Date conclusionDate, String managerID, String payment, Date dispatchDate, Date deliveryDate, Date notificationDate) {
        this.ordersID = ordersID;
        this.clientID = clientID;
        this.carID = carID;
        this.color = color;
        this.count = count;
        this.conclusionDate = conclusionDate;
        this.managerID = managerID;
        this.payment = payment;
        this.dispatchDate = dispatchDate;
        this.deliveryDate = deliveryDate;
        this.notificationDate = notificationDate;
    }

    public int getOrdersID() {
        return ordersID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getCarID() {
        return carID;
    }

    public String getColor() {
        return color;
    }

    public String getCount() {
        return count;
    }

    public Date getConclusionDate() {
        return conclusionDate;
    }

    public String getManagerID() {
        return managerID;
    }

    public String getPayment() {
        return payment;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public int getOrder(int id) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT КодЗаказа FROM Заказы WHERE КодЗаказа="+id);
        rs.next();
        return rs.getInt("КодЗаказа");
    }

    private Scene scene;
    private Parent root;
    public Scene getScene() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../UI/orders.fxml"));
        scene = new Scene(root);
        return scene;
    }
}
