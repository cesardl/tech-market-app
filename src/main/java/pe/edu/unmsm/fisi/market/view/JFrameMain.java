/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JFrameMain.java
 *
 * Created on 24/06/2009, 08:39:57 PM
 */
package pe.edu.unmsm.fisi.market.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.util.AppUtils;

/**
 * @author Eliana Zapata
 * @since 2009
 */
public class JFrameMain extends javax.swing.JFrame {

    private static final long serialVersionUID = -7967665231395260595L;

    private static final Logger LOG = LoggerFactory.getLogger(JFrameMain.class);

    /**
     * Creates new form Main
     */
    public JFrameMain() {
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

        jDesktopPane = new javax.swing.JDesktopPane();
        jInternalFrameCustomers = new pe.edu.unmsm.fisi.market.view.JInternalFrameCustomers();
        jInternalFrameBuscar = new pe.edu.unmsm.fisi.market.view.JInternalFrameProducts();
        jInternalFrameVentas = new pe.edu.unmsm.fisi.market.view.JInternalFramePurchaseOrders();
        javax.swing.JMenuBar jMenuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu jMenuMantenimiento = new javax.swing.JMenu();
        javax.swing.JMenuItem jMenuItemCustomers = new javax.swing.JMenuItem();
        javax.swing.JMenuItem jMenuItemProducts = new javax.swing.JMenuItem();
        javax.swing.JMenuItem jMenuItemPurchaseOrders = new javax.swing.JMenuItem();
        javax.swing.JSeparator jSeparator = new javax.swing.JSeparator();
        javax.swing.JMenuItem jMenuItemExit = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenuHelp = new javax.swing.JMenu();
        javax.swing.JMenuItem jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tech market");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/apache_derby.png")).getImage());

        jDesktopPane.setBackground(new java.awt.Color(26, 185, 241));

        jMenuMantenimiento.setText("Archivo");

        jMenuItemCustomers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemCustomers.setText("Clientes");
        jMenuItemCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCustomersActionPerformed(evt);
            }
        });
        jMenuMantenimiento.add(jMenuItemCustomers);

        jMenuItemProducts.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemProducts.setText("Productos");
        jMenuItemProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProductsActionPerformed(evt);
            }
        });
        jMenuMantenimiento.add(jMenuItemProducts);

        jMenuItemPurchaseOrders.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemPurchaseOrders.setText("Ventas");
        jMenuItemPurchaseOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPurchaseOrdersActionPerformed(evt);
            }
        });
        jMenuMantenimiento.add(jMenuItemPurchaseOrders);
        jMenuMantenimiento.add(jSeparator);

        jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemExit.setText("Salir");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuMantenimiento.add(jMenuItemExit);

        jMenuBar.add(jMenuMantenimiento);

        jMenuHelp.setText("Ayuda");

        jMenuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAbout.setText("Acerca de");
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCustomersActionPerformed
        LOG.trace(evt.paramString());
        AppUtils.displayInternalFrame(jDesktopPane, jInternalFrameCustomers);
        LOG.debug("Mostrando frame interno de regisro de clientes");
    }//GEN-LAST:event_jMenuItemCustomersActionPerformed

    private void jMenuItemProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProductsActionPerformed
        LOG.trace(evt.paramString());
        AppUtils.displayInternalFrame(jDesktopPane, jInternalFrameBuscar);
        LOG.debug("Mostrando frame interno de busqueda de productos");
    }//GEN-LAST:event_jMenuItemProductsActionPerformed

    private void jMenuItemPurchaseOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPurchaseOrdersActionPerformed
        LOG.trace(evt.paramString());
        AppUtils.displayInternalFrame(jDesktopPane, jInternalFrameVentas);
        LOG.debug("Mostrando frame interno de venta de productos");
    }//GEN-LAST:event_jMenuItemPurchaseOrdersActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        LOG.trace(evt.paramString());
        int optionChosen = javax.swing.JOptionPane.showConfirmDialog(this,
                "Seguro que desea salir?", getTitle(),
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (javax.swing.JOptionPane.YES_OPTION == optionChosen) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        LOG.trace(evt.paramString());
        JDialogAbout dialog = new JDialogAbout(this);
        dialog.setTitle(getTitle());
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane;
    private pe.edu.unmsm.fisi.market.view.JInternalFrameCustomers jInternalFrameCustomers;
    private pe.edu.unmsm.fisi.market.view.JInternalFrameProducts jInternalFrameBuscar;
    private pe.edu.unmsm.fisi.market.view.JInternalFramePurchaseOrders jInternalFrameVentas;
    // End of variables declaration//GEN-END:variables
}
