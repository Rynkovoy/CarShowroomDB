package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class SuppliesController {
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

    /** DATA BASE */
    @FXML
    TableView<Suppliers> tableSupplier;

    @FXML
    TableColumn<Suppliers, String> columnName;

    @FXML
    TableColumn<Suppliers, String> columnCountry;

    @FXML
    TableColumn<Suppliers, String> columnCity;

    @FXML
    TableColumn<Suppliers, String> columnAddress;

    @FXML
    TableColumn<Suppliers, String> columnDeadline;

    @FXML
    TableColumn<Suppliers, String> columnDelivery;




    ObservableList<Suppliers> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnCountry.setCellValueFactory(new PropertyValueFactory("country"));
        columnCity.setCellValueFactory(new PropertyValueFactory("address"));
        columnAddress.setCellValueFactory(new PropertyValueFactory("city"));
        columnDeadline.setCellValueFactory(new PropertyValueFactory("deadLine"));
        columnDelivery.setCellValueFactory(new PropertyValueFactory("deliveryTime"));

        // заполняем таблицу данными
        tableSupplier.setItems(data);
    }
    private void initData() throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement s = mdb.getConn().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Поставщики");
        while (rs.next()) {
            data.add(new Suppliers(
                rs.getString("КодПоставщика"),
                rs.getString("Название"),
                rs.getString("Страна"),
                rs.getString("Город"),
                rs.getString("Адрес"),
                rs.getString("СрокВыполнения"),
                rs.getString("СрокДоставки")

            ));
        }
    }


    @FXML
    AnchorPane layoutAdd = new AnchorPane();

    @FXML
    TextField tfName = new TextField();

    @FXML
    TextField tfCountry = new TextField();

    @FXML
    TextField tfCity = new TextField();

    @FXML
    TextField tfAddress = new TextField();

    @FXML
    TextField tfDeadLine = new TextField();

    @FXML
    TextField tfDeliveryTime = new TextField();



    public void addData(ActionEvent actionEvent) throws SQLException, IOException {
        tableSupplier.setVisible(false);
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

        Suppliers field = tableSupplier.getSelectionModel().getSelectedItem();

        tfName.setText(field.getName());
        tfCountry.setText(field.getCountry());
        tfCity.setText(field.getCity());
        tfAddress.setText(field.getAddress());
        tfDeadLine.setText(field.getDeadLine());
        tfDeliveryTime.setText(field.getDeliveryTime());
    }

    public void deleteData(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("DELETE FROM Поставщики WHERE КодПоставщика=" + tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());

        showSuppliers(null);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        tableSupplier.setVisible(true);
        layoutAdd.setVisible(false);

        showSuppliers(null);
    }

    public void onAdd(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();

        stmt.execute("INSERT INTO Поставщики(Название, Страна, Адрес, Город, СрокВыполнения, СрокДоставки) " +
                        "VALUES(" +
                        "\""+tfName.getText()+"\", " +
                        "\""+tfCountry.getText()+"\", " +
                        "\""+tfAddress.getText()+"\", " +
                        "\""+tfCity.getText()+"\", " +
                        "\""+tfDeadLine.getText()+"\", " +
                        "\""+tfDeliveryTime.getText()+"\""+
                        ")"
        );

        onBack(null);

    }

    public void onChange(ActionEvent actionEvent) throws IOException, SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("UPDATE Поставщики SET Название=\"" + tfName.getText() + "\" WHERE КодПоставщика=" + tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());
        stmt.execute("UPDATE Поставщики SET Страна=\""+tfCountry.getText()+"\" WHERE КодПоставщика="+tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());
        stmt.execute("UPDATE Поставщики SET Адрес=\""+tfAddress.getText()+"\" WHERE КодПоставщика="+tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());
        stmt.execute("UPDATE Поставщики SET Город=\""+tfCity.getText()+"\" WHERE КодПоставщика="+tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());
        stmt.execute("UPDATE Поставщики SET СрокВыполнения=\""+tfDeadLine.getText()+"\" WHERE КодПоставщика="+tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());
        stmt.execute("UPDATE Поставщики SET СрокДоставки=\""+tfDeliveryTime.getText()+"\" WHERE КодПоставщика="+tableSupplier.getSelectionModel().getSelectedItem().getSupplyID());


        onBack(null);
        btnAdd.setVisible(true);
        btnChange.setVisible(false);
    }


}
