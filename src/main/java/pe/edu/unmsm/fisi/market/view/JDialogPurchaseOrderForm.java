/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

/**
 * @author Cesardl
 */
public class JDialogPurchaseOrderForm extends javax.swing.JDialog {

    private static final Logger LOG = LoggerFactory.getLogger(JDialogPurchaseOrderForm.class);

    private final PurchaseOrder purchaseOrder;

    private boolean actionPerformed;

    /**
     * @inheritDoc
     */
    public JDialogPurchaseOrderForm(java.awt.Frame parent, PurchaseOrder purchaseOrder) {
        super(parent);

        this.purchaseOrder = purchaseOrder;
        this.actionPerformed = false;

        initComponents();
    }

    public boolean isActionPerformed() {
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
        javax.swing.JLabel labelCustomer = new javax.swing.JLabel();
        javax.swing.JLabel labelProduct = new javax.swing.JLabel();
        javax.swing.JLabel labelQuantity = new javax.swing.JLabel();
        javax.swing.JLabel labelShippingCost = new javax.swing.JLabel();
        javax.swing.JLabel labelFreightCompany = new javax.swing.JLabel();
        textFieldCustomer = new javax.swing.JTextField();
        textFieldProduct = new javax.swing.JTextField();
        formattedTextFieldQuantity = new javax.swing.JFormattedTextField();
        formattedTextFieldShippingCost = new javax.swing.JFormattedTextField();
        comboBoxFreightCompany = new javax.swing.JComboBox<>();
        javax.swing.JButton buttonConfirm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Orden de Compra");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/apache_derby.png")).getImage());
        setModal(true);
        setResizable(false);

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelCustomer.setText("Cliente");

        labelProduct.setText("Producto");

        labelQuantity.setText("Cantidad");

        labelShippingCost.setText("Costo de envío");

        labelFreightCompany.setText("Compañia de transporte");

        textFieldCustomer.setEditable(false);

        textFieldProduct.setEditable(false);

        formattedTextFieldQuantity.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        formattedTextFieldShippingCost.setEditable(false);

        comboBoxFreightCompany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "Coastal Freight", "FR Express", "Poney Express", "Slow Snail", "Southern Delivery Service", "We deliver", "Western Fast" }));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(labelFreightCompany)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxFreightCompany, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelShippingCost)
                            .addComponent(labelQuantity)
                            .addComponent(labelProduct)
                            .addComponent(labelCustomer))
                        .addGap(14, 14, 14)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldProduct, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(formattedTextFieldQuantity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(formattedTextFieldShippingCost)
                            .addComponent(textFieldCustomer))))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(textFieldCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(textFieldProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(formattedTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelQuantity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(formattedTextFieldShippingCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelShippingCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboBoxFreightCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelFreightCompany))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonConfirm.setText("Confirmar compra");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 263, Short.MAX_VALUE)
                        .addComponent(buttonConfirm)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonConfirm)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        if (b) {
            textFieldCustomer.setText(purchaseOrder.getCustomer().getName());
            textFieldProduct.setText(purchaseOrder.getProduct().getDescription());
            formattedTextFieldQuantity.setText(String.valueOf(purchaseOrder.getQuantity()));
        }
        super.setVisible(b);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxFreightCompany;
    private javax.swing.JFormattedTextField formattedTextFieldQuantity;
    private javax.swing.JFormattedTextField formattedTextFieldShippingCost;
    private javax.swing.JTextField textFieldCustomer;
    private javax.swing.JTextField textFieldProduct;
    // End of variables declaration//GEN-END:variables
}
