/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
        }
        
        return singleton;
    }
    
    public User getUser(String username, String password){
        String usrFalso = "davide";
        String pswFalso = "FPW";
        
        if(username.equals(usrFalso) && 
                password.equals(pswFalso)){
            User usr = new User();
            usr.setId(1);
            usr.setUsername(username);
            usr.setPassword(password);
            return usr;
        }
        
        return null;
    }
        
}
