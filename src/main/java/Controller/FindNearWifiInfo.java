package Controller;

import Dto.WifiInfo;
import Dto.Row;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FindNearWifiInfo {
    public List<Row> find() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        List<Row> rowInfo = null;
        int a = 0;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT DISTINCT *, ROUND(DISTANCE, 4) AS round_distance FROM wifi_info ORDER BY round_distance ASC LIMIT 20");
            rs = preSt.executeQuery();

            rowInfo = new ArrayList<>();
            while (rs.next()) {
                Row row = new Row();
                row.setDISTANCE(rs.getDouble(1));
                row.setX_SWIFI_MGR_NO(rs.getString(2));
                row.setX_SWIFI_WRDOFC(rs.getString(3));
                row.setX_SWIFI_MAIN_NM(rs.getString(4));
                row.setX_SWIFI_ADRES1(rs.getString(5));
                row.setX_SWIFI_ADRES2(rs.getString(6));
                row.setX_SWIFI_INSTL_FLOOR(rs.getString(7));
                row.setX_SWIFI_INSTL_TY(rs.getString(8));
                row.setX_SWIFI_INSTL_MBY(rs.getString(9));
                row.setX_SWIFI_SVC_SE(rs.getString(10));
                row.setX_SWIFI_CMCWR(rs.getString(11));
                row.setX_SWIFI_CNSTC_YEAR(rs.getString(12));
                row.setX_SWIFI_INOUT_DOOR(rs.getString(13));
                row.setX_SWIFI_REMARS3(rs.getString(14));
                row.setLNT(rs.getDouble(15));
                row.setLAT(rs.getDouble(16));
                row.setWORK_DTTM(rs.getString(17));
                rowInfo.add(row);
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
        return rowInfo;
    }
}