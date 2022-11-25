package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final Connection con;
    private final String INIT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_NAME = "DentistOffice";
    private final String host = "localhost:3306";
    private final String URL = "jdbc:mysql://" + host + "/" + DB_NAME;
    private final String DB_USER_NAME = "root";
    private final String DB_PASSWORD = "";
    private final static Database INSTANCE = new Database();

    private Database() {
        try {
            Class.forName(INIT_DRIVER);
            con = DriverManager.getConnection(URL, DB_USER_NAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error in database connection");
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return con;
    }

    public void close() throws SQLException {
        con.close();
    }


}
