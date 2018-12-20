/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.Address;
import jpa.model.Lineitem;
import jpa.model.Orders;
import jpa.model.Payment;
import jpa.model.Shipping;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.AddressJpaController;
import jpa.model.controller.LineitemJpaController;
import jpa.model.controller.OrdersJpaController;
import jpa.model.controller.PaymentJpaController;
import jpa.model.controller.ShippingJpaController;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;
import model.ShoppingCart;

/**
 *
 * @author piyao
 */
public class CheckoutServlet extends HttpServlet {

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
            throws ServletException, IOException {
        String shippingAddress = request.getParameter("addressId");
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
        Account account = (Account) request.getSession().getAttribute("customer");

        if (cart != null && account != null) {
            if (shippingAddress != null && shippingAddress.trim().length() > 0) {
                int addressId = Integer.valueOf(shippingAddress);

                //ORDER
                OrdersJpaController ordersJpaCtrl = new OrdersJpaController(utx, emf);
                Orders order = new Orders(new Date(), "Pending");
                order.setUsername(account);

                try {
                    ordersJpaCtrl.create(order);
                } catch (Exception ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //LINEITEM
                LineitemJpaController lineItemJpaCtrl = new LineitemJpaController(utx, emf);
                List<Lineitem> lineItems = cart.getLineItems();
                List<Lineitem> lineItemList = new ArrayList<>();

                if (lineItems != null) {
                    for (Lineitem lineItem : lineItems) {
                        Lineitem Lineitem = new Lineitem(order, lineItem.getProduct(), lineItem.getQuantity(), lineItem.getUnitprice());
                        lineItemList.add(Lineitem);
                        try {
                            lineItemJpaCtrl.create(Lineitem);
                        } catch (RollbackFailureException ex) {
                            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                //PAYMENT
                PaymentJpaController paymentJpaCtrl = new PaymentJpaController(utx, emf);
                Payment payment = new Payment("Credit Card", cart.getTotalPrice());
                payment.setOrderid(order);

                try {
                    paymentJpaCtrl.create(payment);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //SHIPPING
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, 0);
                Date shipDate = c.getTime();

                ShippingJpaController shippingJpaCtrl = new ShippingJpaController(utx, emf);
                Shipping shipping = new Shipping(shipDate, "Pending");
                shipping.setOrderid(order);

                AddressJpaController addressJpaCtrl = new AddressJpaController(utx, emf);
                Address address = addressJpaCtrl.findAddress(addressId);
                if (address != null) {
                    shipping.setAddressid(address);
                }

                try {
                    shippingJpaCtrl.create(shipping);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                order.setLineitemList(lineItemList);
                order.setPayment(payment);
                order.setShipping(shipping);

                try {
                    ordersJpaCtrl.edit(order);
                } catch (Exception ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                List<Orders> orderList = account.getOrdersList();
                if (orderList != null) {
                    orderList.add(order);
                }

                AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
                try {
                    accountJpaCtrl.edit(account);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //CLEAR CART
                request.getSession().setAttribute("cart", null);
                request.setAttribute("order", order);
                getServletContext().getRequestDispatcher("/cart/ConfirmOrder.jsp").forward(request, response);
                return;
            }
        } 
        else {
            response.sendRedirect("/index.html");
            return;
        }
        getServletContext().getRequestDispatcher("/cart/CheckOrder.jsp").forward(request, response);

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
