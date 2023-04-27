package Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/location.do")
public class FindDistance extends HttpServlet {
    static double lat;
    static double lnt;

    public double calculateDistance(double x1, double y1) {
        double distance = Math.sqrt(Math.pow(x1 - lat, 2) + Math.pow(y1 - lnt, 2));
        return distance;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        lat = Double.parseDouble(request.getParameter("latname"));
        lnt = Double.parseDouble(request.getParameter("lntname"));
        select();
    }

    public void select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, dbUserId, dbPassword);
             PreparedStatement preSt = connection.prepareStatement("SELECT X_SWIFI_MGR_NO, LNT, LAT FROM WIFI_INFO");
             ResultSet rs = preSt.executeQuery()) {
            while (rs.next()) {
                String no = rs.getString(1);
                Double lnt = rs.getDouble(2);
                Double lat = rs.getDouble(3);

                Double distance = calculateDistance(lnt, lat);
                String distanceInsert = "UPDATE wifi_info SET distance = ? WHERE X_SWIFI_MGR_NO = ?";
                PreparedStatement distanceSt = connection.prepareStatement(distanceInsert);
                distanceSt.setDouble(1, distance);
                distanceSt.setString(2, no);
                distanceSt.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
