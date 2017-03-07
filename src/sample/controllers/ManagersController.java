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
public class ManagersController {
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
    TableView<Managers> tableManagers;

    @FXML
    TableColumn<Clients, String> columnFIO;

    @FXML
    TableColumn<Clients, String> columnPhone;

    ObservableList<Managers> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        columnFIO.setCellValueFactory(new PropertyValueFactory("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory("phone"));

        // заполняем таблицу данными
        tableManagers.setItems(data);
    }
    private void initData() throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement s = mdb.getConn().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Менеджеры");
        while (rs.next()) {
            data.add(new Managers(
                    rs.getString("ТабельныйНомер"),
                    rs.getString("Фамилия"),
                    rs.getString("Телефон")
            ));
        }
    }



    @FXML
    AnchorPane layoutAdd = new AnchorPane();

    @FXML
    TextField tfName = new TextField();

    @FXML
    TextField tfPhone = new TextField();



    public void addData(ActionEvent actionEvent) throws SQLException, IOException {
        tableManagers.setVisible(false);
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

        Managers field = tableManagers.getSelectionModel().getSelectedItem();

        tfName.setText(field.getName());
        tfPhone.setText(field.getPhone());
    }

    public void deleteData(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("DELETE FROM Менеджеры WHERE ТабельныйНомер=" + tableManagers.getSelectionModel().getSelectedItem().getManagerID());

        showManagers(null);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        tableManagers.setVisible(true);
        layoutAdd.setVisible(false);

        showManagers(null);
    }

    public void onAdd(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();

        stmt.execute("INSERT INTO Менеджеры(Фамилия, Телефон) " +
                        "VALUES(" +
                        "\""+tfName.getText()+"\", " +
                        "\""+tfPhone.getText()+"\""+
                        ")"
        );

        onBack(null);

    }

    public void onChange(ActionEvent actionEvent) throws IOException, SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("UPDATE Менеджеры SET Фамилия=\"" + tfName.getText() + "\" WHERE ТабельныйНомер=" + tableManagers.getSelectionModel().getSelectedItem().getManagerID());
        stmt.execute("UPDATE Менеджеры SET Телефон=\""+tfPhone.getText()+"\" WHERE ТабельныйНомер="+tableManagers.getSelectionModel().getSelectedItem().getManagerID());



        onBack(null);
        btnAdd.setVisible(true);
        btnChange.setVisible(false);
    }

}
