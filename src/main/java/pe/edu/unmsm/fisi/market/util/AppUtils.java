/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Cesardl
 */
public final class AppUtils {

    public static final int ERROR_NUMBER = -99999;

    private static final Logger LOG = LoggerFactory.getLogger(AppUtils.class);

    private AppUtils() {
    }

    public static double toDouble(final String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage());
            return ERROR_NUMBER;
        }
    }

    public static int toInteger(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage());
            return ERROR_NUMBER;
        }
    }

    public static long toLong(final String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage());
            return ERROR_NUMBER;
        }
    }

    public static void displayInternalFrame(javax.swing.JDesktopPane desktopPane, javax.swing.JInternalFrame internalFrame) {
        if (!internalFrame.isVisible()) {
            if (!java.util.Arrays.asList(desktopPane.getAllFrames()).contains(internalFrame)) {
                desktopPane.add(internalFrame);
            }
            internalFrame.setVisible(true);
        }
    }

    public static String clean(String s) {
        s = s.trim();
        return s.length() == 0 ? null : s;
    }

    public static void markTextField(javax.swing.JTextField textField) {
        textField.requestFocus();
        textField.selectAll();
    }
}
