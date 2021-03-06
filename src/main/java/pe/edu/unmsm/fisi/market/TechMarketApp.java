/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;
import pe.edu.unmsm.fisi.market.view.JFrameMain;

/**
 * @author Cesardl
 */
public class TechMarketApp {

    private static final Logger LOG = LoggerFactory.getLogger(TechMarketApp.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!ConnectionUtils.initialize()) {
            System.exit(1);
        }

        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());

            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

            javax.swing.SwingUtilities.invokeLater(() -> new JFrameMain().setVisible(true));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
