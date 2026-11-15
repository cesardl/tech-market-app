package pe.edu.unmsm.fisi.market.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrder {

    private Integer orderNum;
    private Customer customer;
    private Product product;

    @NotNull
    @Min(value = 1)
    private Integer quantity;

    /**
     * SHIPPING_COST is DECIMAL(12, 2), which holds ten digits before the point, so the
     * annotation says ten and not twelve: a wider cost used to pass validation and fail in
     * the database, where the DAO logs the error and the form can only report that the
     * order could not be saved. @NotNull because neither @Min nor @Digits applies to null,
     * so an empty box answered null and saved an order with no shipping cost at all.
     */
    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Min(value = 1)
    private BigDecimal shippingCost;

    private Date salesDate;

    // @FutureOrPresent
    private Date shippingDate;

    @Size(max = 30)
    private String freightCompany;

    public PurchaseOrder() {
        this.quantity = 0;
        this.shippingCost = BigDecimal.ZERO;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getFreightCompany() {
        return freightCompany;
    }

    public void setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
    }
}
