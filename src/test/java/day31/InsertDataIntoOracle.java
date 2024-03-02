package day31;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDataIntoOracle {
    public static void main(String[] args) {
        // Database connection details
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String password = "admin";

        // Input data
        double offer = 9.99;
        int order = 12345;
        int quantity = 5;

        // Create a SQL INSERT statement
        String sql = "INSERT INTO your_table_name (offer, orders, quantity) VALUES (?, ?, ?)";

        try (
            // Establish a database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            // Create a prepared statement with the SQL query
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            // Set the values for the parameters in the INSERT statement
            statement.setDouble(1, offer);
            statement.setInt(2, order);
            statement.setInt(3, quantity);

            // Execute the INSERT statement
            int rowsAffected = statement.executeUpdate();

            // Check the number of affected rows
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
