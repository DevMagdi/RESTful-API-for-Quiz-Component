package com.werewolves.quizsection.models.mySQLModels;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConnector {
    private static String databaseName = "jdbc:mysql://localhost/quizComponent";
    private static String username = "root";
    private static String password = "";
    private static Connection con;

    public static Boolean openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(databaseName, username, password);
            return true;
        } catch (Exception var1) {
            System.out.println(var1);
            return false;
        }
    }

    public static Boolean closeConnection() {
        try {
            con.close();
            return true;
        } catch (Exception var1) {
            return false;
        }
    }

    public static Boolean executeUpdate(String q) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(q);
            return true;
        } catch (Exception var2) {
            System.out.println(var2);
            return false;
        }
    }

    public static ResultSet executeQuery(String q) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(q);
            ResultSet rs = stmt.executeQuery(q);
            return rs;
        } catch (Exception var3) {
            System.out.println(var3);
            return null;
        }
    }

    public static int getIdOfTheLastAddedIn(String tableName) {
        openConnection();
        String q = "SELECT id FROM " + tableName + " ORDER BY id DESC limit 1";
        ResultSet rs = executeQuery(q);
        if (rs == null) {
            closeConnection();
            return -1;
        } else {
            try {
                rs.next();
                int id = rs.getInt("id");
                closeConnection();
                return id;
            } catch (Exception var4) {
                closeConnection();
                return -1;
            }
        }
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static void setDatabaseName(String databaseName) {
        databaseName = "jdbc:mysql://localhost/" + databaseName;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        password = password;
    }
}
