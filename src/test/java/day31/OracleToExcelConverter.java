package day31;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OracleToExcelConverter {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String password = "admin";
        String query = "SELECT * FROM kite2";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Data");

            int rowNum = 0;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                while (true) {
                    try {
                        String columnValue = resultSet.getString(colNum + 1);
                        Cell cell = row.createCell(colNum);
                        cell.setCellValue(columnValue);
                        colNum++;
                    } catch (SQLException e) {
                        break;
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream("output4.xlsx")) {
                workbook.write(outputStream);
            }

            System.out.println("Data exported to Excel successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
