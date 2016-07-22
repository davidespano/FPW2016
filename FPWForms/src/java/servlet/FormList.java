/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Db;
import model.Form;
import model.User;

/**
 *
 * @author davide
 */
@WebServlet(name = "FormList", urlPatterns = {"/formList.html"})
public class FormList extends HttpServlet {

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
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
       
        HttpSession session = request.getSession();
        User usr = (User) session.getAttribute("user");
        
        if(usr == null){
            // utente non e' autenticato
            // carica la jsp login.jsp all'interno della cartella jsp
            request.getRequestDispatcher("jsp/login.jsp")
                .forward(request, response);
            return;
        }
        
        // sono sicuro che l'utente sia autenticato
        
        if(request.getParameter("cmd") != null){
            if(request.getParameter("cmd").equals("new")){
                // dobbiamo creare un nuovo form
                int formId = Db.getInstance().createNewForm(usr);
                Form form = Db.getInstance().getFormById(formId);
                request.setAttribute("user", usr);
                request.setAttribute("form", form);
                request.getRequestDispatcher("jsp/editForm.jsp")
                .forward(request, response);
                return;
            }
        }
        
        List<Form> forms = Db.getInstance().getFormsByUser(usr);
        request.setAttribute("user", usr);
        request.setAttribute("forms", forms);
        request.getRequestDispatcher("jsp/formList.jsp")
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
