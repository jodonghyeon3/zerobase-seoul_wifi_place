package Controller;

import Dto.BookMark;

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

@WebServlet("/bookMarkAdd")
public class BookMarkAddController extends HttpServlet {
    private static int Id = 1;
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String wifiName = request.getParameter("wifiName");
        String bookName = request.getParameter("bookName");
        System.out.println(bookName);

        add(wifiName, bookName);
        RequestDispatcher dis = request.getRequestDispatcher("/bookmark-list.jsp");
        dis.forward(request, response);
    }
    public void add(String wifiName, String bookName) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT MAX(id) FROM bookmark");
            rs = preSt.executeQuery();
            if (rs.next()) {
                Id = rs.getInt(1) + 1;
            }
            preSt = connection.prepareStatement("INSERT INTO bookMark (id, name, wifiName, date) values (?, ?, ?, ?)");
            preSt.setInt(1, Id);
            preSt.setString(2, bookName);
            preSt.setString(3, wifiName);
            preSt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preSt.executeUpdate();
            Id++;
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

    public List<BookMark> select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        List<BookMark> bookMarks = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT * FROM bookMark");
            rs = preSt.executeQuery();

            bookMarks = new ArrayList<>();
            while (rs.next()) {
                BookMark bookMark = new BookMark();
                bookMark.setId(String.valueOf(rs.getInt((1))));
                bookMark.setName(rs.getString(2));
                bookMark.setWifiName(rs.getString(3));
                bookMark.setDate(rs.getString(4));
                bookMarks.add(bookMark);
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
        return bookMarks;
    }
}
