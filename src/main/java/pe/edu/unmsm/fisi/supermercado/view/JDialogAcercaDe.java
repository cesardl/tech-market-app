/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JDialogAcercaDe.java
 *
 * Created on 11/07/2009, 09:21:26 PM
 */
package pe.edu.unmsm.fisi.supermercado.view;

/**
 *
 * @author Cesardl
 */
public class JDialogAcercaDe extends javax.swing.JDialog {

    /**
     * Creates new form JDialogAcercaDe.
     *
     * @param parent the parent
     * @param modal is modality
     */
    public JDialogAcercaDe(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        javax.swing.JLabel jLabel = new javax.swing.JLabel();
        javax.swing.JButton jButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Supermercado");

        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel.setText("<html>\n<h2 align=center>Supermercado<br><i> Mantenimiendo de Productos</i></h2>\n<hr>\nImplementado por:<br>\n<ul><li>\nPablo  C&eacute;sar D&iacute;az Lurita\n</li></ul>\n\n<h5 align=right>CU, julio del 2009</h5>\n</html>");
        jLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton.setMnemonic('c');
        jButton.setText("Cerrar");
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
