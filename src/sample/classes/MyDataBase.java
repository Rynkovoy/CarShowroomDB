package sample.classes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private Connection conn;

    public MyDataBase() throws SQLException {
         conn = DriverManager.getConnection("jdbc:ucanaccess://src/database.accdb");
    }

    public Connection getConn() {
        return conn;
    }

    //    private Database database;
//    public Database getDatabase() throws IOException {
//        database = DatabaseBuilder.open(new File("src/database.accdb"));
//        return database;
//    }

}

