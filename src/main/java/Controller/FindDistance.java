package Controller;

import Dto.Root;
import Dto.Row;
import Dto.WifiInfo;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/location.do")
public class FindDistance extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        double lat = Double.parseDouble(request.getParameter("latname"));
        double lnt = Double.parseDouble(request.getParameter("lntname"));
        List<Row> rows = select(lat, lnt);
        request.setAttribute("lat", lat);
        request.setAttribute("lnt", lnt);
        request.setAttribute("rows", rows);
        RequestDispatcher dis = request.getRequestDispatcher("/nearWifi.jsp");
        dis.forward(request, response);
    }

    public List<Row> select(double lat, double lnt){
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;
        List<Row> rowInfo = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT *, ROUND(6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(LAT)) * COS(RADIANS(LNT) - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(LAT))), 4)  AS DISTANCE_KM FROM wifi_info ORDER BY DISTANCE_KM ASC LIMIT 20;");
            preSt.setDouble(1, lnt);
            preSt.setDouble(2, lat);
            preSt.setDouble(3, lnt);
            rs = preSt.executeQuery();
            rowInfo = new ArrayList<>();

            while (rs.next()) {
                Row row = new Row();
                row.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                row.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                row.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                row.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                row.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                row.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                row.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                row.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                row.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                row.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                row.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                row.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                row.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                row.setLNT(rs.getDouble("lnt"));
                row.setLAT(rs.getDouble("lat"));
                row.setWORK_DTTM(rs.getString("WORK_DTTM"));
                row.setDISTANCE(rs.getDouble("DISTANCE_KM"));
                rowInfo.add(row);
            }
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
        return rowInfo;
    }
}