/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.Orders;
import jpa.model.Payment;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.OrdersJpaController;
import jpa.model.controller.PaymentJpaController;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class AddAddressServlet extends HttpServlet {

    @PersistenceUnit(unitName = "WebAppProjPU")
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
        String location = request.getParameter("location");
        
        if (location != null && location.trim().length() > 0) {
            AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
            Account account = (Account) request.getSession().getAttribute("customer");
            
            OrdersJpaController ordersJpaCtrl = new OrdersJpaController(utx, emf);
            Orders orders = new Orders(Integer.valueOf(location));
            
            if (account != null) {
                orders.setOrderid(orders.getOrderid());
                
                List<Orders> ordersList = account.getOrdersList();
                if (ordersList == null) {
                    ordersList = new ArrayList<>();
                }
                ordersList.add(orders);
                
                try {
                    ordersJpaCtrl.create(orders);
                    accountJpaCtrl.edit(account);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(AddAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(AddAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AddAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getSession().setAttribute("customer", account);
            }
            response.sendRedirect("Checkout");
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
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
