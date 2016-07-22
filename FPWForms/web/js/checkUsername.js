/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
   $("#checkUsername").hide(); 
    
   $("#username").keyup(function(){
       $.ajax({
            url: "register.html",
            data :{
                cmd: "checkUsername",
                username: $("#username").val()
            },
            dataType: 'json',
            success: function(data, state){
                changeUsernameMessageState(data);
            },
            error: function(data, state){
                
            }
       });
       
       //alert($("#username").val());
   });
   
   function changeUsernameMessageState(check){
       if(check.ok){
           $("#checkUsername")
                   .addClass("ok")
                   .removeClass("error")
                   .text("Nome utente disponibile")
                   .show();
       }else{
           $("#checkUsername")
                   .addClass("error")
                   .removeClass("ok")
                   .text("Nome utente non disponibile")
                   .show();
       }
   }
});
