package Controller;

import Dto.History;
import Dto.Row;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiHistory {
    public void save(Row row) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("INSERT INTO history (lnt, lat, date) values (?, ?, ?)");
            preSt.setDouble(1, row.getLNT());
            preSt.setDouble(2, row.getLAT());
            preSt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preSt != null)
                    preSt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<History> select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        List<History> historyList = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT * FROM history");
            rs = preSt.executeQuery();

            historyList = new ArrayList<>();
            while (rs.next()) {
                History history  = new History();
                history.setLnt(rs.getDouble(1));
                history.setLat(rs.getDouble(2));
                history.setDate(rs.getString(3));
                historyList.add(history);
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (preSt != null)
                    preSt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return historyList;
    }
}
