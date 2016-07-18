/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Db {
    
    private static Db singleton;
    
    private Db(){
        
    }
    
    public static Db getInstance(){
        if(singleton == null){
            singleton = new Db();
            
            try {
                // caricamento esplicito del driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
        return singleton;
    }
    
    public User getUser(String username, String password){
        Connection conn = null;
        User user = null;
        Statement stmt = null;
        try {
            conn = DriverManager
                    .getConnection(
                            "jdbc:derby://localhost:1527/davideSpano",
                            "davide", "password");
            stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(
                    "select * from appUser where username='"
                            + username + "' "
                            + "and password='"
                            + password +"'");
            while(set.next()){
                user = new User();
                user.setUsername(set.getString("username"));
                user.setPassword(set.getString("password"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally{
            try{
                if(stmt != null){
                    stmt.close();
                }
                
                if(conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(Db.class.getName())
                    .log(Level.SEVERE, null, ex);
            }
        }
        
        return user;
        
//        String usrFalso = "davide";
//        String pswFalso = "FPW";
//        
//        String usrFalso2 ="giovanni";
//        String pswFalso2 ="nuovo";
//        
//        if(username.equals(usrFalso) && 
//                password.equals(pswFalso)){
//            User usr = new User();
//            usr.setId(1);
//            usr.setUsername(username);
//            usr.setPassword(password);
//            return usr;
//        }
//        
//        if(username.equals(usrFalso2) &&
//                password.equals(pswFalso2)){
//            User usr = new User();
//            usr.setId(2);
//            usr.setUsername(username);
//            usr.setPassword(password);
//            return usr;
//        }
        
//        return null;

    }
        
}
