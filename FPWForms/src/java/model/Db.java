/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    private static final String dbConnectionUrl = 
            "jdbc:derby://localhost:1527/davideSpano";
    private static final String dbConnectionUser = "davide";
    private static final String dbConnectionPsw = "password";
    
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
    
    public User getUserVer0(String username, String password){
        String usrFalso = "davide";
        String pswFalso = "FPW";
        
        String usrFalso2 ="giovanni";
        String pswFalso2 ="nuovo";
        
        if(username.equals(usrFalso) && 
                password.equals(pswFalso)){
            User usr = new User();
            usr.setId(1);
            usr.setUsername(username);
            usr.setPassword(password);
            return usr;
        }
        
        if(username.equals(usrFalso2) &&
                password.equals(pswFalso2)){
            User usr = new User();
            usr.setId(2);
            usr.setUsername(username);
            usr.setPassword(password);
            return usr;
        }
        
        return null;
    }
    
    /**
     * Ricerca un utente sul db tramite username e password
     * N.B. Permette la sql injection
     * @param username lo username specificato dall'utente
     * @param password la password specificata dall'utente
     * @return l'utente corrispondente allo username e 
     * password specificati, null in caso non esista
     */
    public User getUserVer1(String username, String password){
        Connection conn = null;
        User user = null;
        Statement stmt = null;
        try {
            conn = DriverManager
                    .getConnection(
                            dbConnectionUrl,
                            dbConnectionUser, 
                            dbConnectionPsw);
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
        

    }
    
    
    /**
     * Ricerca un utente sul db tramite username e password
     * N.B. Non permette la sql injection
     * @param username lo username specificato dall'utente
     * @param password la password specificata dall'utente
     * @return l'utente corrispondente allo username e 
     * password specificati, null in caso non esista
     */
    public User getUserVer2(String username, String password){
        Connection conn = null;
        User user = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager
                    .getConnection(
                            dbConnectionUrl,
                            dbConnectionUser, 
                            dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "select * from appUser where "
                            + "username = ? and "
                            + "password = ?"
            );
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet set = stmt.executeQuery();
            
            while(set.next()){
                user = new User();
                user.setId(set.getInt("id"));
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
        

    }
        
}
