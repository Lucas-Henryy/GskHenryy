
package telas;

import classes.FormaPagamento;
import classes.Vendas;
import classesDAO.FormaPagamentoDAO;
import validacoes.Alerta;


public class Pagamento extends javax.swing.JDialog {

 private Vendas venda;
 
    public Pagamento(java.awt.Frame parent, boolean modal, Vendas venda) {
        super(parent, modal);
        initComponents();
        this.venda = venda;
        labelTotal.setText("R$ " + venda.getTotalVenda().toString());
    }

    public void formaPag(){
        if(cbFormaPag.getSelectedIndex() == 0){
            Alerta.Erro("Campo vazio", "Por favor, selecione uma forma de pagamento");
        } else if(tfNumCartao.getText().isBlank()){
            Alerta.Erro("Campo vazio", "Por favor, inserir o número do cartão");
        } else if(tfNomeCartao.getText().isBlank()){
            Alerta.Erro("Campo vazio", "Por favor, inserir o nome do cartão");
        } else if(cbMesData.getSelectedIndex() == 0){
            Alerta.Erro("Campo vazio", "Por favor, selecione um mês");
        } else if(cbAnoData.getSelectedIndex() == 0){
            Alerta.Erro("Campo vazio", "Por favor, selecione o ano");
        } else if(cbParcela.getSelectedIndex() == 0){
            Alerta.Erro("Campo vazio", "Por favor, selecione a quantidade de parcelas");
        }
        
        FormaPagamento formapag = new FormaPagamento();
        
        formapag.setMetodoPag(cbFormaPag.getSelectedItem().toString());
        formapag.setParcelasPag(Integer.parseInt(cbParcela.getSelectedItem().toString()));
        venda.setFormapagamento(formapag);
        formapag.setVenda(venda);
        
        
        FormaPagamentoDAO.cadastrarVenda(formapag);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbFormaPag = new javax.swing.JComboBox<>();
        tfNumCartao = new javax.swing.JTextField();
        cbAnoData = new javax.swing.JComboBox<>();
        cbMesData = new javax.swing.JComboBox<>();
        tfNomeCartao = new javax.swing.JTextField();
        cbParcela = new javax.swing.JComboBox<>();
        labelTotal = new javax.swing.JLabel();
        buttonPagar = new components.JCustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Formas de Pagamento");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Numero do Cartão:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Data de expiração:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Forma de Pagamento:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Parcelas:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Total:");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nome no Cartão:");

        cbFormaPag.setBackground(new java.awt.Color(255, 255, 255));
        cbFormaPag.setFont(new java.awt.Font("Segoe UI Semibold", 1, 25)); // NOI18N
        cbFormaPag.setForeground(new java.awt.Color(0, 0, 0));
        cbFormaPag.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "À vista", "Crédito", "Débito" }));

        tfNumCartao.setBackground(new java.awt.Color(255, 255, 255));
        tfNumCartao.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfNumCartao.setForeground(new java.awt.Color(0, 0, 0));

        cbAnoData.setBackground(new java.awt.Color(255, 255, 255));
        cbAnoData.setFont(new java.awt.Font("Segoe UI Semibold", 1, 25)); // NOI18N
        cbAnoData.setForeground(new java.awt.Color(0, 0, 0));
        cbAnoData.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "26", "27", "28", "29", "30", "31", "32" }));

        cbMesData.setBackground(new java.awt.Color(255, 255, 255));
        cbMesData.setFont(new java.awt.Font("Segoe UI Semibold", 1, 25)); // NOI18N
        cbMesData.setForeground(new java.awt.Color(0, 0, 0));
        cbMesData.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        tfNomeCartao.setBackground(new java.awt.Color(255, 255, 255));
        tfNomeCartao.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfNomeCartao.setForeground(new java.awt.Color(0, 0, 0));

        cbParcela.setBackground(new java.awt.Color(255, 255, 255));
        cbParcela.setFont(new java.awt.Font("Segoe UI Semibold", 1, 25)); // NOI18N
        cbParcela.setForeground(new java.awt.Color(0, 0, 0));
        cbParcela.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" }));

        labelTotal.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        labelTotal.setForeground(new java.awt.Color(0, 0, 0));

        buttonPagar.setForeground(new java.awt.Color(255, 255, 255));
        buttonPagar.setText("Pagar");
        buttonPagar.setFont(new java.awt.Font("Segoe UI Semibold", 3, 28)); // NOI18N
        buttonPagar.setRound(15);
        buttonPagar.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPagarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbFormaPag, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfNumCartao)
                            .addComponent(tfNomeCartao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbMesData, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(cbAnoData, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel1)))
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(381, 381, 381))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbFormaPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNumCartao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfNomeCartao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbMesData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAnoData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(buttonPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPagarActionPerformed
        formaPag();
        this.dispose();
    }//GEN-LAST:event_buttonPagarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.JCustomButton buttonPagar;
    private javax.swing.JComboBox<String> cbAnoData;
    private javax.swing.JComboBox<String> cbFormaPag;
    private javax.swing.JComboBox<String> cbMesData;
    private javax.swing.JComboBox<String> cbParcela;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JTextField tfNomeCartao;
    private javax.swing.JTextField tfNumCartao;
    // End of variables declaration//GEN-END:variables
}
