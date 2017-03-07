package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.classes.MyDataBase;
import sample.classes.MyStage;
import sample.tables.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Rynkovoy on 10/31/2016.
 */
public class ClientsController {
    public void showAssortment(ActionEvent actionEvent) throws IOException, SQLException {
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

    /** DATA BASE */
    @FXML
    TableView<Clients> tableClients;

    @FXML
    TableColumn<Clients, String> columnName;

    @FXML
    TableColumn<Clients, String> columnCategory;

    @FXML
    TableColumn<Clients, String> columnCountry;

    @FXML
    TableColumn<Clients, String> columnCity;

    @FXML
    TableColumn<Clients, String> columnPhone;

    @FXML
    TableColumn<Clients, String> columnAddress;

    ObservableList<Clients> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnCategory.setCellValueFactory(new PropertyValueFactory("category"));
        columnCountry.setCellValueFactory(new PropertyValueFactory("country"));
        columnCity.setCellValueFactory(new PropertyValueFactory("city"));
        columnPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        columnAddress.setCellValueFactory(new PropertyValueFactory("address"));

        // заполняем таблицу данными
        tableClients.setItems(data);
    }
    private void initData() throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement s = mdb.getConn().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Клиенты");
        while (rs.next()) {
            data.add(new Clients(
                    rs.getString("КодКлиента"),
                    rs.getString("Название"),
                    rs.getString("Адрес"),
                    rs.getString("Категория"),
                    rs.getString("СтранаКлиента"),
                    rs.getString("Город"),
                    rs.getString("Телефон")
            ));
        }
    }




    @FXML
    AnchorPane layoutAdd = new AnchorPane();

    @FXML
    TextField tfName = new TextField();

    @FXML
    TextField tfAddress = new TextField();

    @FXML
    TextField tfCategory = new TextField();

    @FXML
    TextField tfCountry = new TextField();

    @FXML
    TextField tfCity = new TextField();

    @FXML
    TextField tfPhone = new TextField();



    public void addData(ActionEvent actionEvent) throws SQLException, IOException {
        tableClients.setVisible(false);
        layoutAdd.setVisible(true);
    }

    @FXML
    Button btnAdd = new Button();
    @FXML
    Button btnChange = new Button();

    public void changeData(ActionEvent actionEvent) throws IOException, SQLException {
        addData(null);
        btnAdd.setVisible(false);
        btnChange.setVisible(true);

        Clients field = tableClients.getSelectionModel().getSelectedItem();

        tfName.setText(field.getName());
        tfAddress.setText(field.getAddress());
        tfCategory.setText(field.getCategory());
        tfCountry.setText(field.getCountry());
        tfCity.setText(field.getCity());
        tfPhone.setText(field.getPhone());
    }

    public void deleteData(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("DELETE FROM Клиенты WHERE КодКлиента=" + tableClients.getSelectionModel().getSelectedItem().getId());

        showClients(null);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        tableClients.setVisible(true);
        layoutAdd.setVisible(false);

        showClients(null);
    }

    public void onAdd(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();

        stmt.execute("INSERT INTO Клиенты(Название, Адрес, Категория, СтранаКлиента, Город, Телефон) " +
                     "VALUES(" +
                            "\""+tfName.getText()+"\", " +
                            "\""+tfAddress.getText()+"\", " +
                            "\""+tfCategory.getText()+"\", "+
                            "\""+tfCountry.getText()+"\", "+
                            "\""+tfCity.getText()+"\", "+
                            "\""+tfPhone.getText()+"\""+
                        ")"
        );

        onBack(null);

    }

    public void onChange(ActionEvent actionEvent) throws IOException, SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("UPDATE Клиенты SET Название=\""+tfName.getText()+"\" WHERE КодКлиента="+tableClients.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Клиенты SET Адрес=\""+tfAddress.getText()+"\" WHERE КодКлиента="+tableClients.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Клиенты SET Категория=\""+tfCategory.getText()+"\" WHERE КодКлиента="+tableClients.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Клиенты SET СтранаКлиента=\""+tfCountry.getText()+"\" WHERE КодКлиента="+tableClients.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Клиенты SET Город=\""+tfCity.getText()+"\" WHERE КодКлиента="+tableClients.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Клиенты SET Телефон=\""+tfPhone.getText()+"\" WHERE КодКлиента="+tableClients.getSelectionModel().getSelectedItem().getId());



        onBack(null);
        btnAdd.setVisible(true);
        btnChange.setVisible(false);
    }
}
