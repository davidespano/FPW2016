/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Db;
import model.Form;
import model.Option;
import model.Question;
import model.User;

/**
 *
 * @author davide
 */
@WebServlet(name = "EditForm", urlPatterns = {"/editForm.html"})
public class EditForm extends HttpServlet {

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
        
        if(request.getParameter("formId") != null){
            int formId = Integer.parseInt(request.getParameter("formId"));
            
            if(request.getParameter("cmd") != null){
                String cmd = request.getParameter("cmd");
                if(cmd.equals("saveForm")){
                    // l'utente ha premuto il pulsante per modificare
                    // i dati del form (il titolo). Effettuo l'update
                    // sul database
                    Form f = new Form();
                    f.setId(formId);
                    f.setTitle(request.getParameter("formTitle"));
                    Db.getInstance().updateForm(f);
                    
                }else if(cmd.equals("saveQuestion")){
                    // l'utente ha premuto il pulsante per
                    // salvare le modifiche ad una domanda
                    // contenuta nel form
                    // N.B. andrebbe controllato che tutti i parametri 
                    // della richiesta siano diversi da null
                    
                    Question q = new Question();
                    q.setTitle(request.getParameter("questionTitle"));
                    q.setId(Integer.parseInt(
                            request.getParameter("questionId")));
                    q.setOrder(Integer.parseInt(
                            request.getParameter("questionOrder")));
                    q.setType(Integer.parseInt(
                            request.getParameter("questionType")));
                    Db.getInstance().updateQuestion(q);
                    
                }else if(cmd.equals("saveOption")){
                    // l'utente ha premuto il pulsante per salvare le
                    // modifiche ad una opzione
                    // N.B. andrebbe controllato che tutti i parametri 
                    // della richiesta siano diversi da null
                    
                    Option o = new Option();
                    o.setValue(request.getParameter("optionValue"));
                    o.setId(Integer.parseInt(
                            request.getParameter("optionId")));
                    Db.getInstance().updateOption(o);
                }
            }
            
            Form form = Db.getInstance().getFormById(formId);
            request.setAttribute("form", form);
            request.getRequestDispatcher("jsp/editForm.jsp")
                .forward(request, response);
            return;
        }
        
        // non ho l'id del form, faccio una redirect alla pagina 
        // con la lista dei form
        response.sendRedirect("formList.html");
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
