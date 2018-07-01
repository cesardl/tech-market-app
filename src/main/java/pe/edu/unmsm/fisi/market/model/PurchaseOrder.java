package pe.edu.unmsm.fisi.market.model;

import java.util.Date;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrder {

    private int orderNum;
    private Customer customer;
    private Product product;
    private int quantity;
    private Date salesDate;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }
}
