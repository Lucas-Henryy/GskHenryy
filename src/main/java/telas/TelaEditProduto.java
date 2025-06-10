package telas;

import classes.Categoria;
import classes.Produto;
import classesDAO.ProdutoDAO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import validacoes.Alerta;

public class TelaEditProduto extends javax.swing.JDialog {

    private Produto produto;
    private List<Categoria> listaCategorias  = new ArrayList<>();
 
    public TelaEditProduto(java.awt.Frame parent, boolean modal, Produto produto) {
        super(parent, modal);
        initComponents();
        this.produto = produto;
        this.listaCategorias = ProdutoDAO.pegarCategoria();
        montarCampos();
        montarComboboxCategorias();
    }
    
    public void montarCampos() {
        tfNomeProd.setText(produto.getNome());
        tfPreco.setText(String.valueOf(produto.getPreco()));
        cbxCategoriaa.setSelectedItem(produto.getCategoria());
        
        tfQtd.setText(String.valueOf(produto.getQuantidade()));
        tfDescricao.setText(produto.getDescricao());
        tfCodigo.setText(produto.getCodigo());
    }
    
    public void montarComboboxCategorias() {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) cbxCategoriaa.getModel();
        modelo.removeAllElements();
        
        for (Categoria categoria : listaCategorias) {
            modelo.addElement(categoria.getNomeCategoria());
        }
        cbxCategoriaa.setModel(modelo);
    }

    public void salvarProduto() {

        if (tfNomeProd.getText().isBlank() && tfPreco.getText().isBlank()
                && tfCodigo.getText().isBlank()
                && tfQtd.getText().isBlank() && tfDescricao.getText().isBlank()) {

            Alerta.Erro("Todos os campos do Produto estão vazios!", "Campo Obrigatório");
        } else if (tfNomeProd.getText().isBlank()) {
            Alerta.Erro("Digite o nome do Produto!", "Campo Obrigatório");
        } else if (tfPreco.getText().isBlank()) {
            Alerta.Erro("Digite o Preço do Produto!", "Campo Obrigatório");
        } else if (tfCodigo.getText().isBlank()) {
            Alerta.Erro("Digite o Codigo do Produto", "Campo Obrigatório");
        } else if (tfQtd.getText().isBlank()) {
            Alerta.Erro("Digite a Quantidade!", "Campo Obrigatório");
        } else if (tfDescricao.getText().isBlank()) {
            Alerta.Erro("Digite uma Descrição!", "Campo Obrigatório");

        } else {
            Categoria categselecionado = (Categoria) listaCategorias.stream()
                    .filter(categoria -> categoria.getNomeCategoria().equals(cbxCategoriaa.getSelectedItem().toString()))
                    .findFirst()
                    .orElse(null);
            
            produto.setNome(tfNomeProd.getText());
            produto.setCategoria(categselecionado);
            produto.setCodigo(tfCodigo.getText());
            produto.setPreco(Double.parseDouble(tfPreco.getText()));
            produto.setQuantidade(Integer.parseInt(tfQtd.getText()));
            produto.setDescricao(tfDescricao.getText());
            
            ProdutoDAO.editarProduto(produto);
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tfNomeProd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfPreco = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfQtd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfDescricao = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        buttonSalvar = new components.JCustomButton();
        cbxCategoriaa = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nome:");

        tfNomeProd.setBackground(new java.awt.Color(217, 217, 217));
        tfNomeProd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfNomeProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfNomeProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeProdActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Preço:");

        tfPreco.setBackground(new java.awt.Color(217, 217, 217));
        tfPreco.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfPreco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfPreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPrecoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Codigo:");

        tfCodigo.setBackground(new java.awt.Color(217, 217, 217));
        tfCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodigoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Quantidade:");

        tfQtd.setBackground(new java.awt.Color(217, 217, 217));
        tfQtd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfQtd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfQtdActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Categoria:");

        jLabel10.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Descrição:");

        tfDescricao.setBackground(new java.awt.Color(217, 217, 217));
        tfDescricao.setColumns(20);
        tfDescricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfDescricao.setRows(5);
        tfDescricao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        jScrollPane1.setViewportView(tfDescricao);

        jLabel1.setFont(new java.awt.Font("Georgia", 3, 35)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Editar Cadastro do Produto");

        buttonSalvar.setForeground(new java.awt.Color(255, 255, 255));
        buttonSalvar.setText("Salvar");
        buttonSalvar.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonSalvar.setRound(15);
        buttonSalvar.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalvarActionPerformed(evt);
            }
        });

        cbxCategoriaa.setBackground(new java.awt.Color(217, 217, 217));
        cbxCategoriaa.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        cbxCategoriaa.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(65, 65, 65)
                                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxCategoriaa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(tfNomeProd)
                            .addComponent(jScrollPane1))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNomeProd)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                            .addComponent(tfPreco, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(cbxCategoriaa))))
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(buttonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfNomeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeProdActionPerformed

    private void tfPrecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPrecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPrecoActionPerformed

    private void tfCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodigoActionPerformed

    private void tfQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfQtdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQtdActionPerformed

    private void buttonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvarActionPerformed
        salvarProduto();
    }//GEN-LAST:event_buttonSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.JCustomButton buttonSalvar;
    private javax.swing.JComboBox<String> cbxCategoriaa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextArea tfDescricao;
    private javax.swing.JTextField tfNomeProd;
    private javax.swing.JTextField tfPreco;
    private javax.swing.JTextField tfQtd;
    // End of variables declaration//GEN-END:variables
}
