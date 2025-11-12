package controllers;

import classes.Produto;
import services.ProdutoService;
import validacoes.Alerta;
import java.util.List;

public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoService();
    }
    
    
    public void cadastrarProduto(Produto produto) {
        try {
            produtoService.cadastrarProduto(produto);
            Alerta.Erro("Cadastro concluído!", "Produto cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro no cadastro", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao cadastrar o produto no sistema.");
        }
    }

    public void editarProduto(Produto produto) {
        try {
            produtoService.editarProduto(produto);
            Alerta.Erro("Atualização concluída!", "Produto atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na atualização", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao atualizar o produto no sistema.");
        }
    }

    public List<Produto> listarProdutos(String cod) {
        try {
            return produtoService.listarProdutos(cod);
        } catch (Exception e) {
            Alerta.Erro("Erro na listagem", "Falha ao listar os produtos cadastrados.");
            return null;
        }
    }

    public Produto buscarPorId(Long id) {
        try {
            return produtoService.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na busca", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao buscar o produto pelo ID.");
        }
        return null;
    }

    public void excluirProduto(String id) {
        try {
            produtoService.excluirProdutos(id);
            Alerta.Erro("Exclusão concluída!", "Produto removido com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na exclusão", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao excluir o produto do sistema.");
        }
    }
}

