/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class RegisterServlet extends HttpServlet {
@PersistenceUnit (unitName = "WebAppProjPU")
EntityManagerFactory emf;

@Resource
UserTransaction utx;
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
            throws ServletException, IOException, RollbackFailureException, Exception {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        
        
        if (username != null && password != null && firstname != null && lastname != null) {
                if (username.trim().length() > 0 && password.trim().length() > 0 && firstname.trim().length() > 0
                        && lastname.trim().length() > 0) {
                    AccountJpaController actrl = new AccountJpaController(utx, emf);
                    Account account = new Account(actrl.getAccountCount() + 1);
                    int userInt = Integer.parseInt(username);
                    
                    account.setFirstname(firstname);
                    account.setLastname(lastname);
                    account.setUsername(userInt);
                    account.setPassword(password);

                    try {
                        actrl.create(account);
                        actrl.edit(account);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
                }
        }
        request.setAttribute("message", "error");
        getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        
    }
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String cusfname = request.getParameter("cusfname");
//        String cuslname = request.getParameter("cuslname");
//
//        if (username != null && username.trim().length() > 0 && 
//            password != null && password.trim().length() > 0 &&
//            cusfname != null && cusfname.trim().length() > 0 && 
//            cuslname != null && cuslname.trim().length() > 0 ) {
//            
//            String passwordEncrypt=cryptWithMD5(password);
//            
//            Register register = new Register();
//            register.setUsername(username);
//            register.setPassword(passwordEncrypt);
//            register.setCusfname(cusfname);
//            register.setCuslname(cuslname);
//            
//            RegisterJpaController regJpaCtrl = new RegisterJpaController(utx, emf);
//            regJpaCtrl.create(register);
//            getServletContext().getRequestDispatcher("/productList.jsp").forward(request, response);
//        }
//        
//        getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
//    }
//    public static String cryptWithMD5(String pass) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] passBytes = pass.getBytes();
//            md.reset();
//            byte[] digested = md.digest(passBytes);
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < digested.length; i++) {
//                sb.append(Integer.toHexString(0xff & digested[i]));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException ex) {
//            System.out.println(ex);
//        }
//        return null;
//    }

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
    try {
        processRequest(request, response);
    } catch (Exception ex) {
        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
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
    try {
        processRequest(request, response);
    } catch (Exception ex) {
        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
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
