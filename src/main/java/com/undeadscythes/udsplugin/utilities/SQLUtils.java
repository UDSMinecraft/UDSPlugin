package com.undeadscythes.udsplugin.utilities;

import java.sql.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class SQLUtils {
    private static Connection c = null;

    public static void init() throws SQLException {
        try {
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/udsmc", "root", "");
        } catch (SQLException ex) {
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "");
            Statement stmt = c.createStatement();
            stmt.executeUpdate("CREATE DATABASE udsmc");
            c.close();
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/udsmc", "root", "");
        }
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE members ("
              + "name CHAR(12) NOT NULL, "
              + "PRIMARY KEY(name), "
              + "password CHAR(40), "
              + "session CHAR(64), "
              + "rank CHAR(10), "
              + "rankid INT, "
              + "email CHAR(30), "
              + "irlname CHAR(30), "
              + "time INT"
              + ")"
            );
            stmt.executeUpdate(
                "CREATE TABLE online ("
              + "loc CHAR(20) NOT NULL, "
              + "PRIMARY KEY(loc), "
              + "members INT"
              + ")");
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 1050) {
                Bukkit.getLogger().info(ex.getMessage());
            }
        }
    }

    public static void load() {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/udsmc", "root", "");
        } catch(Exception ex) {
            Bukkit.getLogger().info(ex.getMessage());
        }
    }

    public static void close() {
        try {
            c.close();
        } catch(SQLException ex) {}
    }

    public static Statement getStatement() throws SQLException {
        Statement stmt;
        stmt = c.createStatement();
        return stmt;
    }

    private SQLUtils() {}
}
