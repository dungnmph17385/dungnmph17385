/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class JdbcHelper {

    Connection conn;
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public JdbcHelper() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=Polypro;"
                    + "username=sa;password=a");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("ket noi that bai");
            ex.printStackTrace();
        }
    }

    public PreparedStatement getStmt(String sql, Object... args) {
        PreparedStatement psStmt = null;

        try {
            if (sql.trim().startsWith("{")) {
                psStmt = conn.prepareCall(sql);
            } else {
                psStmt = conn.prepareStatement(sql);
            }
            for (int i = 0; i < args.length; i++) {
                int n = i + 1;
                psStmt.setObject(n, args[i]);
            }
        } catch (SQLException e) {
            System.out.println("Loi getStmt");
            e.printStackTrace();
        }

        return psStmt;
    }

    public ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement ps = this.getStmt(sql, args);
        return ps.executeQuery();
    }

    public Object value(String sql, Object... args) {
        try {
            ResultSet rs = this.query(sql, args);
            if (rs.next()) {
                return rs.getObject(1);
            }
            rs.getStatement().getConnection().close();

        } catch (SQLException e) {
            System.out.println("loi");
            e.printStackTrace();
        }
        return null;
    }

    public int update(String sql, Object... args) {
        try {
            PreparedStatement ps = this.getStmt(sql, args);
//            try {
            return ps.executeUpdate();
//            } finally {
//                ps.getConnection().close();
//            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

}
