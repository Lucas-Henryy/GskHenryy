package telas;

import classes.Cargo;
import classes.Categoria;
import classes.Cliente;
import classes.Funcionario;
import classes.ItemCarrinho;
import classes.Login;
import classes.Produto;
import classes.Venda;
import classes.Vendas;
import classesDAO.ClienteDAO;
import classesDAO.FuncionarioDAO;
import classesDAO.ProdutoDAO;
import classesDAO.VendaDAO;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import utilitarios.criptografia;
import validacoes.Alerta;

public class CadastroCliente extends javax.swing.JFrame {

    private List<Cargo> listaCargos = FuncionarioDAO.pegarCargos();
    private List<Categoria> listaCategorias = ProdutoDAO.pegarCategoria();
    private Login login;

    public CadastroCliente(Login login) {
        initComponents();
        this.login = login;
        tfData.setText(LocalDate.now().toString());
        montarComboboxCategorias();
        montarComboboxCargos();
    }

    public void montarComboboxCategorias() { //Método para montar um combobox com uma lista de Categorias que vem do banco de dados
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) cbxCategoria.getModel();
        modelo.removeAllElements();

        for (Categoria categoria : listaCategorias) {
            modelo.addElement(categoria.getNomeCategoria());
        }
        cbxCategoria.setModel(modelo);
    }
    
    public void montarComboboxCargos() { //Método para montar um combobox com uma lista de Cargos que vem do banco de dados
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) cbFCargos.getModel();
        modelo.removeAllElements();
        for (Cargo cargo : listaCargos) {
            modelo.addElement(cargo.getFuncao());
        }
        cbFCargos.setModel(modelo);
    }

    public void limparCampos(JTextField... campos) { //Método para limpar os textfield
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
    
    private Cliente cliente = new Cliente();
    private Produto produto = new Produto();
    private List<ItemCarrinho> carrinho = new ArrayList<>();

    public void listagemProdutos() {
        produto = VendaDAO.listarProdutos(tfCodVendas.getText());
        tfProdutoVendas.setText(produto.getNome());
        tfPrecoVendas.setText(String.valueOf(produto.getPreco()));
    }

    public void listarCPF() {
        cliente = VendaDAO.listarCPF(tfPesquisaCpf.getText());       
        Alerta.Cliente("Info", cliente.getCpf(), cliente.getTelefone());
    }
    
    public void limparTabela(){
        DefaultTableModel modelo = (DefaultTableModel) TabelaCadVendas.getModel();
        modelo.setRowCount(0);
        TabelaCadVendas.setModel(modelo);
    }

    public void tabelaCarrinho() {
        DefaultTableModel modelo = (DefaultTableModel) TabelaCadVendas.getModel();
        modelo.setRowCount(0);

        for (ItemCarrinho item : carrinho) {
            Double subtotal = item.getQtd() * item.getProduto().getPreco();
            
           /* String[] linha = {
                String.valueOf(item.getProduto().getCodigo()),
                item.getProduto().getNome(),
                String.valueOf(logo)
            };*/
            
            String[] linha = {
                String.valueOf(item.getProduto().getCodigo()), 
                item.getProduto().getNome(),
                String.valueOf(item.getQtd()),
                String.valueOf(item.getProduto().getPreco()),
                "R$" + subtotal.toString()};
            modelo.addRow(linha); 
        }
        TabelaCadVendas.setModel(modelo);
    }

    public void adcCarrinho() {
        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(produto);
        item.setQtd(Integer.valueOf(tfQtdVendas.getText()));

        carrinho.add(item);
        limparCampos(tfProdutoVendas, tfCodVendas, tfPrecoVendas, tfQtdVendas);

        tabelaCarrinho();
    }

    public void cadastroCliente() {

        if (tfNome.getText().isBlank() && tfcadastroClienteCpf.getText().isBlank()
                && cbxSexoClie.getSelectedIndex() == 0 && tfEmail1.getText().isBlank()
                && tfTelefone.getText().isBlank()) {

            Alerta.Erro("Todos os campos do Cliente estão vazios!", "Campo Obrigatório");
        } else if (tfNome.getText().isBlank()) {
            Alerta.Erro("Digite o nome do cliente!", "Campo Obrigatório");
        } else if (cbxSexoClie.getSelectedIndex() == 0) {
            Alerta.Erro("Selecione o sexo!", "Campo Obrigatório");
        } else if (tfcadastroClienteCpf.getText().isBlank()) {
            Alerta.Erro("Digite o CPF do cliente!", "Campo Obrigatório");
        } else if (tfTelefone.getText().isBlank()) {
            Alerta.Erro("Digite o telefone!", "Campo Obrigatório");
        } else if (tfEmail1.getText().isBlank()) {
            Alerta.Erro("Digite o email!", "Campo Obrigatório");

        } else {

            // Cadastro de novo cliente
            Cliente cliente = new Cliente();
            cliente.setNome(tfNome.getText());
            cliente.setCpf(tfcadastroClienteCpf.getText());
            cliente.setSexo(cbxSexoClie.getSelectedItem().toString());
            cliente.setTelefone(tfTelefone.getText());
            cliente.setEmail(tfEmail1.getText());

            ClienteDAO.cadastrarCliente(cliente);

            limparCampos(tfNome, tfcadastroClienteCpf, tfEmail1, tfTelefone);
            cbxSexoClie.setSelectedIndex(-1);
        }
    }

    public void cadastroProduto() {

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
                    .filter(categoria -> categoria.getNomeCategoria().equals(cbxCategoria.getSelectedItem().toString()))
                    .findFirst()
                    .orElse(null);

            // Cadastro de novo Produto
            Produto produto = new Produto();
            produto.setNome(tfNomeProd.getText());
            produto.setPreco(Double.parseDouble(tfPreco.getText()));
            produto.setCodigo(tfCodigo.getText());
            produto.setQuantidade(Integer.parseInt(tfQtd.getText()));
            produto.setDescricao(tfDescricao.getText());
            produto.setCategoria(categselecionado);

            ProdutoDAO.cadastrarProduto(produto);

            limparCampos(tfNomeProd, tfPreco, tfCodigo, tfQtd);
            tfDescricao.setText("");
            cbxSexoClie.setSelectedIndex(-1);
        }
    }

    public void cadastroFuncionario() { //Método para cadastrar o funcionário no banco de dados
        String passwordFunc = tfFSenha.getText();

        if (tfFNome.getText().isBlank() && tfFCpf.getText().isBlank()
                && tfFCep.getText().isBlank() && tfFLogradouro.getText().isBlank()
                && tfFNum.getText().isBlank() && tfFComplemento.getText().isBlank()
                && tfFLogin.getText().isBlank()
                && passwordFunc.isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir os dados");
        } else if (tfFNome.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o nome");
        } else if (tfFCpf.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o CPF");
        } else if (tfFCep.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o CEP");
        } else if (tfFLogradouro.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o logradouro");
        } else if (tfFNum.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o numero");
        } else if (tfFComplemento.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o complemento");
        } else if (tfFLogin.getText().isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir o login");
        } else if (passwordFunc.isBlank()) {
            Alerta.Erro("Campo vazio", "Por favor, inserir a senha");
        } else {

            Login log = new Login();
            log.setLogin(tfFLogin.getText());
            log.setSenha(criptografia.toMD5(passwordFunc));

            Cargo cargoselecionado = (Cargo) listaCargos.stream()
                    .filter(cargo -> cargo.getFuncao().equals(cbFCargos.getSelectedItem().toString()))
                    .findFirst()
                    .orElse(null);

            Funcionario func = new Funcionario();
            func.setNomeF(tfFNome.getText());
            func.setCpfF(tfFCpf.getText());
            func.setCep(tfFCep.getText());
            func.setLogradouro(tfFLogradouro.getText());
            func.setNumero(tfFNum.getText());
            func.setComplemento(tfFComplemento.getText());
            func.setTelefoneF(tfFTelefone.getText());
            func.setLogin(log);
            log.setFuncionario(func);
            func.setCargo(cargoselecionado);

            FuncionarioDAO.cadastrarFuncionario(func);

            limparCampos(tfFNome, tfFCpf, tfFCep, tfFLogradouro,
                    tfFNum, tfFComplemento, tfFTelefone, tfFLogin, tfFSenha);
            cbFCargos.setSelectedIndex(-1);

        }

    }

    public void cadastroVendas() {
        Vendas venda = new Vendas();
        venda.setCliente(cliente);
        venda.setFuncionario(login.getFuncionario());
        List<Produto> produtos = new ArrayList<>();
        Double valorTotal = 0.0;
        int qtdprod = 0;
        
        for(ItemCarrinho item : carrinho){
            produtos.add(item.getProduto());
            valorTotal = valorTotal + (item.getProduto().getPreco() * item.getQtd());
            qtdprod = qtdprod + item.getQtd();
            int qtdEstoque = item.getProduto().getQuantidade();
            int qtdEstoqueAtz = qtdEstoque - item.getQtd();
            item.getProduto().setQuantidade(qtdEstoqueAtz);
        }
        venda.setProd(produtos);
        venda.setDataVenda(LocalDate.now());
        venda.setTotalVenda(valorTotal);
        venda.setQtdVenda(qtdprod);
        new Pagamento(this, rootPaneCheckingEnabled, venda).setVisible(true);

        
    }

    public void listaClientes() { //Método para listar os clientes que foram cadastrados no banco de dados
        DefaultTableModel modelo = (DefaultTableModel) tabelaExibCliente.getModel();
        modelo.setRowCount(0);
        List<Cliente> lista = ClienteDAO.listarClientes(tfExibCliente.getText());

        for (Cliente cliente : lista) {
            String[] linha = {cliente.getId().toString(), cliente.getNome(),
                cliente.getCpf(), cliente.getSexo(), cliente.getTelefone()};
            modelo.addRow(linha);
        }
        tabelaExibCliente.setModel(modelo);
    }

    public void listaProdutos() {  //Método para listar os produtos que foram cadastrados no banco de dados
        DefaultTableModel modelo = (DefaultTableModel) tableExibProd.getModel();
        modelo.setRowCount(0);
        List<Produto> lista = ProdutoDAO.listarProdutos(tfCódigoExibProd.getText());

        for (Produto produto : lista) {
            String[] linha = {produto.getId().toString(), String.valueOf(produto.getCodigo()), produto.getNome(),
                produto.getCategoria().getNomeCategoria(), String.valueOf(produto.getQuantidade()),
                String.valueOf(produto.getPreco()), produto.getDescricao()};
            modelo.addRow(linha);
        }
        tableExibProd.setModel(modelo);
    }
    
    public void listaVendas() {  //Método para listar as vendas que foram cadastrados no banco de dados
        DefaultTableModel modelo = (DefaultTableModel) tableExibirVendas.getModel();
        modelo.setRowCount(0);
        List<Vendas> lista = VendaDAO.listarVendas(tfBuscarVendas.getText());

        for (Vendas venda : lista) {
            String[] linha = {venda.getId().toString(), venda.getCliente().getNome(), 
                String.valueOf(venda.getDataVenda()), String.valueOf(venda.getQtdVenda()),
            String.valueOf(venda.getTotalVenda())};
            modelo.addRow(linha);
        }
        tableExibirVendas.setModel(modelo);
    }

    public void editarClientes() {  //Método para editar os clientes que foram cadastrados no banco de dados
        int linhaSelecionada = tabelaExibCliente.getSelectedRow();

        if (linhaSelecionada == -1) {
            Alerta.Erro("Erro na linha", "Nenhuma linha selecionada");
        } else {
            String idCliente = (String) tabelaExibCliente.getValueAt(linhaSelecionada, 0);

            new TelaEditCliente(this,
                    rootPaneCheckingEnabled,
                    ClienteDAO.listarCliente(idCliente)).setVisible(true);
        }
        listaClientes();
    }

    public void editarProdutos() {  //Método para editar os produtos que foram cadastrados no banco de dados
        int linhaSelecionada = tableExibProd.getSelectedRow();

        if (linhaSelecionada == -1) {
            Alerta.Erro("Erro na linha", "Nenhuma linha selecionada");
        } else {
            String idProduto = (String) tableExibProd.getValueAt(linhaSelecionada, 0);

            new TelaEditProduto(this,
                    rootPaneCheckingEnabled,
                    ProdutoDAO.listarProduto(idProduto)).setVisible(true);
        }
        listaProdutos();
    }

    public void excluirClientes() { ////Método para excluir os clientes que foram cadastrados no banco de dados
        int linhaSelecionada = tabelaExibCliente.getSelectedRow();

        if (linhaSelecionada == -1) {
            Alerta.Erro("Erro na linha", "Nenhuma linha selecionada");
        } else {
            String idCliente = (String) tabelaExibCliente.getValueAt(linhaSelecionada, 0);

            ClienteDAO.excluirClientes(idCliente);
        }
        listaClientes();
    }

    public void excluirProdutos() { ////Método para excluir os produtos que foram cadastrados no banco de dados
        int linhaSelecionada = tableExibProd.getSelectedRow();

        if (linhaSelecionada == -1) {
            Alerta.Erro("Erro na linha", "Nenhuma linha selecionada");
        } else {
            String idProduto = (String) tableExibProd.getValueAt(linhaSelecionada, 0);

            ProdutoDAO.excluirProdutos(idProduto);
        }
        listaProdutos();
    }
    
    public void excluirVendas() { ////Método para excluir as vendas que foram cadastrados no banco de dados
        int linhaSelecionada = tableExibirVendas.getSelectedRow();

        if (linhaSelecionada == -1) {
            Alerta.Erro("Erro na linha", "Nenhuma linha selecionada");
        } else {
            String idVenda = (String) tableExibirVendas.getValueAt(linhaSelecionada, 0);

            VendaDAO.excluirVendas(idVenda);
        }
        listaVendas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        CadastroProduto = new components.JCustomButton();
        ExibirVendas = new components.JCustomButton();
        ExibirProduto = new components.JCustomButton();
        CadastroVendas = new components.JCustomButton();
        ExibirCliente = new components.JCustomButton();
        ButtonSair = new components.JCustomButton();
        CadastroCliente1 = new components.JCustomButton();
        CadastroCliente = new components.JCustomButton();
        paneltelas = new javax.swing.JPanel();
        CadastroUsuario = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tfFCep = new javax.swing.JTextField();
        tfFNome = new javax.swing.JTextField();
        cbFCargos = new javax.swing.JComboBox<>();
        tfFCpf = new javax.swing.JTextField();
        tfFNum = new javax.swing.JTextField();
        tfFComplemento = new javax.swing.JTextField();
        tfFLogradouro = new javax.swing.JTextField();
        tfFTelefone = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        tfFSenha = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        tfFLogin = new javax.swing.JTextField();
        buttonLimpar1 = new components.JCustomButton();
        buttonCadastrar1 = new components.JCustomButton();
        cadastroCliente = new javax.swing.JPanel();
        labelNomeCliente = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        labelSexo = new javax.swing.JLabel();
        labelcpff = new javax.swing.JLabel();
        tfcadastroClienteCpf = new javax.swing.JTextField();
        labelTelefone = new javax.swing.JLabel();
        tfTelefone = new javax.swing.JTextField();
        buttonCadastrar = new components.JCustomButton();
        buttonLimpar = new components.JCustomButton();
        cbxSexoClie = new javax.swing.JComboBox<>();
        labelEmail1 = new javax.swing.JLabel();
        tfEmail1 = new javax.swing.JTextField();
        cadastroProduto = new javax.swing.JPanel();
        labelNomeProd = new javax.swing.JLabel();
        tfNomeProd = new javax.swing.JTextField();
        labelPreco = new javax.swing.JLabel();
        tfQtd = new javax.swing.JTextField();
        labelQtd = new javax.swing.JLabel();
        labelCodigo = new javax.swing.JLabel();
        tfPreco = new javax.swing.JTextField();
        labelCategoriaProd = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        labelDescricao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfDescricao = new javax.swing.JTextArea();
        buttonLimparProd = new components.JCustomButton();
        buttonCadastrarProd = new components.JCustomButton();
        cbxCategoria = new javax.swing.JComboBox<>();
        cadastroVendas = new javax.swing.JPanel();
        tfProdutoVendas = new javax.swing.JTextField();
        labelProduto = new javax.swing.JLabel();
        labelCodVendas = new javax.swing.JLabel();
        tfCodVendas = new javax.swing.JTextField();
        labelData = new javax.swing.JLabel();
        tfData = new javax.swing.JTextField();
        labelPrecoVendas = new javax.swing.JLabel();
        tfPrecoVendas = new javax.swing.JTextField();
        labelQtdVendas = new javax.swing.JLabel();
        tfQtdVendas = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaCadVendas = new javax.swing.JTable();
        buttonAdicVendas = new components.JCustomButton();
        buttonCancVendas = new components.JCustomButton();
        buttonPesquisar = new components.JCustomButton();
        labelProduto1 = new javax.swing.JLabel();
        tfPesquisaCpf = new javax.swing.JTextField();
        buttonPesquisarCPF = new components.JCustomButton();
        exibirClientes = new javax.swing.JPanel();
        labelExibCliente = new javax.swing.JLabel();
        tfExibCliente = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaExibCliente = new javax.swing.JTable();
        buttonExibEdit = new components.JCustomButton();
        buttonExibExcluir = new components.JCustomButton();
        buttonExibPesquisar = new components.JCustomButton();
        exibirProdutos = new javax.swing.JPanel();
        labelCódigoExibProd = new javax.swing.JLabel();
        tfCódigoExibProd = new javax.swing.JTextField();
        buttonExibProd = new components.JCustomButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableExibProd = new javax.swing.JTable();
        buttonExibExcluirProd = new components.JCustomButton();
        buttonExibExcluirProd1 = new components.JCustomButton();
        exibirVendas = new javax.swing.JPanel();
        labelExibirCliente = new javax.swing.JLabel();
        tfBuscarVendas = new javax.swing.JTextField();
        buttonExibirPesquisa = new components.JCustomButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableExibirVendas = new javax.swing.JTable();
        buttonExibirClienteExcluir = new components.JCustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidebar.setBackground(new java.awt.Color(205, 205, 205));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HenryP.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(205, 205, 205));

        CadastroProduto.setForeground(new java.awt.Color(255, 255, 255));
        CadastroProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconProduto.png"))); // NOI18N
        CadastroProduto.setText("Cadastrar Produto");
        CadastroProduto.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        CadastroProduto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CadastroProduto.setIconTextGap(15);
        CadastroProduto.setRound(20);
        CadastroProduto.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        CadastroProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastroProdutoActionPerformed(evt);
            }
        });

        ExibirVendas.setForeground(new java.awt.Color(255, 255, 255));
        ExibirVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconExibirVend.png"))); // NOI18N
        ExibirVendas.setText("Exibir Vendas");
        ExibirVendas.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        ExibirVendas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ExibirVendas.setIconTextGap(15);
        ExibirVendas.setRound(20);
        ExibirVendas.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        ExibirVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExibirVendasActionPerformed(evt);
            }
        });

        ExibirProduto.setForeground(new java.awt.Color(255, 255, 255));
        ExibirProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconExibirProd.png"))); // NOI18N
        ExibirProduto.setText("Exibir Produtos");
        ExibirProduto.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        ExibirProduto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ExibirProduto.setIconTextGap(15);
        ExibirProduto.setRound(20);
        ExibirProduto.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        ExibirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExibirProdutoActionPerformed(evt);
            }
        });

        CadastroVendas.setForeground(new java.awt.Color(255, 255, 255));
        CadastroVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconVendas.png"))); // NOI18N
        CadastroVendas.setText("Cadastrar Vendas");
        CadastroVendas.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        CadastroVendas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CadastroVendas.setIconTextGap(15);
        CadastroVendas.setRound(20);
        CadastroVendas.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        CadastroVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastroVendasActionPerformed(evt);
            }
        });

        ExibirCliente.setForeground(new java.awt.Color(255, 255, 255));
        ExibirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconExibirCli.png"))); // NOI18N
        ExibirCliente.setText("Exibir Cliente");
        ExibirCliente.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        ExibirCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ExibirCliente.setIconTextGap(15);
        ExibirCliente.setRound(20);
        ExibirCliente.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        ExibirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExibirClienteActionPerformed(evt);
            }
        });

        ButtonSair.setForeground(new java.awt.Color(255, 255, 255));
        ButtonSair.setText("Sair");
        ButtonSair.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        ButtonSair.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        ButtonSair.setIconTextGap(5);
        ButtonSair.setRound(15);
        ButtonSair.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        ButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSairActionPerformed(evt);
            }
        });

        CadastroCliente1.setBackground(new java.awt.Color(255, 255, 255));
        CadastroCliente1.setForeground(new java.awt.Color(255, 255, 255));
        CadastroCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconCliente.png"))); // NOI18N
        CadastroCliente1.setText("Cadastrar Cliente");
        CadastroCliente1.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        CadastroCliente1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CadastroCliente1.setIconTextGap(15);
        CadastroCliente1.setRound(20);
        CadastroCliente1.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        CadastroCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastroCliente1ActionPerformed(evt);
            }
        });

        CadastroCliente.setBackground(new java.awt.Color(255, 255, 255));
        CadastroCliente.setForeground(new java.awt.Color(255, 255, 255));
        CadastroCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clientes(20).png"))); // NOI18N
        CadastroCliente.setText("Cadastrar Funcionarios");
        CadastroCliente.setFont(new java.awt.Font("Segoe UI Semilight", 3, 20)); // NOI18N
        CadastroCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CadastroCliente.setIconTextGap(15);
        CadastroCliente.setRound(20);
        CadastroCliente.setStyle(components.JCustomButton.ButtonStyle.DEFAULT);
        CadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastroClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CadastroCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CadastroProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0))
                            .addComponent(CadastroVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ExibirCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ExibirProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ExibirVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(ButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CadastroCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CadastroProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CadastroVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ExibirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ExibirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ExibirVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(ButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        paneltelas.setBackground(new java.awt.Color(204, 204, 204));
        paneltelas.setLayout(new java.awt.CardLayout());

        CadastroUsuario.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("CEP:");

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Nome:");

        jLabel23.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("CPF:");

        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Cargo:");

        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Logradouro:");

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Numero:");

        jLabel28.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Complemento:");

        tfFCep.setBackground(new java.awt.Color(255, 255, 255));
        tfFCep.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFCep.setForeground(new java.awt.Color(0, 0, 0));

        tfFNome.setBackground(new java.awt.Color(255, 255, 255));
        tfFNome.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFNome.setForeground(new java.awt.Color(0, 0, 0));

        cbFCargos.setBackground(new java.awt.Color(255, 255, 255));
        cbFCargos.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        cbFCargos.setForeground(new java.awt.Color(0, 0, 0));

        tfFCpf.setBackground(new java.awt.Color(255, 255, 255));
        tfFCpf.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFCpf.setForeground(new java.awt.Color(0, 0, 0));

        tfFNum.setBackground(new java.awt.Color(255, 255, 255));
        tfFNum.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFNum.setForeground(new java.awt.Color(0, 0, 0));

        tfFComplemento.setBackground(new java.awt.Color(255, 255, 255));
        tfFComplemento.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFComplemento.setForeground(new java.awt.Color(0, 0, 0));

        tfFLogradouro.setBackground(new java.awt.Color(255, 255, 255));
        tfFLogradouro.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFLogradouro.setForeground(new java.awt.Color(0, 0, 0));

        tfFTelefone.setBackground(new java.awt.Color(255, 255, 255));
        tfFTelefone.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFTelefone.setForeground(new java.awt.Color(0, 0, 0));

        jLabel30.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Senha:");

        jLabel31.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Login:");

        tfFSenha.setBackground(new java.awt.Color(255, 255, 255));
        tfFSenha.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFSenha.setForeground(new java.awt.Color(0, 0, 0));

        jLabel32.setFont(new java.awt.Font("Segoe UI Semibold", 1, 35)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Tel:");

        tfFLogin.setBackground(new java.awt.Color(255, 255, 255));
        tfFLogin.setFont(new java.awt.Font("Segoe UI Semibold", 1, 30)); // NOI18N
        tfFLogin.setForeground(new java.awt.Color(0, 0, 0));

        buttonLimpar1.setForeground(new java.awt.Color(255, 255, 255));
        buttonLimpar1.setText("Limpar");
        buttonLimpar1.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonLimpar1.setRound(15);
        buttonLimpar1.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonLimpar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimpar1ActionPerformed(evt);
            }
        });

        buttonCadastrar1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCadastrar1.setText("Cadastrar");
        buttonCadastrar1.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonCadastrar1.setRound(15);
        buttonCadastrar1.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonCadastrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastrar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(tfFLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel31))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfFNome, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfFCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfFCep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(121, 121, 121)
                                                .addComponent(tfFComplemento))
                                            .addComponent(tfFLogin))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel26)
                                                    .addComponent(jLabel24))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbFCargos, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(tfFNum, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(jLabel30)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(tfFSenha))
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(jLabel32)
                                                        .addGap(60, 60, 60)
                                                        .addComponent(tfFTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(buttonLimpar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonCadastrar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addComponent(jLabel25)
                            .addComponent(jLabel28))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(tfFNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(cbFCargos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tfFCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(tfFNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(tfFLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(tfFComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30)
                    .addComponent(tfFSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCadastrar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLimpar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout CadastroUsuarioLayout = new javax.swing.GroupLayout(CadastroUsuario);
        CadastroUsuario.setLayout(CadastroUsuarioLayout);
        CadastroUsuarioLayout.setHorizontalGroup(
            CadastroUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CadastroUsuarioLayout.setVerticalGroup(
            CadastroUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        paneltelas.add(CadastroUsuario, "CadastroFuncionario");

        cadastroCliente.setBackground(new java.awt.Color(255, 255, 255));

        labelNomeCliente.setFont(new java.awt.Font("Georgia", 3, 38)); // NOI18N
        labelNomeCliente.setForeground(new java.awt.Color(0, 0, 0));
        labelNomeCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomeCliente.setText("Nome:");

        tfNome.setBackground(new java.awt.Color(217, 217, 217));
        tfNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeActionPerformed(evt);
            }
        });

        labelSexo.setFont(new java.awt.Font("Georgia", 3, 38)); // NOI18N
        labelSexo.setForeground(new java.awt.Color(0, 0, 0));
        labelSexo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSexo.setText("Sexo:");

        labelcpff.setFont(new java.awt.Font("Georgia", 3, 38)); // NOI18N
        labelcpff.setForeground(new java.awt.Color(0, 0, 0));
        labelcpff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelcpff.setText("CPF:");

        tfcadastroClienteCpf.setBackground(new java.awt.Color(217, 217, 217));
        tfcadastroClienteCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfcadastroClienteCpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfcadastroClienteCpf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfcadastroClienteCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcadastroClienteCpfActionPerformed(evt);
            }
        });

        labelTelefone.setFont(new java.awt.Font("Georgia", 3, 38)); // NOI18N
        labelTelefone.setForeground(new java.awt.Color(0, 0, 0));
        labelTelefone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTelefone.setText("Telefone:");

        tfTelefone.setBackground(new java.awt.Color(217, 217, 217));
        tfTelefone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfTelefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTelefone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTelefoneActionPerformed(evt);
            }
        });

        buttonCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        buttonCadastrar.setText("Cadastrar");
        buttonCadastrar.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonCadastrar.setRound(15);
        buttonCadastrar.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastrarActionPerformed(evt);
            }
        });

        buttonLimpar.setForeground(new java.awt.Color(255, 255, 255));
        buttonLimpar.setText("Limpar");
        buttonLimpar.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonLimpar.setRound(15);
        buttonLimpar.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparActionPerformed(evt);
            }
        });

        cbxSexoClie.setBackground(new java.awt.Color(217, 217, 217));
        cbxSexoClie.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        cbxSexoClie.setForeground(new java.awt.Color(0, 0, 0));
        cbxSexoClie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Masculino", "Feminino", "Outro" }));
        cbxSexoClie.setBorder(null);
        cbxSexoClie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSexoClieActionPerformed(evt);
            }
        });

        labelEmail1.setFont(new java.awt.Font("Georgia", 3, 38)); // NOI18N
        labelEmail1.setForeground(new java.awt.Color(0, 0, 0));
        labelEmail1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmail1.setText("Email:");

        tfEmail1.setBackground(new java.awt.Color(217, 217, 217));
        tfEmail1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfEmail1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfEmail1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfEmail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEmail1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cadastroClienteLayout = new javax.swing.GroupLayout(cadastroCliente);
        cadastroCliente.setLayout(cadastroClienteLayout);
        cadastroClienteLayout.setHorizontalGroup(
            cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroClienteLayout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cadastroClienteLayout.createSequentialGroup()
                        .addComponent(buttonLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroClienteLayout.createSequentialGroup()
                            .addComponent(labelcpff)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfcadastroClienteCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(cadastroClienteLayout.createSequentialGroup()
                            .addComponent(labelTelefone)
                            .addGap(62, 62, 62)
                            .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(cadastroClienteLayout.createSequentialGroup()
                            .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(cadastroClienteLayout.createSequentialGroup()
                                    .addComponent(labelNomeCliente)
                                    .addGap(113, 113, 113))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroClienteLayout.createSequentialGroup()
                                    .addComponent(labelSexo)
                                    .addGap(136, 136, 136)))
                            .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxSexoClie, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(cadastroClienteLayout.createSequentialGroup()
                            .addComponent(labelEmail1)
                            .addGap(111, 111, 111)
                            .addComponent(tfEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(240, 240, 240))
        );
        cadastroClienteLayout.setVerticalGroup(
            cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroClienteLayout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNome)
                    .addComponent(labelNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSexoClie, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfcadastroClienteCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelcpff, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(cadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110))
        );

        paneltelas.add(cadastroCliente, "cadastroCliente");

        cadastroProduto.setBackground(new java.awt.Color(255, 255, 255));

        labelNomeProd.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelNomeProd.setForeground(new java.awt.Color(0, 0, 0));
        labelNomeProd.setText("Nome:");

        tfNomeProd.setBackground(new java.awt.Color(217, 217, 217));
        tfNomeProd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfNomeProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfNomeProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeProdActionPerformed(evt);
            }
        });

        labelPreco.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelPreco.setForeground(new java.awt.Color(0, 0, 0));
        labelPreco.setText("Preço:");

        tfQtd.setBackground(new java.awt.Color(217, 217, 217));
        tfQtd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfQtd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfQtdActionPerformed(evt);
            }
        });

        labelQtd.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelQtd.setForeground(new java.awt.Color(0, 0, 0));
        labelQtd.setText("Quantidade:");

        labelCodigo.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelCodigo.setForeground(new java.awt.Color(0, 0, 0));
        labelCodigo.setText("Codigo:");

        tfPreco.setBackground(new java.awt.Color(217, 217, 217));
        tfPreco.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfPreco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfPreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPrecoActionPerformed(evt);
            }
        });

        labelCategoriaProd.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelCategoriaProd.setForeground(new java.awt.Color(0, 0, 0));
        labelCategoriaProd.setText("Categoria:");

        tfCodigo.setBackground(new java.awt.Color(217, 217, 217));
        tfCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodigoActionPerformed(evt);
            }
        });

        labelDescricao.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelDescricao.setForeground(new java.awt.Color(0, 0, 0));
        labelDescricao.setText("Descrição:");

        tfDescricao.setBackground(new java.awt.Color(217, 217, 217));
        tfDescricao.setColumns(20);
        tfDescricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfDescricao.setRows(5);
        tfDescricao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        jScrollPane1.setViewportView(tfDescricao);

        buttonLimparProd.setForeground(new java.awt.Color(255, 255, 255));
        buttonLimparProd.setText("Limpar");
        buttonLimparProd.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonLimparProd.setRound(15);
        buttonLimparProd.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonLimparProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimparProdActionPerformed(evt);
            }
        });

        buttonCadastrarProd.setForeground(new java.awt.Color(255, 255, 255));
        buttonCadastrarProd.setText("Cadastrar");
        buttonCadastrarProd.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonCadastrarProd.setRound(15);
        buttonCadastrarProd.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonCadastrarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastrarProdActionPerformed(evt);
            }
        });

        cbxCategoria.setBackground(new java.awt.Color(217, 217, 217));
        cbxCategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout cadastroProdutoLayout = new javax.swing.GroupLayout(cadastroProduto);
        cadastroProduto.setLayout(cadastroProdutoLayout);
        cadastroProdutoLayout.setHorizontalGroup(
            cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroProdutoLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelQtd)
                    .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelPreco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelDescricao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfNomeProd)
                        .addGroup(cadastroProdutoLayout.createSequentialGroup()
                            .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(73, 73, 73)
                            .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(cadastroProdutoLayout.createSequentialGroup()
                                    .addComponent(labelCodigo)
                                    .addGap(65, 65, 65)
                                    .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(cadastroProdutoLayout.createSequentialGroup()
                                    .addComponent(labelCategoriaProd)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbxCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroProdutoLayout.createSequentialGroup()
                            .addComponent(buttonLimparProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(buttonCadastrarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        cadastroProdutoLayout.setVerticalGroup(
            cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroProdutoLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNomeProd)
                    .addComponent(labelNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cadastroProdutoLayout.createSequentialGroup()
                        .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPreco))
                        .addGap(18, 18, 18)
                        .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(cadastroProdutoLayout.createSequentialGroup()
                        .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelCategoriaProd, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(cbxCategoria))))
                .addGap(61, 61, 61)
                .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDescricao)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(cadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCadastrarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLimparProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        paneltelas.add(cadastroProduto, "cadastroProduto");

        cadastroVendas.setBackground(new java.awt.Color(255, 255, 255));

        tfProdutoVendas.setBackground(new java.awt.Color(217, 217, 217));
        tfProdutoVendas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfProdutoVendas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfProdutoVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfProdutoVendasActionPerformed(evt);
            }
        });

        labelProduto.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelProduto.setForeground(new java.awt.Color(0, 0, 0));
        labelProduto.setText("Produto:");

        labelCodVendas.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelCodVendas.setForeground(new java.awt.Color(0, 0, 0));
        labelCodVendas.setText("Codigo:");

        tfCodVendas.setBackground(new java.awt.Color(217, 217, 217));
        tfCodVendas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfCodVendas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfCodVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodVendasActionPerformed(evt);
            }
        });

        labelData.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelData.setForeground(new java.awt.Color(0, 0, 0));
        labelData.setText("Data:");

        tfData.setBackground(new java.awt.Color(217, 217, 217));
        tfData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDataActionPerformed(evt);
            }
        });

        labelPrecoVendas.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelPrecoVendas.setForeground(new java.awt.Color(0, 0, 0));
        labelPrecoVendas.setText("Preço:");

        tfPrecoVendas.setBackground(new java.awt.Color(217, 217, 217));
        tfPrecoVendas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfPrecoVendas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfPrecoVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPrecoVendasActionPerformed(evt);
            }
        });

        labelQtdVendas.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelQtdVendas.setForeground(new java.awt.Color(0, 0, 0));
        labelQtdVendas.setText("Qtd:");

        tfQtdVendas.setBackground(new java.awt.Color(217, 217, 217));
        tfQtdVendas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfQtdVendas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfQtdVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfQtdVendasActionPerformed(evt);
            }
        });

        TabelaCadVendas.setBackground(new java.awt.Color(217, 217, 217));
        TabelaCadVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Produto", "Qtd", "Preço", "SubTotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TabelaCadVendas);
        if (TabelaCadVendas.getColumnModel().getColumnCount() > 0) {
            TabelaCadVendas.getColumnModel().getColumn(0).setResizable(false);
            TabelaCadVendas.getColumnModel().getColumn(0).setPreferredWidth(70);
            TabelaCadVendas.getColumnModel().getColumn(1).setResizable(false);
            TabelaCadVendas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TabelaCadVendas.getColumnModel().getColumn(2).setResizable(false);
            TabelaCadVendas.getColumnModel().getColumn(2).setPreferredWidth(40);
            TabelaCadVendas.getColumnModel().getColumn(3).setResizable(false);
            TabelaCadVendas.getColumnModel().getColumn(4).setResizable(false);
        }

        buttonAdicVendas.setForeground(new java.awt.Color(255, 255, 255));
        buttonAdicVendas.setText("Adicionar");
        buttonAdicVendas.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonAdicVendas.setRound(15);
        buttonAdicVendas.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonAdicVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdicVendasActionPerformed(evt);
            }
        });

        buttonCancVendas.setForeground(new java.awt.Color(255, 255, 255));
        buttonCancVendas.setText("Pagar");
        buttonCancVendas.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonCancVendas.setRound(15);
        buttonCancVendas.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonCancVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancVendasActionPerformed(evt);
            }
        });

        buttonPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        buttonPesquisar.setText("Pesquisar");
        buttonPesquisar.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonPesquisar.setRound(15);
        buttonPesquisar.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarActionPerformed(evt);
            }
        });

        labelProduto1.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelProduto1.setForeground(new java.awt.Color(0, 0, 0));
        labelProduto1.setText("CPF:");

        tfPesquisaCpf.setBackground(new java.awt.Color(217, 217, 217));
        tfPesquisaCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfPesquisaCpf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfPesquisaCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPesquisaCpfActionPerformed(evt);
            }
        });

        buttonPesquisarCPF.setForeground(new java.awt.Color(255, 255, 255));
        buttonPesquisarCPF.setText("Pesquisar");
        buttonPesquisarCPF.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonPesquisarCPF.setRound(15);
        buttonPesquisarCPF.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonPesquisarCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarCPFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cadastroVendasLayout = new javax.swing.GroupLayout(cadastroVendas);
        cadastroVendas.setLayout(cadastroVendasLayout);
        cadastroVendasLayout.setHorizontalGroup(
            cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroVendasLayout.createSequentialGroup()
                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cadastroVendasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cadastroVendasLayout.createSequentialGroup()
                                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelCodVendas)
                                    .addComponent(labelPrecoVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelData)
                                    .addComponent(labelProduto1))
                                .addGap(26, 26, 26)
                                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(cadastroVendasLayout.createSequentialGroup()
                                        .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tfCodVendas)
                                                .addGroup(cadastroVendasLayout.createSequentialGroup()
                                                    .addComponent(tfPrecoVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                                                    .addComponent(labelQtdVendas)
                                                    .addGap(34, 34, 34)
                                                    .addComponent(tfQtdVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(tfPesquisaCpf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(34, 34, 34)
                                        .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(buttonAdicVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buttonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buttonPesquisarCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(cadastroVendasLayout.createSequentialGroup()
                                .addComponent(labelProduto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfProdutoVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroVendasLayout.createSequentialGroup()
                        .addContainerGap(207, Short.MAX_VALUE)
                        .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCancVendas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(206, 206, 206))
        );
        cadastroVendasLayout.setVerticalGroup(
            cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroVendasLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelProduto1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfPesquisaCpf)
                    .addComponent(buttonPesquisarCPF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfProdutoVendas)
                    .addComponent(labelProduto))
                .addGap(18, 18, 18)
                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelData, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfCodVendas)
                    .addComponent(labelCodVendas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPesquisar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tfPrecoVendas)
                    .addComponent(labelQtdVendas)
                    .addComponent(labelPrecoVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(cadastroVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfQtdVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonAdicVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonCancVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        paneltelas.add(cadastroVendas, "cadastroVendas");

        exibirClientes.setBackground(new java.awt.Color(255, 255, 255));

        labelExibCliente.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelExibCliente.setForeground(new java.awt.Color(0, 0, 0));
        labelExibCliente.setText("CPF:");

        tfExibCliente.setBackground(new java.awt.Color(217, 217, 217));
        tfExibCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfExibCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfExibCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfExibClienteActionPerformed(evt);
            }
        });

        tableExibProd.setBackground(new java.awt.Color(217, 217, 217));
        tabelaExibCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "Sexo", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelaExibCliente);
        if (tabelaExibCliente.getColumnModel().getColumnCount() > 0) {
            tabelaExibCliente.getColumnModel().getColumn(0).setMinWidth(70);
            tabelaExibCliente.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabelaExibCliente.getColumnModel().getColumn(0).setMaxWidth(90);
            tabelaExibCliente.getColumnModel().getColumn(3).setPreferredWidth(20);
        }
        DefaultTableCellRenderer centralizarTabela = new DefaultTableCellRenderer();
        centralizarTabela.setHorizontalAlignment(SwingConstants.CENTER);

        tabelaExibCliente.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < tabelaExibCliente.getColumnCount(); i++) {
            tabelaExibCliente.getColumnModel().getColumn(i).setCellRenderer(centralizarTabela);
        }

        TableCellRenderer baseRender = tabelaExibCliente.getTableHeader().getDefaultRenderer();
        tabelaExibCliente.getTableHeader().setDefaultRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JComponent comp = (JComponent) baseRender.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
            comp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            return comp;
        });
        tabelaExibCliente.setIntercellSpacing(new Dimension(0, 0));
        tabelaExibCliente.setForeground(new java.awt.Color(0, 0, 0));
        tabelaExibCliente.getTableHeader().setBackground(new Color(108,0,0));
        tabelaExibCliente.getTableHeader().setForeground(new Color(255, 255,255));
        tabelaExibCliente.getTableHeader().setSize(50, 50);
        tabelaExibCliente.setShowVerticalLines(false);
        tabelaExibCliente.getTableHeader().setFont( new Font("Segoe UI Semibold",Font.BOLD, 18));

        buttonExibEdit.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibEdit.setText("Editar");
        buttonExibEdit.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibEdit.setRound(15);
        buttonExibEdit.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibEditActionPerformed(evt);
            }
        });

        buttonExibExcluir.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibExcluir.setText("Excluir");
        buttonExibExcluir.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibExcluir.setRound(15);
        buttonExibExcluir.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibExcluirActionPerformed(evt);
            }
        });

        buttonExibPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibPesquisar.setText("Pesquisar");
        buttonExibPesquisar.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibPesquisar.setRound(15);
        buttonExibPesquisar.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exibirClientesLayout = new javax.swing.GroupLayout(exibirClientes);
        exibirClientes.setLayout(exibirClientesLayout);
        exibirClientesLayout.setHorizontalGroup(
            exibirClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exibirClientesLayout.createSequentialGroup()
                .addContainerGap(130, Short.MAX_VALUE)
                .addGroup(exibirClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(exibirClientesLayout.createSequentialGroup()
                        .addComponent(buttonExibEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonExibExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(exibirClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(exibirClientesLayout.createSequentialGroup()
                            .addComponent(labelExibCliente)
                            .addGap(18, 18, 18)
                            .addComponent(tfExibCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(buttonExibPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        exibirClientesLayout.setVerticalGroup(
            exibirClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exibirClientesLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(exibirClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tfExibCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(buttonExibPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(labelExibCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(exibirClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonExibEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExibExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        paneltelas.add(exibirClientes, "exibirClientes");

        exibirProdutos.setBackground(new java.awt.Color(255, 255, 255));

        labelCódigoExibProd.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelCódigoExibProd.setForeground(new java.awt.Color(0, 0, 0));
        labelCódigoExibProd.setText("Código:");

        tfCódigoExibProd.setBackground(new java.awt.Color(217, 217, 217));
        tfCódigoExibProd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfCódigoExibProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfCódigoExibProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCódigoExibProdActionPerformed(evt);
            }
        });

        buttonExibProd.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibProd.setText("Pesquisar");
        buttonExibProd.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibProd.setRound(15);
        buttonExibProd.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibProdActionPerformed(evt);
            }
        });

        tableExibProd.setBackground(new java.awt.Color(217, 217, 217));
        tableExibProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Código", "Produto", "Categoria", "Qtd. Est.", "Preço", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableExibProd);
        if (tableExibProd.getColumnModel().getColumnCount() > 0) {
            tableExibProd.getColumnModel().getColumn(0).setMinWidth(60);
            tableExibProd.getColumnModel().getColumn(0).setPreferredWidth(70);
            tableExibProd.getColumnModel().getColumn(0).setMaxWidth(80);
        }
        tableExibProd.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < tableExibProd.getColumnCount(); i++) {
            tableExibProd.getColumnModel().getColumn(i).setCellRenderer(centralizarTabela);
        }

        TableCellRenderer baseRender5 = tableExibProd.getTableHeader().getDefaultRenderer();
        tableExibProd.getTableHeader().setDefaultRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JComponent comp = (JComponent) baseRender5.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
            comp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            return comp;
        });
        tableExibProd.setIntercellSpacing(new Dimension(0, 0));
        tableExibProd.setForeground(new java.awt.Color(0, 0, 0));
        tableExibProd.getTableHeader().setBackground(new Color(108, 0, 0));
        tableExibProd.getTableHeader().setForeground(new Color(255, 255,255));
        tableExibProd.getTableHeader().setSize(50, 50);
        tableExibProd.setShowVerticalLines(false);
        tableExibProd.getTableHeader().setFont( new Font("Segoe UI Semibold",Font.BOLD, 18));

        buttonExibExcluirProd.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibExcluirProd.setText("Excluir");
        buttonExibExcluirProd.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibExcluirProd.setRound(15);
        buttonExibExcluirProd.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibExcluirProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibExcluirProdActionPerformed(evt);
            }
        });

        buttonExibExcluirProd1.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibExcluirProd1.setText("Editar");
        buttonExibExcluirProd1.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibExcluirProd1.setRound(15);
        buttonExibExcluirProd1.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibExcluirProd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibExcluirProd1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exibirProdutosLayout = new javax.swing.GroupLayout(exibirProdutos);
        exibirProdutos.setLayout(exibirProdutosLayout);
        exibirProdutosLayout.setHorizontalGroup(
            exibirProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exibirProdutosLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(exibirProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(exibirProdutosLayout.createSequentialGroup()
                        .addComponent(buttonExibExcluirProd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonExibExcluirProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(exibirProdutosLayout.createSequentialGroup()
                        .addComponent(labelCódigoExibProd)
                        .addGap(18, 18, 18)
                        .addComponent(tfCódigoExibProd, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(buttonExibProd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(140, 140, 140))
        );
        exibirProdutosLayout.setVerticalGroup(
            exibirProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exibirProdutosLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(exibirProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCódigoExibProd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExibProd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCódigoExibProd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(exibirProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonExibExcluirProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExibExcluirProd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        paneltelas.add(exibirProdutos, "exibirProdutos");

        exibirVendas.setBackground(new java.awt.Color(255, 255, 255));

        labelExibirCliente.setFont(new java.awt.Font("Georgia", 3, 30)); // NOI18N
        labelExibirCliente.setForeground(new java.awt.Color(0, 0, 0));
        labelExibirCliente.setText("Cliente:");

        tfBuscarVendas.setBackground(new java.awt.Color(217, 217, 217));
        tfBuscarVendas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfBuscarVendas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 0, 0), 2));
        tfBuscarVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBuscarVendasActionPerformed(evt);
            }
        });

        buttonExibirPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibirPesquisa.setText("Pesquisar");
        buttonExibirPesquisa.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibirPesquisa.setRound(15);
        buttonExibirPesquisa.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibirPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibirPesquisaActionPerformed(evt);
            }
        });

        tableExibirVendas.setBackground(new java.awt.Color(217, 217, 217));
        tableExibirVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "Cliente", "Data da Venda", "Qtd. Prod", "Total da Venda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tableExibirVendas);
        if (tableExibirVendas.getColumnModel().getColumnCount() > 0) {
            tableExibirVendas.getColumnModel().getColumn(0).setResizable(false);
            tableExibirVendas.getColumnModel().getColumn(1).setResizable(false);
            tableExibirVendas.getColumnModel().getColumn(2).setResizable(false);
            tableExibirVendas.getColumnModel().getColumn(2).setPreferredWidth(120);
            tableExibirVendas.getColumnModel().getColumn(3).setResizable(false);
            tableExibirVendas.getColumnModel().getColumn(3).setPreferredWidth(15);
            tableExibirVendas.getColumnModel().getColumn(4).setResizable(false);
            tableExibirVendas.getColumnModel().getColumn(4).setPreferredWidth(20);
        }
        tableExibirVendas.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < tableExibirVendas.getColumnCount(); i++) {
            tableExibirVendas.getColumnModel().getColumn(i).setCellRenderer(centralizarTabela);
        }

        TableCellRenderer baseRender1 = tableExibirVendas.getTableHeader().getDefaultRenderer();
        tableExibirVendas.getTableHeader().setDefaultRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JComponent comp = (JComponent) baseRender1.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
            comp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            return comp;
        });
        tableExibirVendas.setIntercellSpacing(new Dimension(0, 0));
        tableExibirVendas.setForeground(new java.awt.Color(0, 0, 0));
        tableExibirVendas.getTableHeader().setBackground(new Color(108,0,0));
        tableExibirVendas.getTableHeader().setForeground(new Color(255, 255,255));
        tableExibirVendas.getTableHeader().setSize(50, 50);
        tableExibirVendas.setShowVerticalLines(false);
        tableExibirVendas.getTableHeader().setFont( new Font("Segoe UI Semibold",Font.BOLD, 18));

        buttonExibirClienteExcluir.setForeground(new java.awt.Color(255, 255, 255));
        buttonExibirClienteExcluir.setText("Excluir");
        buttonExibirClienteExcluir.setFont(new java.awt.Font("Segoe UI Semibold", 3, 24)); // NOI18N
        buttonExibirClienteExcluir.setRound(15);
        buttonExibirClienteExcluir.setStyle(components.JCustomButton.ButtonStyle.EXIT);
        buttonExibirClienteExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExibirClienteExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exibirVendasLayout = new javax.swing.GroupLayout(exibirVendas);
        exibirVendas.setLayout(exibirVendasLayout);
        exibirVendasLayout.setHorizontalGroup(
            exibirVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exibirVendasLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(exibirVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonExibirClienteExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(exibirVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(exibirVendasLayout.createSequentialGroup()
                            .addComponent(labelExibirCliente)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tfBuscarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonExibirPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        exibirVendasLayout.setVerticalGroup(
            exibirVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exibirVendasLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(exibirVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelExibirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExibirPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBuscarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(buttonExibirClienteExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        paneltelas.add(exibirVendas, "exibirVendas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(paneltelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneltelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void ExibirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExibirProdutoActionPerformed
        CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "exibirProdutos");
        listaProdutos();
    }//GEN-LAST:event_ExibirProdutoActionPerformed

    private void ExibirVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExibirVendasActionPerformed
        CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "exibirVendas");
        listaVendas();
    }//GEN-LAST:event_ExibirVendasActionPerformed

    private void ExibirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExibirClienteActionPerformed
        CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "exibirClientes");
        listaClientes();
    }//GEN-LAST:event_ExibirClienteActionPerformed

    private void tfNomeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeProdActionPerformed

    private void tfQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfQtdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQtdActionPerformed

    private void tfPrecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPrecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPrecoActionPerformed

    private void tfCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodigoActionPerformed

    private void buttonLimparProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonLimparProdActionPerformed

    private void buttonCadastrarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastrarProdActionPerformed
        cadastroProduto();
    }//GEN-LAST:event_buttonCadastrarProdActionPerformed

    private void tfProdutoVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfProdutoVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfProdutoVendasActionPerformed

    private void tfCodVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodVendasActionPerformed

    private void tfDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDataActionPerformed

    private void tfPrecoVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPrecoVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPrecoVendasActionPerformed

    private void tfQtdVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfQtdVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQtdVendasActionPerformed

    private void buttonAdicVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdicVendasActionPerformed
        adcCarrinho();
    }//GEN-LAST:event_buttonAdicVendasActionPerformed

    private void buttonCancVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancVendasActionPerformed
        cadastroVendas();
        limparTabela();
    }//GEN-LAST:event_buttonCancVendasActionPerformed

    private void tfExibClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfExibClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfExibClienteActionPerformed

    private void buttonExibEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibEditActionPerformed
        editarClientes();
    }//GEN-LAST:event_buttonExibEditActionPerformed

    private void buttonExibExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibExcluirActionPerformed
        excluirClientes();
    }//GEN-LAST:event_buttonExibExcluirActionPerformed

    private void buttonExibPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibPesquisarActionPerformed
        listaClientes();
    }//GEN-LAST:event_buttonExibPesquisarActionPerformed

    private void tfCódigoExibProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCódigoExibProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCódigoExibProdActionPerformed

    private void buttonExibProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonExibProdActionPerformed

    private void buttonExibExcluirProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibExcluirProdActionPerformed
        excluirProdutos();
    }//GEN-LAST:event_buttonExibExcluirProdActionPerformed

    private void tfBuscarVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBuscarVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBuscarVendasActionPerformed

    private void buttonExibirPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibirPesquisaActionPerformed
        listaVendas();
    }//GEN-LAST:event_buttonExibirPesquisaActionPerformed

    private void buttonExibirClienteExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibirClienteExcluirActionPerformed
        excluirVendas();
    }//GEN-LAST:event_buttonExibirClienteExcluirActionPerformed

    private void tfNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeActionPerformed

    private void tfcadastroClienteCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcadastroClienteCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcadastroClienteCpfActionPerformed

    private void tfTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTelefoneActionPerformed

    private void ButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSairActionPerformed
        new TelaLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonSairActionPerformed

    private void buttonCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastrarActionPerformed
        cadastroCliente();
    }//GEN-LAST:event_buttonCadastrarActionPerformed

    private void buttonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonLimparActionPerformed

    private void CadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastroClienteActionPerformed
       CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "CadastroFuncionario");  
    }//GEN-LAST:event_CadastroClienteActionPerformed

    private void CadastroProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastroProdutoActionPerformed
        CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "cadastroProduto");
    }//GEN-LAST:event_CadastroProdutoActionPerformed

    private void CadastroVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastroVendasActionPerformed
        CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "cadastroVendas");
        tfData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
    }//GEN-LAST:event_CadastroVendasActionPerformed

    private void tfEmail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmail1ActionPerformed

    private void cbxSexoClieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSexoClieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSexoClieActionPerformed

    private void buttonExibExcluirProd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExibExcluirProd1ActionPerformed
        editarProdutos();
    }//GEN-LAST:event_buttonExibExcluirProd1ActionPerformed

    private void CadastroCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastroCliente1ActionPerformed
       CardLayout cl = (CardLayout) paneltelas.getLayout();
        cl.show(paneltelas, "cadastroCliente");
    }//GEN-LAST:event_CadastroCliente1ActionPerformed

    private void buttonLimpar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimpar1ActionPerformed
        limparCampos(tfFNome, tfFCpf, tfFCep, tfFLogradouro,
                    tfFNum, tfFComplemento, tfFTelefone, tfFLogin, tfFSenha);
    }//GEN-LAST:event_buttonLimpar1ActionPerformed

    private void buttonCadastrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastrar1ActionPerformed
       cadastroFuncionario();
       
    }//GEN-LAST:event_buttonCadastrar1ActionPerformed

    private void buttonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarActionPerformed
        listagemProdutos();
    }//GEN-LAST:event_buttonPesquisarActionPerformed

    private void tfPesquisaCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPesquisaCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPesquisaCpfActionPerformed

    private void buttonPesquisarCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarCPFActionPerformed
        listarCPF();
    }//GEN-LAST:event_buttonPesquisarCPFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.JCustomButton ButtonSair;
    private components.JCustomButton CadastroCliente;
    private components.JCustomButton CadastroCliente1;
    private components.JCustomButton CadastroProduto;
    private javax.swing.JPanel CadastroUsuario;
    private components.JCustomButton CadastroVendas;
    private components.JCustomButton ExibirCliente;
    private components.JCustomButton ExibirProduto;
    private components.JCustomButton ExibirVendas;
    private javax.swing.JTable TabelaCadVendas;
    private components.JCustomButton buttonAdicVendas;
    private components.JCustomButton buttonCadastrar;
    private components.JCustomButton buttonCadastrar1;
    private components.JCustomButton buttonCadastrarProd;
    private components.JCustomButton buttonCancVendas;
    private components.JCustomButton buttonExibEdit;
    private components.JCustomButton buttonExibExcluir;
    private components.JCustomButton buttonExibExcluirProd;
    private components.JCustomButton buttonExibExcluirProd1;
    private components.JCustomButton buttonExibPesquisar;
    private components.JCustomButton buttonExibProd;
    private components.JCustomButton buttonExibirClienteExcluir;
    private components.JCustomButton buttonExibirPesquisa;
    private components.JCustomButton buttonLimpar;
    private components.JCustomButton buttonLimpar1;
    private components.JCustomButton buttonLimparProd;
    private components.JCustomButton buttonPesquisar;
    private components.JCustomButton buttonPesquisarCPF;
    private javax.swing.JPanel cadastroCliente;
    private javax.swing.JPanel cadastroProduto;
    private javax.swing.JPanel cadastroVendas;
    private javax.swing.JComboBox<String> cbFCargos;
    private javax.swing.JComboBox<String> cbxCategoria;
    private javax.swing.JComboBox<String> cbxSexoClie;
    private javax.swing.JPanel exibirClientes;
    private javax.swing.JPanel exibirProdutos;
    private javax.swing.JPanel exibirVendas;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelCategoriaProd;
    private javax.swing.JLabel labelCodVendas;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JLabel labelCódigoExibProd;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelDescricao;
    private javax.swing.JLabel labelEmail1;
    private javax.swing.JLabel labelExibCliente;
    private javax.swing.JLabel labelExibirCliente;
    private javax.swing.JLabel labelNomeCliente;
    private javax.swing.JLabel labelNomeProd;
    private javax.swing.JLabel labelPreco;
    private javax.swing.JLabel labelPrecoVendas;
    private javax.swing.JLabel labelProduto;
    private javax.swing.JLabel labelProduto1;
    private javax.swing.JLabel labelQtd;
    private javax.swing.JLabel labelQtdVendas;
    private javax.swing.JLabel labelSexo;
    private javax.swing.JLabel labelTelefone;
    private javax.swing.JLabel labelcpff;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel paneltelas;
    private javax.swing.JPanel sidebar;
    private javax.swing.JTable tabelaExibCliente;
    private javax.swing.JTable tableExibProd;
    private javax.swing.JTable tableExibirVendas;
    private javax.swing.JTextField tfBuscarVendas;
    private javax.swing.JTextField tfCodVendas;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCódigoExibProd;
    private javax.swing.JTextField tfData;
    private javax.swing.JTextArea tfDescricao;
    private javax.swing.JTextField tfEmail1;
    private javax.swing.JTextField tfExibCliente;
    private javax.swing.JTextField tfFCep;
    private javax.swing.JTextField tfFComplemento;
    private javax.swing.JTextField tfFCpf;
    private javax.swing.JTextField tfFLogin;
    private javax.swing.JTextField tfFLogradouro;
    private javax.swing.JTextField tfFNome;
    private javax.swing.JTextField tfFNum;
    private javax.swing.JTextField tfFSenha;
    private javax.swing.JTextField tfFTelefone;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfNomeProd;
    private javax.swing.JTextField tfPesquisaCpf;
    private javax.swing.JTextField tfPreco;
    private javax.swing.JTextField tfPrecoVendas;
    private javax.swing.JTextField tfProdutoVendas;
    private javax.swing.JTextField tfQtd;
    private javax.swing.JTextField tfQtdVendas;
    private javax.swing.JTextField tfTelefone;
    private javax.swing.JTextField tfcadastroClienteCpf;
    // End of variables declaration//GEN-END:variables

}
