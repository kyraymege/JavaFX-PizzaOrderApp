package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    private static String dbUserName="root";
    private static String dbPassword="123456";
    private static String dbUrl="jdbc:mysql://localhost:3306/pizza";

    public static Connection getConnection() throws SQLException {
        Connection connection= null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
