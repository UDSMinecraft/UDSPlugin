package com.undeadscythes.udsplugin.utilities;

import java.sql.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class SQLUtils {
    private static String path;
    private static Connection c = null;

    public static void init(String path) throws SQLException {
        SQLUtils.path = path;
        load();
        Statement stmt = c.createStatement();
        stmt.executeUpdate("CREATE TABLE members (name TEXT PRIMARY KEY NOT NULL, password TEXT, session TEXT, rank TEXT, rankid INT, email TEXT, real TEXT, time INT);");
        stmt.executeUpdate("CREATE TABLE online (loc TEXT PRIMARY KEY NOT NULL, members INT);");
        stmt.close();
        close();
    }

    public static void load() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + path);
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
