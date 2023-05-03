package Db;


import Api.GsonApi;
import Dto.Root;

import java.sql.*;

public class Db {
    public int save() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        StringBuffer sb = new StringBuffer();
        String a = "";
        a.toUpperCase();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;
        Root root = null;
        GsonApi parser = new GsonApi();
        int total = 0;
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = " insert into wifi_info (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LNT, LAT, WORK_DTTM) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
            preSt = connection.prepareStatement(sql);

            int start = 1;
            int end = 1000;
            boolean check = true;
            int r = 0;
            int cnt = 0;

            while (check) {
                root = parser.gsonData(start, end);
                for (int i = 0; i < 1000; i++) {

                    preSt.setString(1, root.TbPublicWifiInfo.row.get(i).X_SWIFI_MGR_NO);
                    preSt.setString(2, root.TbPublicWifiInfo.row.get(i).X_SWIFI_WRDOFC);
                    preSt.setString(3, root.TbPublicWifiInfo.row.get(i).X_SWIFI_MAIN_NM);
                    preSt.setString(4, root.TbPublicWifiInfo.row.get(i).X_SWIFI_ADRES1);
                    preSt.setString(5, root.TbPublicWifiInfo.row.get(i).X_SWIFI_ADRES2);
                    preSt.setString(6, root.TbPublicWifiInfo.row.get(i).X_SWIFI_INSTL_FLOOR);
                    preSt.setString(7, root.TbPublicWifiInfo.row.get(i).X_SWIFI_INSTL_TY);
                    preSt.setString(8, root.TbPublicWifiInfo.row.get(i).X_SWIFI_INSTL_MBY);
                    preSt.setString(9, root.TbPublicWifiInfo.row.get(i).X_SWIFI_SVC_SE);
                    preSt.setString(10, root.TbPublicWifiInfo.row.get(i).X_SWIFI_CMCWR);
                    preSt.setString(11, root.TbPublicWifiInfo.row.get(i).X_SWIFI_CNSTC_YEAR);
                    preSt.setString(12, root.TbPublicWifiInfo.row.get(i).X_SWIFI_INOUT_DOOR);
                    preSt.setString(13, root.TbPublicWifiInfo.row.get(i).X_SWIFI_REMARS3);
                    preSt.setDouble(14, root.TbPublicWifiInfo.row.get(i).LNT);
                    preSt.setDouble(15, root.TbPublicWifiInfo.row.get(i).LAT);
                    preSt.setString(16, root.TbPublicWifiInfo.row.get(i).WORK_DTTM);
                    cnt++;
                    r += preSt.executeUpdate();
                    total = Integer.parseInt(root.TbPublicWifiInfo.list_total_count);
                    if(cnt == total) {
                        check = false;
                        break;
                    }
                }
                if (end >= total) {
                    check = false;
                } else {
                    start = end + 1;
                    end = Math.min(end + 1000, total);
                }
            }
            System.out.println("저장 후");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }
}