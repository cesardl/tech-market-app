/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.model;

import java.io.Serializable;

/**
 * @author cesar.diaz
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 2301296275576073714L;

    private Integer productId;
    private Manufacturer manufacturer;
    private ProductCode productCode;
    private Double purchaseCost;
    private Integer quantityOnHand;
    private Boolean available;
    private String description;

    public Product() {
    }

    public Product(Integer productId, String description, Double purchaseCost, Integer quantityOnHand) {
        this.productId = productId;
        this.description = description;
        this.purchaseCost = purchaseCost;
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public void setProductCode(ProductCode productCode) {
        this.productCode = productCode;
    }

    public Double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
