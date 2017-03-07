package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import sample.classes.MyDataBase;
import sample.classes.MyStage;
import sample.tables.*;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Rynkovoy on 10/31/2016.
 */
public class OrdersController {
    public void showAssortment(ActionEvent actionEvent) throws IOException {
        MyStage stage = new MyStage();
        Assortment assortmentTable = new Assortment();
        stage.getStage().setScene(assortmentTable.getScene());
        stage.getStage().show();
    }

    public void showOrders(ActionEvent actionEvent) throws IOException {
        MyStage stage = new MyStage();
        Orders ordersTable = new Orders();
        stage.getStage().setScene(ordersTable.getScene());
        stage.getStage().show();
    }

    public void showClients(ActionEvent actionEvent) throws IOException {
        MyStage stage = new MyStage();
        Clients clientsTable = new Clients();
        stage.getStage().setScene(clientsTable.getScene());
        stage.getStage().show();
    }

    public void showManagers(ActionEvent actionEvent) throws IOException {
        MyStage stage = new MyStage();
        Managers managersTable = new Managers();
        stage.getStage().setScene(managersTable.getScene());
        stage.getStage().show();
    }

    public void showSuppliers(ActionEvent actionEvent) throws IOException {
        MyStage stage = new MyStage();
        Suppliers suppliersTable = new Suppliers();
        stage.getStage().setScene(suppliersTable.getScene());
        stage.getStage().show();
    }

    /***** set database *****/
    @FXML
    TableView<Orders> tableOrders;

    @FXML
    TableColumn<Orders, String> columnClient;

    @FXML
    TableColumn<Orders, String> columnCar;

    @FXML
    TableColumn<Orders, Integer> columnColor;

    @FXML
    TableColumn<Orders, String> columnCount;

    @FXML
    TableColumn<Orders, String> columnAgreementData;

    @FXML
    TableColumn<Orders, String> columnManager;

    @FXML
    TableColumn<Orders, String> columnPay;

    @FXML
    TableColumn<Orders, String> columnDispatch;

    @FXML
    TableColumn<Orders, String> columnDelivery;

    @FXML
    TableColumn<Orders, String> columnNotif;

    ObservableList<Orders> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ParseException {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        columnClient.setCellValueFactory(new PropertyValueFactory("clientID"));
        columnCar.setCellValueFactory(new PropertyValueFactory("carID"));
        columnColor.setCellValueFactory(new PropertyValueFactory("color"));
        columnCount.setCellValueFactory(new PropertyValueFactory("count"));
        columnAgreementData.setCellValueFactory(new PropertyValueFactory("conclusionDate"));
        columnManager.setCellValueFactory(new PropertyValueFactory("managerID"));
        columnPay.setCellValueFactory(new PropertyValueFactory("payment"));
        columnDispatch.setCellValueFactory(new PropertyValueFactory("dispatchDate"));
        columnDelivery.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
        columnNotif.setCellValueFactory(new PropertyValueFactory("notificationDate"));



        // заполняем таблицу данными
        tableOrders.setItems(data);
    }
    private void initData() throws SQLException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MyDataBase mdb = new MyDataBase();
        Statement s = mdb.getConn().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Заказы");
//        ResultSet rs2 = s.executeQuery("SELECT Стоимость FROM Ассортимент WHERE Код = " + new Orders().getCarID());
//        int cost = 0;
        while (rs.next()) {
//            while (rs2.next()) {
//                cost = Integer.parseInt(rs2.getString("Стоимость"));
//            }
            data.add(new Orders(
                    rs.getInt("КодЗаказа"),
                    new Clients().getName(rs.getInt("Клиент")),
                    new Assortment().getCarName(rs.getInt("Автомобиль")) + " " +new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    //new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    rs.getString("Цвет"),
                    rs.getString("Количество"),
                    rs.getDate("ДатаЗаключения"),
                    new Managers().getManager(rs.getInt("Менеджер")),
//                    String.valueOf(cost * Integer.parseInt( rs.getString("Количество"))),
                    rs.getString("ОплатаВПроцентах"),
                    rs.getDate("ДатаОтправки"),
                    rs.getDate("ДатаДоставки"),
                    rs.getDate("ДатаУведомления")
            ));
        }
    }



    /** Operating */

    @FXML
    AnchorPane layoutAdd = new AnchorPane();

    @FXML
    Button btnAdd = new Button();

    @FXML
    Button btnChange = new Button();

    @FXML
    ComboBox cbClient = new ComboBox();

    @FXML
    ComboBox cbCar = new ComboBox();

    @FXML
    TextField tfColor = new TextField();

    @FXML
    TextField tfCount = new TextField();

    @FXML
    ComboBox cbManager = new ComboBox();

    @FXML
    TextField tfPayment = new TextField();

    @FXML
    DatePicker dpConclusionDate = new DatePicker();

    @FXML
    DatePicker dpDispatchDate = new DatePicker();

    @FXML
    DatePicker dpDeliveryDate = new DatePicker();

    @FXML
    DatePicker dpNotificationDate = new DatePicker();


    public void addData(ActionEvent actionEvent) throws SQLException {
        tableOrders.setVisible(false);
        layoutAdd.setVisible(true);

        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ассортимент");

        ObservableList carsList = FXCollections.observableArrayList();
        while (rs.next()){
            carsList.add(rs.getString("Марка") + " " + rs.getString("Модель"));
            //carsList.add(rs.getString("Модель"));
        }
        cbCar.setItems(carsList);


        rs = stmt.executeQuery("SELECT * FROM Клиенты");
        ObservableList clientsList = FXCollections.observableArrayList();
        while (rs.next()){
            clientsList.add(rs.getString("Название"));
        }
        cbClient.setItems(clientsList);

        rs = stmt.executeQuery("SELECT * FROM Менеджеры");
        ObservableList managersList = FXCollections.observableArrayList();
        while (rs.next()){
            managersList.add(rs.getString("Фамилия"));
        }
        cbManager.setItems(managersList);


    }

    public void changeData(ActionEvent actionEvent) throws SQLException {
        addData(null);
        btnAdd.setVisible(false);
        btnChange.setVisible(true);

        Orders field = tableOrders.getSelectionModel().getSelectedItem();

        cbClient.setValue(field.getClientID());
        cbCar.setValue((field.getCarID()));
        tfColor.setText(field.getColor());
        tfCount.setText(String.valueOf(field.getCount()));
        cbManager.setValue(field.getManagerID());
        tfPayment.setText(String.valueOf(field.getPayment()));
        dpConclusionDate.setValue(LocalDate.parse(String.valueOf(field.getConclusionDate())));
        dpDispatchDate.setValue(LocalDate.parse(String.valueOf(field.getDispatchDate())));
        dpDeliveryDate.setValue(LocalDate.parse(String.valueOf(field.getDeliveryDate())));
        dpNotificationDate.setValue(LocalDate.parse(String.valueOf(field.getNotificationDate())));

        carModel = String.valueOf(cbCar.getValue()).trim().split("\\s+");
    }

    public void deleteData(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("DELETE FROM Заказы WHERE КодЗаказа=" + tableOrders.getSelectionModel().getSelectedItem().getOrdersID());

        showOrders(null);
    }


    private String[] carModel;

    public void getCarModel(ActionEvent actionEvent) {
        carModel = String.valueOf(cbCar.getValue()).trim().split("\\s+");
        System.out.println(carModel[1]);
    }

    public void onAdd(ActionEvent actionEvent) throws SQLException, IOException, ParseException {

        System.out.println(carModel[1]);

        MyDataBase mdb = new MyDataBase();
        PreparedStatement ps = mdb.getConn().prepareStatement(
                "INSERT INTO Заказы(Клиент, Автомобиль, Менеджер, Цвет, Количество, ОплатаВПроцентах, ДатаЗаключения, ДатаОтправки, ДатаДоставки, ДатаУведомления) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)"
        );

        ps.setInt(1, Integer.parseInt(new Clients().getClientID(cbClient.getValue()+"")));
        ps.setInt(2, Integer.parseInt(new Assortment().getCarID(carModel[1])));
        ps.setInt(3, Integer.parseInt(new Managers().getManagerID(cbManager.getValue() + "")));
        ps.setString(4, tfColor.getText());
        ps.setString(5, tfCount.getText());
        ps.setString(6, tfPayment.getText());
        ps.setDate(7, Date.valueOf(dpConclusionDate.getValue()));
        ps.setDate(8, Date.valueOf(dpDispatchDate.getValue()));
        ps.setDate(9, Date.valueOf(dpDeliveryDate.getValue()));
        ps.setDate(10, Date.valueOf(dpNotificationDate.getValue()));
        ps.executeUpdate();

        onBack(null);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        tableOrders.setVisible(true);
        layoutAdd.setVisible(false);
        showOrders(null);
    }

    public void onChange(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        PreparedStatement ps = mdb.getConn().prepareStatement(
                "UPDATE Заказы SET Клиент=?, Автомобиль=?, Цвет=?, Количество=?, ОплатаВПроцентах=?, Менеджер=?, ДатаЗаключения=?, ДатаОтправки=?, ДатаДоставки=?, ДатаУведомления=? " +
                "WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID()
        );

        ps.setInt(1, Integer.parseInt(new Clients().getClientID(cbClient.getValue()+"")));
        ps.setInt(2, Integer.parseInt(new Assortment().getCarID(carModel[1])));
        ps.setString(3, tfColor.getText());
        ps.setString(4, tfCount.getText());
        ps.setString(5, tfPayment.getText());
        ps.setString(6, new Managers().getManagerID(cbManager.getValue()+""));
        ps.setDate(7, Date.valueOf(dpConclusionDate.getValue()));
        ps.setDate(8, Date.valueOf(dpDispatchDate.getValue()));
        ps.setDate(9, Date.valueOf(dpDeliveryDate.getValue()));
        ps.setDate(10, Date.valueOf(dpNotificationDate.getValue()));
        ps.executeUpdate();

//        Statement stmt = mdb.getConn().createStatement();
//        stmt.execute("UPDATE Заказы SET Клиент=\"" + new Clients().getClientID(cbClient.getValue()+"")+ "\" WHERE КодЗаказа=" + tableOrders.getSelectionModel().getSelectedItem().getOrdersID());
//        stmt.execute("UPDATE Заказы SET Автомобиль=\""+new Assortment().getCarID(carModel[1])+"\" WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID());
//        stmt.execute("UPDATE Заказы SET Цвет=\""+tfColor.getText()+"\" WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID());
//        stmt.execute("UPDATE Заказы SET Количество=\""+tfCount.getText()+"\" WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID());
//        stmt.execute("UPDATE Заказы SET ОплатаВПроцентах=\""+tfPayment.getText()+"\" WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID());
//        stmt.execute("UPDATE Заказы SET Менеджер=\""+new Managers().getManagerID(cbManager.getValue()+"")+"\" WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID());
//        stmt.execute("UPDATE Заказы SET ДатаЗаключения=\""+Date.valueOf(dpConclusionDate.getValue())+"\" WHERE КодЗаказа="+tableOrders.getSelectionModel().getSelectedItem().getOrdersID());



        onBack(null);
        btnAdd.setVisible(true);
        btnChange.setVisible(false);
    }


    private void calcPayment(ActionEvent actionEvent) throws SQLException {
//        MyDataBase mdb = new MyDataBase();
//        Statement stmt = mdb.getConn().createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT Стоимость FROM Ассортимент WHERE ")
    }


    public void showPerformingOrders(ActionEvent actionEvent) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        data.clear();
        java.util.Date d = new java.util.Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(d));

        PreparedStatement ps = mdb.getConn().prepareStatement("SELECT * FROM Заказы WHERE ДатаОтправки <= ? AND ДатаДоставки > ?");
        ps.setDate(1, Date.valueOf(format1.format(d)));
        ps.setDate(2, Date.valueOf(format1.format(d)));

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            data.add(new Orders(
                    rs.getInt("КодЗаказа"),
                    new Clients().getName(rs.getInt("Клиент")),
                    new Assortment().getCarName(rs.getInt("Автомобиль")) + " " +new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    //new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    rs.getString("Цвет"),
                    rs.getString("Количество"),
                    rs.getDate("ДатаЗаключения"),
                    new Managers().getManager(rs.getInt("Менеджер")),
                    rs.getString("ОплатаВПроцентах"),
                    rs.getDate("ДатаОтправки"),
                    rs.getDate("ДатаДоставки"),
                    rs.getDate("ДатаУведомления")
            ));
        }

        columnClient.setCellValueFactory(new PropertyValueFactory("clientID"));
        columnCar.setCellValueFactory(new PropertyValueFactory("carID"));
        columnColor.setCellValueFactory(new PropertyValueFactory("color"));
        columnCount.setCellValueFactory(new PropertyValueFactory("count"));
        columnAgreementData.setCellValueFactory(new PropertyValueFactory("conclusionDate"));
        columnManager.setCellValueFactory(new PropertyValueFactory("managerID"));
        columnPay.setCellValueFactory(new PropertyValueFactory("payment"));
        columnDispatch.setCellValueFactory(new PropertyValueFactory("dispatchDate"));
        columnDelivery.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
        columnNotif.setCellValueFactory(new PropertyValueFactory("notificationDate"));
    }

    public void showRealizedOrders(ActionEvent actionEvent) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        data.clear();
        java.util.Date d = new java.util.Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(d));

        PreparedStatement ps = mdb.getConn().prepareStatement("SELECT * FROM Заказы WHERE ДатаДоставки <= ?");
        ps.setDate(1, Date.valueOf(format1.format(d)));

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            data.add(new Orders(
                    rs.getInt("КодЗаказа"),
                    new Clients().getName(rs.getInt("Клиент")),
                    new Assortment().getCarName(rs.getInt("Автомобиль")) + " " +new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    //new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    rs.getString("Цвет"),
                    rs.getString("Количество"),
                    rs.getDate("ДатаЗаключения"),
                    new Managers().getManager(rs.getInt("Менеджер")),
                    rs.getString("ОплатаВПроцентах"),
                    rs.getDate("ДатаОтправки"),
                    rs.getDate("ДатаДоставки"),
                    rs.getDate("ДатаУведомления")
            ));
        }

        columnClient.setCellValueFactory(new PropertyValueFactory("clientID"));
        columnCar.setCellValueFactory(new PropertyValueFactory("carID"));
        columnColor.setCellValueFactory(new PropertyValueFactory("color"));
        columnCount.setCellValueFactory(new PropertyValueFactory("count"));
        columnAgreementData.setCellValueFactory(new PropertyValueFactory("conclusionDate"));
        columnManager.setCellValueFactory(new PropertyValueFactory("managerID"));
        columnPay.setCellValueFactory(new PropertyValueFactory("payment"));
        columnDispatch.setCellValueFactory(new PropertyValueFactory("dispatchDate"));
        columnDelivery.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
        columnNotif.setCellValueFactory(new PropertyValueFactory("notificationDate"));
    }

    public void showProcessingOrders(ActionEvent actionEvent) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        data.clear();
        java.util.Date d = new java.util.Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(d));

        PreparedStatement ps = mdb.getConn().prepareStatement("SELECT * FROM Заказы WHERE (ДатаУведомления <= ? OR ДатаЗаключения <= ?) AND ДатаОтправки > ?");
        ps.setDate(1, Date.valueOf(format1.format(d)));
        ps.setDate(2, Date.valueOf(format1.format(d)));
        ps.setDate(3, Date.valueOf(format1.format(d)));

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            data.add(new Orders(
                    rs.getInt("КодЗаказа"),
                    new Clients().getName(rs.getInt("Клиент")),
                    new Assortment().getCarName(rs.getInt("Автомобиль")) + " " +new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    //new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    rs.getString("Цвет"),
                    rs.getString("Количество"),
                    rs.getDate("ДатаЗаключения"),
                    new Managers().getManager(rs.getInt("Менеджер")),
                    rs.getString("ОплатаВПроцентах"),
                    rs.getDate("ДатаОтправки"),
                    rs.getDate("ДатаДоставки"),
                    rs.getDate("ДатаУведомления")
            ));
        }

        columnClient.setCellValueFactory(new PropertyValueFactory("clientID"));
        columnCar.setCellValueFactory(new PropertyValueFactory("carID"));
        columnColor.setCellValueFactory(new PropertyValueFactory("color"));
        columnCount.setCellValueFactory(new PropertyValueFactory("count"));
        columnAgreementData.setCellValueFactory(new PropertyValueFactory("conclusionDate"));
        columnManager.setCellValueFactory(new PropertyValueFactory("managerID"));
        columnPay.setCellValueFactory(new PropertyValueFactory("payment"));
        columnDispatch.setCellValueFactory(new PropertyValueFactory("dispatchDate"));
        columnDelivery.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
        columnNotif.setCellValueFactory(new PropertyValueFactory("notificationDate"));
    }

    public void showClientMax(ActionEvent actionEvent) throws SQLException {
        data.clear();
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Заказы WHERE ОплатаВПроцентах = (SELECT MAX(ОплатаВПроцентах) FROM Заказы)");

        while (rs.next()){
            data.add(new Orders(
                    rs.getInt("КодЗаказа"),
                    new Clients().getName(rs.getInt("Клиент")),
                    new Assortment().getCarName(rs.getInt("Автомобиль")) + " " +new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    //new Assortment().getCarModel(rs.getInt("Автомобиль")),
                    rs.getString("Цвет"),
                    rs.getString("Количество"),
                    rs.getDate("ДатаЗаключения"),
                    new Managers().getManager(rs.getInt("Менеджер")),
//                    String.valueOf(cost * Integer.parseInt( rs.getString("Количество"))),
                    rs.getString("ОплатаВПроцентах"),
                    rs.getDate("ДатаОтправки"),
                    rs.getDate("ДатаДоставки"),
                    rs.getDate("ДатаУведомления")
            ));
            columnClient.setCellValueFactory(new PropertyValueFactory("clientID"));
            columnCar.setCellValueFactory(new PropertyValueFactory("carID"));
            columnColor.setCellValueFactory(new PropertyValueFactory("color"));
            columnCount.setCellValueFactory(new PropertyValueFactory("count"));
            columnAgreementData.setCellValueFactory(new PropertyValueFactory("conclusionDate"));
            columnManager.setCellValueFactory(new PropertyValueFactory("managerID"));
            columnPay.setCellValueFactory(new PropertyValueFactory("payment"));
            columnDispatch.setCellValueFactory(new PropertyValueFactory("dispatchDate"));
            columnDelivery.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
            columnNotif.setCellValueFactory(new PropertyValueFactory("notificationDate"));
        }
    }
}
