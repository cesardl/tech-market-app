/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.impl.CustomerBusiness;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.DiscountCode;
import pe.edu.unmsm.fisi.market.model.MicroMarket;
import pe.edu.unmsm.fisi.market.util.AppUtils;
import pe.edu.unmsm.fisi.market.util.TechMarketValidator;

import java.util.Map;

/**
 * @author Cesardl
 */
public class JDialogCustomerForm extends javax.swing.JDialog {

    private static final long serialVersionUID = 1832568877971046252L;

    private static final Logger LOG = LoggerFactory.getLogger(JDialogCustomerForm.class);

    private final Customer customer;
    private final CustomerBusiness customerBusiness;

    private TechMarketValidator<Customer> validator;
    private boolean actionPerformed;
    private boolean errorProcessed;

    /**
     * @inheritDoc
     */
    public JDialogCustomerForm(java.awt.Frame parent, Customer customer) {
        super(parent);

        this.customerBusiness = CustomerBusiness.getInstance();
        this.customer = customer;
        this.validator = new TechMarketValidator<>();
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
        javax.swing.JLabel labelId = new javax.swing.JLabel();
        javax.swing.JLabel labelDiscountCode = new javax.swing.JLabel();
        javax.swing.JLabel labelMicroMarket = new javax.swing.JLabel();
        javax.swing.JLabel labelName = new javax.swing.JLabel();
        javax.swing.JLabel labelAddressLine1 = new javax.swing.JLabel();
        javax.swing.JLabel labelAddressLine2 = new javax.swing.JLabel();
        javax.swing.JLabel labelCity = new javax.swing.JLabel();
        javax.swing.JLabel labelState = new javax.swing.JLabel();
        javax.swing.JLabel labelPhone = new javax.swing.JLabel();
        javax.swing.JLabel labelFax = new javax.swing.JLabel();
        javax.swing.JLabel labelEmail = new javax.swing.JLabel();
        javax.swing.JLabel labelCreditLine = new javax.swing.JLabel();
        textFieldId = new javax.swing.JTextField();
        comboBoxDiscountCode = new javax.swing.JComboBox<>();
        comboBoxMicroMarket = new javax.swing.JComboBox<>();
        textFieldName = new javax.swing.JTextField();
        textFieldAddressLine1 = new javax.swing.JTextField();
        textFieldAddressLine2 = new javax.swing.JTextField();
        textFieldCity = new javax.swing.JTextField();
        textFieldState = new javax.swing.JTextField();
        textFieldPhone = new javax.swing.JTextField();
        textFieldFax = new javax.swing.JTextField();
        textFieldEmail = new javax.swing.JTextField();
        textFieldCreditLine = new javax.swing.JTextField();
        javax.swing.JButton buttonSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalle de Cliente");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/apache_derby.png")).getImage());
        setModal(true);
        setResizable(false);

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelId.setText("Id");

        labelDiscountCode.setText("Descuento");

        labelMicroMarket.setText("ZIP");

        labelName.setText("Nombre");

        labelAddressLine1.setText("Direccion (principal)");

        labelAddressLine2.setText("Direccion (extensión)");

        labelCity.setText("Ciudad");

        labelState.setText("Estado");

        labelPhone.setText("Telefono");

        labelFax.setText("Fax");

        labelEmail.setText("E-mail");

        labelCreditLine.setText("Línea de crédito");

        textFieldId.setEnabled(false);

        comboBoxDiscountCode.setFocusable(false);

        comboBoxMicroMarket.setFocusable(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDiscountCode)
                    .addComponent(labelId)
                    .addComponent(labelMicroMarket)
                    .addComponent(labelName)
                    .addComponent(labelAddressLine1)
                    .addComponent(labelAddressLine2)
                    .addComponent(labelCity)
                    .addComponent(labelState)
                    .addComponent(labelPhone)
                    .addComponent(labelFax)
                    .addComponent(labelEmail)
                    .addComponent(labelCreditLine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxDiscountCode, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxMicroMarket, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldAddressLine1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldAddressLine2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldState, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldFax, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldCreditLine, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelId)
                    .addComponent(textFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboBoxDiscountCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDiscountCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMicroMarket)
                    .addComponent(comboBoxMicroMarket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAddressLine1)
                    .addComponent(textFieldAddressLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAddressLine2)
                    .addComponent(textFieldAddressLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCity)
                    .addComponent(textFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelState)
                    .addComponent(textFieldState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPhone)
                    .addComponent(textFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFax)
                    .addComponent(textFieldFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEmail)
                    .addComponent(textFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCreditLine)
                    .addComponent(textFieldCreditLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonSave.setText("Guardar");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonSave)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        if (b) {
            comboBoxDiscountCode.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new java.util.Vector<>(customerBusiness.getDiscountCodes())));

            comboBoxMicroMarket.setModel(
                    new javax.swing.DefaultComboBoxModel<>(
                            new java.util.Vector<>(customerBusiness.getMicroMarkets())));

            if (customer.getCustomerId() != null) {
                showData();
            }
        }
        super.setVisible(b);
    }

    private void showData() {
        textFieldId.setText(String.valueOf(customer.getCustomerId()));

        for (int index = 0; index < comboBoxDiscountCode.getItemCount(); index++) {
            DiscountCode dc = comboBoxDiscountCode.getItemAt(index);
            if (dc.getDiscountCode().equals(customer.getDiscountCode().getDiscountCode())) {
                comboBoxDiscountCode.setSelectedItem(dc);
                break;
            }
        }

        for (int index = 0; index < comboBoxMicroMarket.getItemCount(); index++) {
            MicroMarket mm = comboBoxMicroMarket.getItemAt(index);
            if (mm.getZipCode().equals(customer.getMicroMarket().getZipCode())) {
                comboBoxMicroMarket.setSelectedItem(mm);
                break;
            }
        }

        textFieldName.setText(customer.getName());
        textFieldAddressLine1.setText(customer.getAddressLine1());
        textFieldAddressLine2.setText(customer.getAddressLine2());
        textFieldCity.setText(customer.getCity());
        textFieldState.setText(customer.getState());
        textFieldPhone.setText(customer.getPhone());
        textFieldFax.setText(customer.getFax());
        textFieldEmail.setText(customer.getEmail());
        textFieldCreditLine.setText(String.valueOf(customer.getCreditLimit()));
    }

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        LOG.trace(evt.paramString());

        errorProcessed = false;
        Map<String, String> errors = catchingData();
        if (errors.isEmpty()) {
            if (customerBusiness.saveOrUpdate(customer)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Se registró la orden de compra correctamente!",
                        getTitle(), javax.swing.JOptionPane.INFORMATION_MESSAGE);
                actionPerformed = true;
                dispose();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se puede agregar el cliente!",
                        getTitle(), javax.swing.JOptionPane.ERROR_MESSAGE);
                AppUtils.markTextField(textFieldName);
            }
        } else {
            processIfHasError("name", errors, textFieldName);
            processIfHasError("addressLine1", errors, textFieldAddressLine1);
            processIfHasError("addressLine2", errors, textFieldAddressLine2);
            processIfHasError("city", errors, textFieldCity);
            processIfHasError("state", errors, textFieldState);
            processIfHasError("phone", errors, textFieldPhone);
            processIfHasError("fax", errors, textFieldFax);
            processIfHasError("email", errors, textFieldEmail);
            processIfHasError("creditLimit", errors, textFieldCreditLine);
        }
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void processIfHasError(final String key, final Map<String, String> errors, final javax.swing.JTextField textField) {
        if (!errorProcessed && errors.containsKey(key)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    key.concat(" ").concat(errors.get(key)), getTitle(), javax.swing.JOptionPane.WARNING_MESSAGE);
            AppUtils.markTextField(textField);
            errorProcessed = true;
        }
    }

    private Map<String, String> catchingData() {
        String name = AppUtils.clean(textFieldName.getText());
        String addressLine1 = AppUtils.clean(textFieldAddressLine1.getText());
        String addressLine2 = AppUtils.clean(textFieldAddressLine2.getText());
        String city = AppUtils.clean(textFieldCity.getText());
        String state = AppUtils.clean(textFieldState.getText());
        String phone = AppUtils.clean(textFieldPhone.getText());
        String fax = AppUtils.clean(textFieldFax.getText());
        String email = AppUtils.clean(textFieldEmail.getText());
        Long creditLine = AppUtils.toLong(textFieldCreditLine.getText().trim());

        customer.setName(name);
        customer.setDiscountCode(comboBoxDiscountCode.getItemAt(comboBoxDiscountCode.getSelectedIndex()));
        customer.setMicroMarket(comboBoxMicroMarket.getItemAt(comboBoxMicroMarket.getSelectedIndex()));
        customer.setAddressLine1(addressLine1);
        customer.setAddressLine2(addressLine2);
        customer.setCity(city);
        customer.setState(state);
        customer.setPhone(phone);
        customer.setFax(fax);
        customer.setEmail(email);
        customer.setCreditLimit(creditLine);

        return validator.validate(customer);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<DiscountCode> comboBoxDiscountCode;
    private javax.swing.JComboBox<MicroMarket> comboBoxMicroMarket;
    private javax.swing.JTextField textFieldAddressLine1;
    private javax.swing.JTextField textFieldAddressLine2;
    private javax.swing.JTextField textFieldCity;
    private javax.swing.JTextField textFieldCreditLine;
    private javax.swing.JTextField textFieldEmail;
    private javax.swing.JTextField textFieldFax;
    private javax.swing.JTextField textFieldId;
    private javax.swing.JTextField textFieldName;
    private javax.swing.JTextField textFieldPhone;
    private javax.swing.JTextField textFieldState;
    // End of variables declaration//GEN-END:variables
}
