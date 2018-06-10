/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.model;

/**
 * @author soporte
 */
public class Producto {

    private int codigo;
    private String nombre;
    private double precUnit;
    private int cantidad;

    public Producto() {
        codigo = 0;
        nombre = "";
        precUnit = 0.0;
        cantidad = 0;
    }

    public Producto(int codigo, String nombre, double precUnit, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precUnit = precUnit;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecUnit() {
        return precUnit;
    }

    public void setPrecUnit(double precUnit) {
        this.precUnit = precUnit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Producto) {
            return codigo == ((Producto) obj).getCodigo();
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
        return "Codigo: " + codigo +
                "\nNombre: " + nombre +
                "\nPrecio Unitario: " + precUnit +
                "\nCantidad: " + cantidad;
    }
}
