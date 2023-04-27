package Controller;

import Dto.WifiInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/location.do")
public class FindDistance extends HttpServlet {

    public double calculateDistance(double x1, double y1, double lat, double lnt) {
        double distance = Math.sqrt(Math.pow(x1 - lat, 2) + Math.pow(y1 - lnt, 2));
        return distance;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        double lat = Double.parseDouble(request.getParameter("latname"));
        double lnt = Double.parseDouble(request.getParameter("lntname"));
        select(lat, lnt);
        RequestDispatcher dis = request.getRequestDispatcher("/nearWifi.jsp");
        dis.forward(request, response);
    }

    public void select(double lat, double lnt) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;
        int i = 0;
        int a = 0;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT X_SWIFI_MGR_NO, LNT, LAT FROM WIFI_INFO");
            rs = preSt.executeQuery();

            List<WifiInfo> wifiInfos = new ArrayList<>();
            while (rs.next()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setNo(rs.getString(1));
                wifiInfo.setLnt(rs.getDouble(2));
                wifiInfo.setLat(rs.getDouble(3));
                wifiInfos.add(wifiInfo);
            }

            for (WifiInfo wifiInfo : wifiInfos) {
                double distance = calculateDistance(wifiInfo.getLnt(), wifiInfo.getLat(), lat, lnt);
                String distanceInsert = "UPDATE wifi_info SET distance = ? WHERE X_SWIFI_MGR_NO = ?";
                PreparedStatement distanceSt = connection.prepareStatement(distanceInsert);
                distanceSt.setDouble(1, distance);
                distanceSt.setString(2, wifiInfo.getNo());
                distanceSt.executeUpdate();
                distanceSt.close();
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
    }
}
