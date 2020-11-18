package homework.day10.work_6.jdbc;

import lombok.SneakyThrows;

import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2B8";
        Connection con = DriverManager.getConnection(url, "root", "czmn12138");
        query(con);
    }

    @SneakyThrows
    private static void query(Connection conn) {
        String sql = "select * from people";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt("id"));
        }
    }

    @SneakyThrows
    private static void insert(Connection conn, Integer id, Integer name, String age) {
        String sql = "insert people(id, name, age) values(?,?)";
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(sql);
        System.out.println(res);
    }

    @SneakyThrows
    private static void delete(Connection conn, Integer id) {
        String sql = "delete from people where id = ?";
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(sql);
        System.out.println(res);
    }

    @SneakyThrows
    private static void update(Connection conn, Integer id, Integer age) {
        String sql = "update people set age = ? where id = ?";

        Statement statement = conn.createStatement();
        int res = statement.executeUpdate(sql);
        System.out.println(res);
    }

}
