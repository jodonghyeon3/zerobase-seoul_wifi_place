package Controller;

import Dto.BookMark;
import Dto.History;
import Dto.Row;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bookmark")
public class BookMarkController extends HttpServlet {
    private static int Id = 1;
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String turn = request.getParameter("turn");
        save(name, turn);
        RequestDispatcher dis = request.getRequestDispatcher("/bookmark-group.jsp");
        dis.forward(request,response);
    }
    public void save(String name, String turn) {
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
            preSt = connection.prepareStatement("INSERT INTO bookmark (id, name, turn, date) values (?, ?, ?, ?)");
            preSt.setInt(1, Id);
            preSt.setString(2, name);
            preSt.setString(3, turn);
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
            preSt = connection.prepareStatement("SELECT * FROM bookmark");
            rs = preSt.executeQuery();

            bookMarks = new ArrayList<>();
            while (rs.next()) {
                BookMark bookMark  = new BookMark();
                bookMark.setId(String.valueOf(rs.getInt((1))));
                bookMark.setName(rs.getString(2));
                bookMark.setTurn(rs.getString(3));
                bookMark.setDate(rs.getString(4));
                bookMark.setDateNew(rs.getString(5));
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
