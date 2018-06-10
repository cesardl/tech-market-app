/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Cesardl
 */
public class AppUtils {

    public static final int ERROR_NUMBER = -99999;

    private static final Logger LOG = LoggerFactory.getLogger(AppUtils.class);

    public static double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage(), nfe);
            return ERROR_NUMBER;
        }
    }

    public static int toInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe.getMessage(), nfe);
            return ERROR_NUMBER;
        }
    }

    public static void marcarTextField(javax.swing.JTextField jtextField) {
        jtextField.requestFocus();
        jtextField.selectAll();
    }
}
