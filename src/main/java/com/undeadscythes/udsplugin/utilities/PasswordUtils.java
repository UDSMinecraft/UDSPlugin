package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.members.*;
import java.sql.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class PasswordUtils {
    private static final String LETTERS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPRSTUVWXYZ";
    private static final int PASSWORD_LENGTH = 8;

    public static String getPassword(String name) throws PasswordRetrievalException {
        Statement stmt;
        try {
            SQLUtils.load();
            stmt = SQLUtils.getStatement();
        } catch (SQLException ex) {
            SQLUtils.close();
            throw new PasswordRetrievalException(name + " - " + ex.getMessage());
        }
        String password = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT password FROM members WHERE LOWER(name)=\"" + name.toLowerCase() + "\";");
            rs.next();
            password = rs.getString("password");
        } catch(SQLException ex) {
            SQLUtils.close();
            throw new PasswordRetrievalException(name + " - " + ex.getMessage());
        }
        try {
            MemberUtils.saveMember(MemberUtils.getMember(name));
        } catch (NoPlayerFoundException ex) {}
        if(password != null && password.length() == 8) {
            SQLUtils.close();
            return password;
        }
        Random rng = new Random();
        String hash = "";
        while(hash.length() < PASSWORD_LENGTH) {
            int n = rng.nextInt(LETTERS.length());
            hash = hash.concat(LETTERS.substring(n, n + 1));
        }
        try {
            stmt.executeUpdate("UPDATE members SET password=\"" + hash + "\" WHERE LOWER(name)=\"" + name.toLowerCase() + "\";");
            stmt.close();
            SQLUtils.close();
        } catch(SQLException ex) {
            SQLUtils.close();
            throw new PasswordRetrievalException(name + " - " + ex.getMessage());
        }
        return hash;
    }

    private PasswordUtils() {}
}
