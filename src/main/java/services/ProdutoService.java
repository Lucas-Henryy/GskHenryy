package services;
import classes.Categoria;
import classes.Produto;
import classesDAO.ProdutoDAO;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void cadastrarProduto(Produto produto) {
        validarCamposObrigatorios(produto);
        produtoDAO.cadastrarProduto(produto);
    }

    public void editarProduto(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto inválido para edição.");
        }
        validarCamposObrigatorios(produto);
        produtoDAO.editarProduto(produto);
    }

    public void excluirProdutos(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID do produto é obrigatório para exclusão.");
        }
        produtoDAO.excluirProdutos(id);
    }

   public Produto buscarPorId(Long idProduto) {
    if (idProduto == null || idProduto <= 0) {
        throw new IllegalArgumentException("O ID do produto é obrigatório e deve ser válido para busca.");
    }
    return produtoDAO.buscarPorId(idProduto);
}


    public List<Produto> listarProdutos(String cod) {
        return produtoDAO.listarProdutos(cod);
    }

    public List<Categoria> pegarCategoria() {
        return produtoDAO.pegarCategoria();
    }

    // ✅ Validações baseadas no schema do banco
    private void validarCamposObrigatorios(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }

        if (produto.getCodigo() == null || produto.getCodigo().isBlank()) {
            throw new IllegalArgumentException("O código do produto é obrigatório.");
        }
        
        if (produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade do produto deve ser zero ou maior.");
        }

        if (produto.getDescricao() == null || produto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("A descrição do produto é obrigatória.");
        }

        if (produto.getCategoria() == null || produto.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("A categoria do produto é obrigatória.");
        }
    }
}
