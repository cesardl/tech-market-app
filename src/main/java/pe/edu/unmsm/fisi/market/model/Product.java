/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.model;

import java.io.Serializable;

/**
 * @author soporte
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 2301296275576073714L;

    private int productId;
    private Manufacturer manufacturer;
    private double purchaseCost;
    private int quantityOnHand;
    private boolean available;
    private String description;

    public Product() {
        productId = 0;
        description = "";
        purchaseCost = 0.0;
        quantityOnHand = 0;
    }

    public Product(int productId, String description, double purchaseCost, int quantityOnHand) {
        this.productId = productId;
        this.description = description;
        this.purchaseCost = purchaseCost;
        this.quantityOnHand = quantityOnHand;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return productId == ((Product) obj).getProductId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.productId;
        return hash;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
