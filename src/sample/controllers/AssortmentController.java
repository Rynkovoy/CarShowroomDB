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
public class AssortmentController {


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
    TableView<Assortment> tableAssortment;

    @FXML
    TableColumn<Assortment, String> columnName;

    @FXML
    TableColumn<Assortment, String> columnBodyType;

    @FXML
    TableColumn<Assortment, String> columnModel;

    @FXML
    TableColumn<Assortment, Integer> columnSupplier;

    @FXML
    TableColumn<Assortment, String> columnCost;

    @FXML
    TableColumn<Assortment, String> columnGearbox;

    ObservableList<Assortment> data = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnModel.setCellValueFactory(new PropertyValueFactory("model"));
        columnBodyType.setCellValueFactory(new PropertyValueFactory("bodyType"));
        columnSupplier.setCellValueFactory(new PropertyValueFactory("supplier"));
        columnCost.setCellValueFactory(new PropertyValueFactory("cost"));
        columnGearbox.setCellValueFactory(new PropertyValueFactory("gearbox"));

        // заполняем таблицу данными
        tableAssortment.setItems(data);
    }
    private void initData() throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement s = mdb.getConn().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Ассортимент");
        while (rs.next()) {
            data.add(new Assortment(
                    rs.getInt("Код"),
                    rs.getString("Марка"),
                    rs.getString("Модель"),
                    rs.getString("ТипКузова"),
                    new Suppliers().getSupplier(rs.getInt("Поставщик")),
                    rs.getDouble("Стоимость"),
                    rs.getString("КоробкаПередач")
            ));
        }
    }


    @FXML
    AnchorPane layoutAdd = new AnchorPane();

    @FXML
    TextField tfName = new TextField();

    @FXML
    TextField tfModel = new TextField();

    @FXML
    TextField tfBodyType = new TextField();

    @FXML
    ComboBox cbSupplier = new ComboBox();

    @FXML
    TextField tfCost = new TextField();

    @FXML
    ComboBox cbGearbox = new ComboBox();



    public void addData(ActionEvent actionEvent) throws SQLException, IOException {
        tableAssortment.setVisible(false);
        layoutAdd.setVisible(true);

        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Название FROM Поставщики");

        ObservableList suppliersList = FXCollections.observableArrayList();

        while (rs.next()){
            suppliersList.add(rs.getString("Название"));
        }
        cbSupplier.setItems(suppliersList);

        ObservableList gearboxList = FXCollections.observableArrayList();
        gearboxList.add("АКПП");
        gearboxList.add("МКПП");
        gearboxList.add("Типтроник");

        cbGearbox.setItems(gearboxList);
    }

    @FXML
    Button btnAdd = new Button();
    @FXML
    Button btnChange = new Button();

    public void changeData(ActionEvent actionEvent) throws IOException, SQLException {
        addData(null);
        btnAdd.setVisible(false);
        btnChange.setVisible(true);

        Assortment field = tableAssortment.getSelectionModel().getSelectedItem();

        tfName.setText(field.getName());
        tfModel.setText(field.getModel());
        tfBodyType.setText(field.getBodyType());
        cbSupplier.setValue(field.getSupplier());
        tfCost.setText(String.valueOf(field.getCost()));
        cbGearbox.setValue(field.getGearbox());
    }

    public void deleteData(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("DELETE FROM Ассортимент WHERE Код=" + tableAssortment.getSelectionModel().getSelectedItem().getId());

        showAssortment(null);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        tableAssortment.setVisible(true);
        layoutAdd.setVisible(false);

        showAssortment(null);
    }

    public void onAdd(ActionEvent actionEvent) throws SQLException, IOException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("" +
                        "INSERT INTO Ассортимент(Марка, Модель, ТипКузова, Поставщик, Стоимость, КоробкаПередач) " +
                        "VALUES(" +
                        "\"" + tfName.getText() + "\", " +
                        "\"" + tfModel.getText() + "\", " +
                        "\"" + tfBodyType.getText() + "\", " +
                        "" + new Suppliers().getSupplierID(cbSupplier.getValue()+"")+ ", " +
                        "\"" + tfCost.getText() + "\", " +
                        "\"" + cbGearbox.getValue() + "\"" +
                        ")"
        );

//        stmt.execute("INSERT INTO Ассортимент(Марка) VALUES(\""+tfName.getText()+"\")");
//        stmt.execute("INSERT INTO Ассортимент(Модель) VALUES(\""+tfModel.getText()+"\")");
//        stmt.execute("INSERT INTO Ассортимент(ТипКузова) VALUES(\""+tfBodyType.getText()+"\")");
//        stmt.execute("INSERT INTO Ассортимент(Поставщик) VALUES(SELECT * FROM Поставщики WHERE КодПоставщика=\""+cbSupplier.getValue()+"\")");
//        stmt.execute("INSERT INTO Ассортимент(Стоимость) VALUES(\""+tfCost.getText()+"\")");
//        stmt.execute("INSERT INTO Ассортимент(КоробкаПередач) VALUES(\""+cbGearbox.getValue()+"\")");

        onBack(null);

    }

    public void onChange(ActionEvent actionEvent) throws IOException, SQLException {

        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        stmt.execute("UPDATE Ассортимент SET Марка=\""+tfName.getText()+"\" WHERE Код="+tableAssortment.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Ассортимент SET Модель=\""+tfModel.getText()+"\" WHERE Код="+tableAssortment.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Ассортимент SET ТипКузова=\""+tfBodyType.getText()+"\" WHERE Код="+tableAssortment.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Ассортимент SET Поставщик=\""+new Suppliers().getSupplierID(cbSupplier.getValue()+"")+"\" WHERE Код="+tableAssortment.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Ассортимент SET Стоимость=\""+tfCost.getText()+"\" WHERE Код="+tableAssortment.getSelectionModel().getSelectedItem().getId());
        stmt.execute("UPDATE Ассортимент SET КоробкаПередач=\""+cbGearbox.getValue()+"\" WHERE Код="+tableAssortment.getSelectionModel().getSelectedItem().getId());

        onBack(null);
        btnAdd.setVisible(true);
        btnChange.setVisible(false);
    }


    @FXML
    ComboBox cbShowCars = new ComboBox();

    public void onShowCar(ActionEvent actionEvent) throws SQLException {
        data.clear();

        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        if (cbShowCars.getValue() == "Все"){
               initialize();
        }else {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Ассортимент WHERE Марка = \"" + cbShowCars.getValue() + "\"");
            while (rs.next()) {
                data.add(new Assortment(
                        rs.getInt("Код"),
                        rs.getString("Марка"),
                        rs.getString("Модель"),
                        rs.getString("ТипКузова"),
                        new Suppliers().getSupplier(rs.getInt("Поставщик")),
                        rs.getDouble("Стоимость"),
                        rs.getString("КоробкаПередач")
                ));
            }


            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnModel.setCellValueFactory(new PropertyValueFactory("model"));
            columnBodyType.setCellValueFactory(new PropertyValueFactory("bodyType"));
            columnSupplier.setCellValueFactory(new PropertyValueFactory("supplier"));
            columnCost.setCellValueFactory(new PropertyValueFactory("cost"));
            columnGearbox.setCellValueFactory(new PropertyValueFactory("gearbox"));

            // заполняем таблицу данными
            tableAssortment.setItems(data);
        }
    }

    public void updateshowcars(ActionEvent actionEvent) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Марка FROM Ассортимент GROUP BY Марка");
        ObservableList carsMark = FXCollections.observableArrayList();

        carsMark.add("Все");

        while (rs.next()){
            carsMark.add(rs.getString("Марка"));
        }
        cbShowCars.setItems(carsMark);
    }

    public void TheCostestCar(ActionEvent actionEvent) throws SQLException {
        MyDataBase mdb = new MyDataBase();
        Statement stmt = mdb.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ассортимент WHERE Стоимость = (SELECT MAX(Стоимость) FROM Ассортимент)");

        data.clear();

        while (rs.next()) {
            data.add(new Assortment(
                    rs.getInt("Код"),
                    rs.getString("Марка"),
                    rs.getString("Модель"),
                    rs.getString("ТипКузова"),
                    new Suppliers().getSupplier(rs.getInt("Поставщик")),
                    rs.getDouble("Стоимость"),
                    rs.getString("КоробкаПередач")
            ));
        }


        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnModel.setCellValueFactory(new PropertyValueFactory("model"));
        columnBodyType.setCellValueFactory(new PropertyValueFactory("bodyType"));
        columnSupplier.setCellValueFactory(new PropertyValueFactory("supplier"));
        columnCost.setCellValueFactory(new PropertyValueFactory("cost"));
        columnGearbox.setCellValueFactory(new PropertyValueFactory("gearbox"));

        // заполняем таблицу данными
        tableAssortment.setItems(data);

    }
}


























