/*
 * JInternalFrameCustomers.java
 *
 * Created on 24/06/2009, 08:47:18 PM
 */
package pe.edu.unmsm.fisi.market.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.impl.CustomerBusiness;
import pe.edu.unmsm.fisi.market.model.Customer;

import java.util.Collection;

/**
 *
 * @author Eliana Zapata
 */
public class JInternalFrameCustomers extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = -8655880407433397905L;

    private static final Logger LOG = LoggerFactory.getLogger(JInternalFrameCustomers.class);

    private final CustomerBusiness customerBusiness;

    /**
     * Creates new form JInternalFrameInsertar
     */
    public JInternalFrameCustomers() {
        customerBusiness = CustomerBusiness.getInstance();
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

        javax.swing.JPanel panel = new javax.swing.JPanel();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        javax.swing.JButton buttonAdd = new javax.swing.JButton();
        javax.swing.JLabel labelTotal = new javax.swing.JLabel();
        labelTotalRows = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Customers");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apache_derby.png"))); // NOI18N

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new String [] {
                "Id", "Name", "City", "Phone", "Email"
            }, 0
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tableData.getTableHeader().setReorderingAllowed(false);
        tableData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDataMouseClicked(evt);
            }
        });
        tableData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableDataKeyPressed(evt);
            }
        });
        scrollPane.setViewportView(tableData);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        buttonAdd.setText("New customer");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        labelTotal.setText("Total:");

        labelTotalRows.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalRows.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 663, Short.MAX_VALUE)
                        .addComponent(labelTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTotalRows)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(buttonAdd)
                    .addComponent(labelTotal)
                    .addComponent(labelTotalRows))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        if (b) {
            refreshDataTable(customerBusiness.all());
        }

        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
    }

    private void refreshDataTable(Collection<Customer> customers) {
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) tableData.getModel();

        int rowCount = dtm.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dtm.removeRow(i);
        }

        customers.forEach(c -> {
            Object[] rowData = {
                c.getCustomerId(), c.getName(), c.getCity(), c.getPhone(), c.getEmail()
            };
            dtm.addRow(rowData);
        });
        labelTotalRows.setText(String.valueOf(customers.size()));
    }

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        LOG.trace(evt.paramString());
        JDialogCustomerForm customerForm = new JDialogCustomerForm(
                javax.swing.JOptionPane.getFrameForComponent(this), new Customer());
        customerForm.setVisible(true);

        if (customerForm.isActionPerformed()) {
            Collection<Customer> customers = customerBusiness.all();
            LOG.info("Customer has been saved, showing data");
            refreshDataTable(customers);
        } else {
            LOG.debug("No action has performed");
        }
    }//GEN-LAST:event_buttonAddActionPerformed

    private void tableDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataMouseClicked
        LOG.trace(evt.paramString());

        if (evt.getClickCount() == 2) {
            javax.swing.JTable target = (javax.swing.JTable) evt.getSource();
            int row = target.getSelectedRow();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) target.getModel();

            Integer customerId = (Integer) dtm.getValueAt(row, 0);
            LOG.debug("Getting customer identifier {}", customerId);

            showCustomer(customerId);
        }
    }//GEN-LAST:event_tableDataMouseClicked

    private void tableDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableDataKeyPressed
        int selectedRow = tableData.getSelectedRow();
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE && selectedRow != -1) {

            Integer customerId = (Integer) tableData.getValueAt(selectedRow, 0);
            deleteCustomerAction(customerId);
        }
    }//GEN-LAST:event_tableDataKeyPressed

    private void showCustomer(int customerId) {
        Customer customer = customerBusiness.findById(customerId);

        JDialogCustomerForm productForm = new JDialogCustomerForm(
                javax.swing.JOptionPane.getFrameForComponent(this), customer);
        productForm.setVisible(true);

        if (productForm.isActionPerformed()) {
            Collection<Customer> customers = customerBusiness.all();
            LOG.info("Product has been updated, showing data");
            refreshDataTable(customers);
        } else {
            LOG.debug("No action has performed");
        }
    }

    private void deleteCustomerAction(int customerId) {
        int optionChosen = javax.swing.JOptionPane.showConfirmDialog(this,
                "Seguro que desea eliminar el producto?", getTitle(),
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (javax.swing.JOptionPane.YES_OPTION == optionChosen) {

            if (customerBusiness.delete(customerId)) {
                Collection<Customer> customers = customerBusiness.all();
                LOG.info("Customer has been deleted, showing data");
                refreshDataTable(customers);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se pudo borrar el cliente.", getTitle(),
                        javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelTotalRows;
    private javax.swing.JTable tableData;
    // End of variables declaration//GEN-END:variables
}
