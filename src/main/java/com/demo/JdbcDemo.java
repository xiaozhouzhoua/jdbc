package com.demo;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("connection.is = " + connection.isValid(0));
            // 查询
            PreparedStatement ps = connection.prepareStatement("select * from USERS where name = ?");
            ps.setString(1, "Marco");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }

            // 新增
            PreparedStatement insertPs = connection.prepareStatement("insert into USERS (name) values (?)");
            insertPs.setString(1, "John");
            int insertCount = insertPs.executeUpdate();
            System.out.println("insertCount = " + insertCount);

            // 更新

            PreparedStatement updatePs = connection.prepareStatement("update USERS set name = ? where name = ?");
            updatePs.setString(1, "Johnny");
            updatePs.setString(2, "John");
            int updateCount = updatePs.executeUpdate();
            System.out.println("updateCount = " + updateCount);

            // 删除
            PreparedStatement deletePs = connection.prepareStatement("delete from USERS where name = ?");
            deletePs.setString(1, "Johnny");
            int deleteCount = deletePs.executeUpdate();
            System.out.println("deleteCount = " + deleteCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql'");
        return ds;
    }
}
