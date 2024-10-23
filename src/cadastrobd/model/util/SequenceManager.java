/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Lascala
 */
public class SequenceManager {
    public static int getValue(String sequenceName) throws SQLException {
        int value = -1;
        String sql = "SELECT NEXT VALUE FOR " + sequenceName;

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
}

