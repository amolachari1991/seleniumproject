package day31;
import java.sql.*;

public class OracleQueryExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "SYSTEM";
        String password = "admin";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getModifiedSQLQuery())) {

            System.out.println("Minute Group\tFirst Second Offer\tLast Second Offer\tMin Offer\tMax Offer");

            while (rs.next()) {
                String minuteGroup = rs.getString("minute_group");
                String firstSecondOffer = rs.getString("first_second_offer");
                String lastSecondOffer = rs.getString("last_second_offer");
                String minOffer = rs.getString("min_offer");
                String maxOffer = rs.getString("max_offer");

                System.out.println(minuteGroup + "\t\t" + firstSecondOffer + "\t\t\t" +
                        lastSecondOffer + "\t\t\t" + minOffer + "\t\t" + maxOffer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getModifiedSQLQuery() {
        return "WITH cte AS (\n" +
                "  SELECT\n" +
                "    TO_CHAR(TIMESTAMPP, 'YYYY-MM-DD HH24:MI') AS minute_group,\n" +
                "    MIN(TIMESTAMPP) KEEP (DENSE_RANK FIRST ORDER BY TIMESTAMPP) AS first_second,\n" +
                "    MAX(TIMESTAMPP) KEEP (DENSE_RANK LAST ORDER BY TIMESTAMPP) AS last_second\n" +
                "  FROM kite2\n" +
                "  GROUP BY TO_CHAR(TIMESTAMPP, 'YYYY-MM-DD HH24:MI')\n" +
                ")\n" +
                "SELECT cte.minute_group,\n" +
                "       (SELECT offer FROM kite2 WHERE TIMESTAMPP = cte.first_second) AS first_second_offer,\n" +
                "       (SELECT offer FROM kite2 WHERE TIMESTAMPP = cte.last_second) AS last_second_offer,\n" +
                "       MIN(offer) AS min_offer,\n" +
                "       MAX(offer) AS max_offer\n" +
                "FROM cte\n" +
                "JOIN kite2 ON kite2.TIMESTAMPP >= cte.first_second AND kite2.TIMESTAMPP <= cte.last_second\n" +
                "GROUP BY cte.minute_group, cte.first_second, cte.last_second\n" +
                "HAVING (SELECT offer FROM kite2 WHERE TIMESTAMPP = cte.first_second) = MIN(offer)\n" +
                "ORDER BY cte.minute_group ASC";
    }
}
