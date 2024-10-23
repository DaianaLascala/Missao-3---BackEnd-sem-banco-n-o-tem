/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Lascala
 */
public class ConectorBD {
     
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=SeuBancoDeDados";
        String user = "seuUsuario";
        String password = "suaSenha";
        return DriverManager.getConnection(url, user, password);
    }

    
    public static PreparedStatement getPrepared(String sql) throws SQLException {
        Connection conn = getConnection();
        return conn.prepareStatement(sql);
    }

    
    public static ResultSet getSelect(String sql) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(sql);
    }

    
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
