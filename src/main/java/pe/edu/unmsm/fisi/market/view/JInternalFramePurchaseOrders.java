/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JInternalFramePurchaseOrders.java
 *
 * Created on 01/07/2009, 09:30:27 PM
 */
package pe.edu.unmsm.fisi.market.view;

import java.util.Collection;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.impl.CustomerBusiness;
import pe.edu.unmsm.fisi.market.business.impl.ProductBusiness;
import pe.edu.unmsm.fisi.market.business.impl.PurchaseOrderBusiness;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;
import pe.edu.unmsm.fisi.market.util.AppUtils;

/**
 *
 * @author Eliana Zapata
 */
public class JInternalFramePurchaseOrders extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 3611566289002788602L;

    private static final Logger LOG = LoggerFactory.getLogger(JInternalFramePurchaseOrders.class);

    private final CustomerBusiness customerBusiness;
    private final ProductBusiness productBusiness;
    private final PurchaseOrderBusiness purchaseOrderBusiness;
    private PurchaseOrder purchaseOrder;

    /**
     * Creates new form JInternalFrameVentas
     */
    public JInternalFramePurchaseOrders() {
        customerBusiness = CustomerBusiness.getInstance();
        productBusiness = ProductBusiness.getInstance();
        purchaseOrderBusiness = PurchaseOrderBusiness.getInstance();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel panelBuy = new javax.swing.JPanel();
        javax.swing.JLabel labelCustomer = new javax.swing.JLabel();
        comboBoxCustomer = new javax.swing.JComboBox<>();
        javax.swing.JLabel labelProduct = new javax.swing.JLabel();
        comboBoxProduct = new javax.swing.JComboBox<>();
        javax.swing.JLabel labelQuantity = new javax.swing.JLabel();
        spinnerQuantity = new javax.swing.JSpinner();
        javax.swing.JButton buttonBuy = new javax.swing.JButton();
        javax.swing.JPanel panelPurchaseOrders = new javax.swing.JPanel();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        tablePurchaseOrders = new javax.swing.JTable();
        javax.swing.JLabel jLabelTotal = new javax.swing.JLabel();
        labelTotalRows = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Venta de Productos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apache_derby.png"))); // NOI18N

        panelBuy.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Producto"));

        labelCustomer.setText("Cliente");

        labelProduct.setText("Producto");

        labelQuantity.setText("Cantidad");

        spinnerQuantity.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        buttonBuy.setText("Comprar");
        buttonBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBuyLayout = new javax.swing.GroupLayout(panelBuy);
        panelBuy.setLayout(panelBuyLayout);
        panelBuyLayout.setHorizontalGroup(
            panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelQuantity)
                    .addComponent(labelProduct)
                    .addComponent(labelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxProduct, 0, 353, Short.MAX_VALUE)
                    .addGroup(panelBuyLayout.createSequentialGroup()
                        .addComponent(spinnerQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                        .addGap(256, 256, 256))
                    .addComponent(comboBoxCustomer, 0, 353, Short.MAX_VALUE))
                .addGap(171, 171, 171)
                .addComponent(buttonBuy)
                .addContainerGap())
        );
        panelBuyLayout.setVerticalGroup(
            panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelCustomer)
                    .addComponent(comboBoxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboBoxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(spinnerQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelQuantity)
                    .addComponent(buttonBuy))
                .addGap(8, 8, 8))
        );

        panelPurchaseOrders.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenes de Compra"));

        tablePurchaseOrders.setModel(new javax.swing.table.DefaultTableModel(
            new String [] {
                "Number", "Customer", "Product", "Quantity", "Sales Date", "Shipping Date"
            }, 0
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.util.Date.class, java.util.Date.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePurchaseOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePurchaseOrdersMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(tablePurchaseOrders);

        javax.swing.GroupLayout panelPurchaseOrdersLayout = new javax.swing.GroupLayout(panelPurchaseOrders);
        panelPurchaseOrders.setLayout(panelPurchaseOrdersLayout);
        panelPurchaseOrdersLayout.setHorizontalGroup(
            panelPurchaseOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPurchaseOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane)
                .addContainerGap())
        );
        panelPurchaseOrdersLayout.setVerticalGroup(
            panelPurchaseOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPurchaseOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelTotal.setText("Total:");

        labelTotalRows.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalRows.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPurchaseOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotalRows)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPurchaseOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotal)
                    .addComponent(labelTotalRows))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        if (b) {
            comboBoxCustomer.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new java.util.Vector<>(customerBusiness.all())));

            comboBoxProduct.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new java.util.Vector<>(productBusiness.all())));

            refreshDataTable(purchaseOrderBusiness.all());
        }

        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
    }

    private void refreshDataTable(Collection<PurchaseOrder> purchaseOrders) {
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) tablePurchaseOrders.getModel();

        int rowCount = dtm.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dtm.removeRow(i);
        }

        purchaseOrders.forEach(po -> {
            Object[] rowData = {
                po.getOrderNum(), po.getCustomer(), po.getProduct(), po.getQuantity(), po.getSalesDate(), po.getShippingDate()
            };
            dtm.addRow(rowData);
        });
        labelTotalRows.setText(String.valueOf(purchaseOrders.size()));
    }

    private void buttonBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuyActionPerformed
        LOG.trace(evt.paramString());

        if (capturaDatos()) {
            boolean result = purchaseOrderBusiness.saveOrUpdate(purchaseOrder);
            if (result) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Se registró la orden de compra correctamente!",
                        getTitle(), javax.swing.JOptionPane.INFORMATION_MESSAGE);
                refreshDataTable(purchaseOrderBusiness.all());
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Ingrese correctamente los datos de compra",
                    getTitle(), javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonBuyActionPerformed

    private boolean capturaDatos() {
        int quantity = AppUtils.toInteger(String.valueOf(spinnerQuantity.getValue()));

        if (quantity == AppUtils.ERROR_NUMBER || quantity == 0) {
            spinnerQuantity.requestFocus();
            return false;
        }
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomer(comboBoxCustomer.getItemAt(comboBoxCustomer.getSelectedIndex()));
        purchaseOrder.setProduct(comboBoxProduct.getItemAt(comboBoxProduct.getSelectedIndex()));
        purchaseOrder.setQuantity(quantity);
        return true;
    }

    private void tablePurchaseOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePurchaseOrdersMouseClicked
        if (evt.getClickCount() == 2) {
            javax.swing.JTable target = (javax.swing.JTable) evt.getSource();
            int row = target.getSelectedRow();
            System.out.println(" double click " + row);
            DefaultTableModel dtm = (DefaultTableModel) target.getModel();

            // TODO Complete
            System.out.println(dtm.getValueAt(row, 0));
        }
    }//GEN-LAST:event_tablePurchaseOrdersMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Customer> comboBoxCustomer;
    private javax.swing.JComboBox<Product> comboBoxProduct;
    private javax.swing.JLabel labelTotalRows;
    private javax.swing.JSpinner spinnerQuantity;
    private javax.swing.JTable tablePurchaseOrders;
    // End of variables declaration//GEN-END:variables
}
