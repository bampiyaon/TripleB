/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;

/**
 *
 * @author piyao
 */
public class SearchServlet extends HttpServlet {

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
        String keyword = request.getParameter("keyword");
        String searchOption = request.getParameter("searchOption");

        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        List<Product> productList = productJpaCtrl.findProductEntities();
        List<Product> products = null;

       if(searchOption != null && searchOption.trim().length() > 0){
            products = new ArrayList<>();
            
            if (!searchOption.equalsIgnoreCase("all")) {
                if(productList != null){
                    for (Product product : productList) {
                        if(product.getShopid().getShopid().equals(searchOption)){
                        products.add(product);
                        }
                    }
                }
            }
            else{
                products = productList;
            }
       } 
        
        if (keyword != null && keyword.trim().length() > 0) {
            keyword = keyword.trim().toLowerCase();
            
            if(products != null){
                for (int i = 0; i < products.size(); i++) {
                    if(!products.get(i).getProductname().contains(keyword)){
                       products.remove(products.get(i));
                       i--;
                    }
                    
                }
            }

        }

//        if (products == null) {
//            products = productList;
//        }
//
//        if (searchOption != null && searchOption.trim().length() > 0) {
//            searchOption = searchOption.trim().toLowerCase();
//
//            if (!searchOption.equalsIgnoreCase("all")) {
//                if (products != null) {
//                    for (Product product : productList) {
//                        if (!product.getProductid().equalsIgnoreCase(searchOption)) {
//                            products.remove(product);
//                        }
//                    }
//                }
//            }
            request.setAttribute("products", products);
            getServletContext().getRequestDispatcher("/ProductList.jsp").forward(request, response);

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
