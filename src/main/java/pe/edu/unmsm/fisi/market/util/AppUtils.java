/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Cesardl
 */
public final class AppUtils {

    public static final int ERROR_NUMBER = -99999;

    private static final Logger LOG = LoggerFactory.getLogger(AppUtils.class);

    private AppUtils() {
    }

    public static double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage());
            return ERROR_NUMBER;
        }
    }

    public static int toInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage());
            return ERROR_NUMBER;
        }
    }

    public static void displayInternalFrame(javax.swing.JDesktopPane desktopPane, javax.swing.JInternalFrame internalFrame) {
        if (!internalFrame.isVisible()) {
            if (!Arrays.asList(desktopPane.getAllFrames()).contains(internalFrame)) {
                desktopPane.add(internalFrame);
            }
            internalFrame.setVisible(true);
        }
    }

    public static void marcarTextField(javax.swing.JTextField jTextField) {
        jTextField.requestFocus();
        jTextField.selectAll();
    }
}
