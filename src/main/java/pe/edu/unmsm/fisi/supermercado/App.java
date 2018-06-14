/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.supermercado.view.Main;

/**
 * @author Cesardl
 */
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());

            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

            javax.swing.SwingUtilities.invokeLater(() -> new Main().setVisible(true));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
