package pe.edu.unmsm.fisi.supermercado.model;

/**
 * Created on 14/06/2018.
 *
 * @author Cesardl
 */
public class ProductCode {

    private String prodCode;
    private String description;

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
