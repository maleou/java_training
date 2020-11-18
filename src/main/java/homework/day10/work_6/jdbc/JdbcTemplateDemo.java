package homework.day10.work_6.jdbc;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcTemplateDemo {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2B8";
        Connection con = DriverManager.getConnection(url, "root", "czmn12138");
        query(con, 1);
    }

    @SneakyThrows
    private static void query(Connection conn, Integer id) {
        String sql = "select * from people where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getInt("id"));
        }
    }

    @SneakyThrows
    private static void insert(Connection conn, Integer id, Integer name, String age) {
        String sql = "insert people(id, name, age) values(?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, name);
        preparedStatement.setString(3, age);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs);
    }

    @SneakyThrows
    private static void delete(Connection conn, Integer id) {
        String sql = "delete from people where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs);
    }

    @SneakyThrows
    private static void update(Connection conn, Integer id, Integer age) {
        String sql = "update people set age = ? where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, age);
        preparedStatement.setInt(2, id);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs);
    }
}
