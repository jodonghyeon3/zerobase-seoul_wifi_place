package Controller;

import Dto.BookMarkGroup;

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

@WebServlet("/bookmark")
public class BookMarkGroupAddController extends HttpServlet {
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
            preSt = connection.prepareStatement("SELECT MAX(id) FROM bookmarkGroup");
            rs = preSt.executeQuery();
            if (rs.next()) {
                Id = rs.getInt(1) + 1;
            }
            preSt = connection.prepareStatement("INSERT INTO bookMarkGroup (id, name, turn, date) values (?, ?, ?, ?)");
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
    public List<BookMarkGroup> select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        List<BookMarkGroup> bookMarkGroups = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT * FROM bookMarkGroup");
            rs = preSt.executeQuery();

            bookMarkGroups = new ArrayList<>();
            while (rs.next()) {
                BookMarkGroup bookMarkGroup = new BookMarkGroup();
                bookMarkGroup.setId(String.valueOf(rs.getInt((1))));
                bookMarkGroup.setName(rs.getString(2));
                bookMarkGroup.setTurn(rs.getString(3));
                bookMarkGroup.setDate(rs.getString(4));
                bookMarkGroup.setDateNew(rs.getString(5));
                bookMarkGroups.add(bookMarkGroup);
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
        return bookMarkGroups;
    }


}
