/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Db;
import model.User;

/**
 *
 * @author davide
 */
@WebServlet(name = "Register", urlPatterns = {"/register.html"})
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        if(request.getParameter("cmd") != null){
            String cmd = request.getParameter("cmd");
            if(cmd.equals("checkUsername")){
                // ho ricevuto una chiamata ajax
                // per controllare lo username
                boolean ok = false;
                
                User usr = Db.getInstance().getUserByUsername(
                        request.getParameter("username")
                );
                ok = (usr == null);
                
                request.setAttribute("ok", ok);
                
                request.getRequestDispatcher("jsp/checkUserJson.jsp")
                        .forward(request, response);
                return;
            }
            if(cmd.equals("registration")){
                User usr = new User();
                usr.setUsername(request.getParameter("username"));
                usr.setPassword(request.getParameter("password1"));
                usr.setSurname(request.getParameter("surname"));
                usr.setName(request.getParameter("name"));
                
                // inserisco l'utente nel db
                Db.getInstance().createUser(usr);
                
                // lo ricerco immediatamente per avere il valore dell'id 
                // generato dal db
                usr = Db.getInstance().getUserVer2(usr.getUsername(), usr.getPassword());
                
                // faccio la login in automatico
                HttpSession session = request.getSession();
                session.setAttribute("user", usr);
                response.sendRedirect("formList.html");
                return;
            }
        }
        
        request.getRequestDispatcher("jsp/registration.jsp")
                .forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
