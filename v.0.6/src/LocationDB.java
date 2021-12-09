/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author scw
 */
public class LocationDB {

    String strDriver = "com.mysql.cj.jdbc.Driver";
    String strURL = "jdbc:mysql://127.0.0.1:3306/java_Project?characterEncoding=UTF-8&serverTimezone=UTC";
    String strUser = "root";
    String strPass = "scw31421@#";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public void dbOpen() throws IOException {
        try {
            Class.forName(strDriver);
            conn = DriverManager.getConnection(strURL, strUser, strPass);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    public void dbClose() throws IOException {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("SQLException : " + e.getMessage());
        }
    }
}
