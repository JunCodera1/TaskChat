package controllers;
import java.security.AlgorithmConstraints;
import java.sql.*;

public class DatabaseConnection {
    static final String DB_URL = "jdbc:mysql://localhost/taskchat";
    static final String USER = "root";
    static final String PASS = "123456";
    static final String QUERY = "SELECT idusersLogin, username, password FROM userslogin";

    public static void main(String[] args) {
        //Open connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);){
            //Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                System.out.println("ID: " + rs.getInt("idusersLogin"));
                System.out.println(", Username: " + rs.getString("username"));
                System.out.println(", Password: " + rs.getString("password"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
