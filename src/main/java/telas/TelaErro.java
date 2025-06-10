
package telas;

public class TelaErro extends javax.swing.JDialog {

    public TelaErro(java.awt.Frame parent, boolean modal, String titulo, String mensagem) {
        super(parent, modal);
        initComponents();
        setTitle(titulo);
        telaErro.setText(mensagem);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        telaErro = new javax.swing.JLabel();
        jCustomButton28 = new components.JCustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        telaErro.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        telaErro.setForeground(new java.awt.Color(0, 0, 0));
        telaErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCustomButton28.setForeground(new java.awt.Color(255, 255, 255));
        jCustomButton28.setText("OK");
        jCustomButton28.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        jCustomButton28.setRound(15);
        jCustomButton28.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        jCustomButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCustomButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(telaErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(201, 201, 201)
                .addComponent(jCustomButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(telaErro, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jCustomButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCustomButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCustomButton28ActionPerformed
        dispose();
    }//GEN-LAST:event_jCustomButton28ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.JCustomButton jCustomButton28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel telaErro;
    // End of variables declaration//GEN-END:variables
}
