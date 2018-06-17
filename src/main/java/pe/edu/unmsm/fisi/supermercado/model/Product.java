/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.model;

import java.io.Serializable;

/**
 * @author soporte
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 2301296275576073714L;

    private int codigo;
    private String description;
    private double purchaseCost;
    private int quantityOnHand;
    private boolean available;

    public Product() {
        codigo = 0;
        description = "";
        purchaseCost = 0.0;
        quantityOnHand = 0;
    }

    public Product(int codigo, String description, double purchaseCost, int quantityOnHand) {
        this.codigo = codigo;
        this.description = description;
        this.purchaseCost = purchaseCost;
        this.quantityOnHand = quantityOnHand;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return codigo == ((Product) obj).getCodigo();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.codigo;
        return hash;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
