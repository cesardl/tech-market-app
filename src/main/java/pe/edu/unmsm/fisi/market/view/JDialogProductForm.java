/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.impl.ProductBusiness;
import pe.edu.unmsm.fisi.market.model.Manufacturer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.ProductCode;

import static pe.edu.unmsm.fisi.market.util.AppUtils.*;

/**
 * @author Cesardl
 */
public class JDialogProductForm extends javax.swing.JDialog {

    private static final long serialVersionUID = 2633032220209840428L;

    private static final Logger LOG = LoggerFactory.getLogger(JDialogProductForm.class);

    private final Product product;
    private final ProductBusiness productBusiness;

    private boolean actionPerformed;

    /**
     * @inheritDoc
     */
    public JDialogProductForm(java.awt.Frame parent, Product product) {
        super(parent);

        this.productBusiness = ProductBusiness.getInstance();
        this.product = product;
        this.actionPerformed = false;

        initComponents();
    }

    boolean isActionPerformed() {
        return actionPerformed;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel panel = new javax.swing.JPanel();
        javax.swing.JLabel labelProductId = new javax.swing.JLabel();
        javax.swing.JLabel labelManufacturer = new javax.swing.JLabel();
        javax.swing.JLabel labelProductCode = new javax.swing.JLabel();
        javax.swing.JLabel labelDescription = new javax.swing.JLabel();
        javax.swing.JLabel labelPurchaseCost = new javax.swing.JLabel();
        javax.swing.JLabel labelMarkup = new javax.swing.JLabel();
        javax.swing.JLabel labelQuantity = new javax.swing.JLabel();
        textFieldProductId = new javax.swing.JTextField();
        comboBoxManufacturer = new javax.swing.JComboBox<>();
        comboBoxProductCode = new javax.swing.JComboBox<>();
        textFieldDescription = new javax.swing.JTextField();
        textFieldPurchaseCost = new javax.swing.JTextField();
        textFieldMarkup = new javax.swing.JTextField();
        spinnerQuantity = new javax.swing.JSpinner();
        javax.swing.JButton buttonSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalle de Producto");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/apache_derby.png")).getImage());
        setModal(true);
        setResizable(false);

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        labelProductId.setText("Codigo");

        labelManufacturer.setText("Manufacturer");

        labelProductCode.setText("Tipo");

        labelDescription.setText("Descripcion");

        labelPurchaseCost.setText("Precio Unitario");

        labelMarkup.setText("Margen");

        labelQuantity.setText("Cantidad");

        textFieldProductId.setEditable(false);

        comboBoxManufacturer.setFocusable(false);

        comboBoxProductCode.setFocusable(false);

        spinnerQuantity.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(labelQuantity)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelManufacturer)
                                    .addComponent(labelProductId))
                                .addGap(22, 22, 22)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboBoxManufacturer, 0, 256, Short.MAX_VALUE)
                                    .addComponent(textFieldProductId)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelProductCode)
                                    .addComponent(labelDescription)
                                    .addComponent(labelPurchaseCost)
                                    .addComponent(labelMarkup))
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textFieldMarkup)
                                    .addComponent(textFieldPurchaseCost)
                                    .addComponent(textFieldDescription)
                                    .addComponent(comboBoxProductCode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(spinnerQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelProductId)
                    .addComponent(textFieldProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelManufacturer)
                    .addComponent(comboBoxManufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboBoxProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProductCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(textFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(textFieldPurchaseCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPurchaseCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(textFieldMarkup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMarkup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(spinnerQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelQuantity))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textFieldDescription.requestFocus();

        buttonSave.setText("Guardar");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonSave)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        if (b) {
            comboBoxManufacturer.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new java.util.Vector<>(productBusiness.getManufacturers())));

            comboBoxProductCode.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new java.util.Vector<>(productBusiness.getProductCodes())));

            if (product.getProductId() != null) {
                showData();
            }
        }
        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
    }

    private void showData() {
        for (int index = 0; index < comboBoxManufacturer.getItemCount(); index++) {
            Manufacturer m = comboBoxManufacturer.getItemAt(index);
            if (m.getManufacturerId().equals(product.getManufacturer().getManufacturerId())) {
                comboBoxManufacturer.setSelectedItem(m);
                break;
            }
        }

        textFieldProductId.setText(String.valueOf(product.getProductId()));

        for (int index = 0; index < comboBoxProductCode.getItemCount(); index++) {
            ProductCode pc = comboBoxProductCode.getItemAt(index);
            if (pc.getProdCode().equals(product.getProductCode().getProdCode())) {
                comboBoxProductCode.setSelectedItem(pc);
                break;
            }
        }

        textFieldDescription.setText(product.getDescription());
        textFieldPurchaseCost.setText(String.valueOf(product.getPurchaseCost()));
        textFieldMarkup.setText(String.valueOf(product.getMarkup()));
        spinnerQuantity.setValue(product.getQuantityOnHand());
    }

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        LOG.trace(evt.paramString());
        if (catchingData()) {
            if (productBusiness.saveOrUpdate(product)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Se agrego el producto correctamente!",
                        getTitle(), javax.swing.JOptionPane.INFORMATION_MESSAGE);
                actionPerformed = true;
                dispose();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se puede agregar el producto!",
                        getTitle(), javax.swing.JOptionPane.ERROR_MESSAGE);
                markTextField(textFieldDescription);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Ingrese correctamente los datos",
                    getTitle(), javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonSaveActionPerformed

    private boolean catchingData() {
        String description = textFieldDescription.getText().trim();
        double purchaseCost = toDouble(textFieldPurchaseCost.getText().trim());
        double markup = toDouble(textFieldMarkup.getText().trim());
        int quantityOnHand = toInteger(String.valueOf(spinnerQuantity.getValue()));

        if (description.length() == 0) {
            markTextField(textFieldDescription);
            return false;
        }
        if (purchaseCost == ERROR_NUMBER || purchaseCost == 0) {
            markTextField(textFieldPurchaseCost);
            return false;
        }
        if (markup == ERROR_NUMBER) {
            markTextField(textFieldMarkup);
            return false;
        }
        if (quantityOnHand == ERROR_NUMBER) {
            spinnerQuantity.requestFocus();
            return false;
        }

        product.setManufacturer(comboBoxManufacturer.getItemAt(comboBoxManufacturer.getSelectedIndex()));
        product.setProductCode(comboBoxProductCode.getItemAt(comboBoxProductCode.getSelectedIndex()));
        product.setPurchaseCost(purchaseCost);
        product.setMarkup(markup);
        product.setQuantityOnHand(quantityOnHand);
        product.setDescription(description);
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Manufacturer> comboBoxManufacturer;
    private javax.swing.JComboBox<ProductCode> comboBoxProductCode;
    private javax.swing.JSpinner spinnerQuantity;
    private javax.swing.JTextField textFieldDescription;
    private javax.swing.JTextField textFieldMarkup;
    private javax.swing.JTextField textFieldProductId;
    private javax.swing.JTextField textFieldPurchaseCost;
    // End of variables declaration//GEN-END:variables
}
