/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jpa.model.Product;

/**
 *
 * @author piyao
 */
public class ShoppingCart implements Serializable {

    private Map<String, LineItem> cart;

    public ShoppingCart() {
        cart = new HashMap();
    }

    public void add(Product p) {
        LineItem line = cart.get(p.getProductid());
        if (line == null) {
            cart.put(p.getProductid(), new LineItem(p));
        } else {
            line.setQuantity(line.getQuantity() + 1);
        }
    }

    public void remove(Product p) {
        LineItem line = cart.get(p.getProductid());
        if (line == null) {
            cart.put(p.getProductid(), new LineItem(p));
        }
        if (line.getQuantity() > 1) {
            line.setQuantity(line.getQuantity() - 1);
        } else {
            cart.remove(p.getProductid());
        }

    }

    //cart.remove(p.getProductid());
    public void remove(String productCode) {
        cart.remove(productCode);
    }

    public Double getTotalPrice() {
        Double sum = 0d;
        Collection<LineItem> lineItems = cart.values();
        for (LineItem lineItem : lineItems) {
            sum += lineItem.getTotalPrice();
        }
        return sum;
    }

    public int getTotalQuantity() {
        int sum = 0;
        Collection<LineItem> lineItems = cart.values();
        for (LineItem lineItem : lineItems) {
            sum += lineItem.getQuantity();
        }
        return sum;
    }

    public List<LineItem> getLineItems() {
        return new ArrayList(cart.values());
    }
}
