package controllers;

import DTO.ProdutoDTO;
import classes.Produto;
import services.ProdutoService;
import validacoes.Alerta;
import java.util.List;

public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoService();
    }
    
    
    public void cadastrarProduto(ProdutoDTO produtoDTO) {
        try {
            produtoService.cadastrarProduto(produtoDTO);
            System.out.println("Produto cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro no cadastro");
        } catch (Exception e) {
            System.out.println("Falha ao cadastrar o produto no sistema.");
        }
    }

    public void editarProduto(ProdutoDTO produtoDTO, Long id) {
        try {
            produtoService.editarProduto(produtoDTO, id);
            System.out.println("Produto atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na atualização");
        } catch (Exception e) {
            System.out.println("Falha ao atualizar o produto no sistema.");
        }
    }

    public List<Produto> listarProdutos(String cod) {
        try {
            return produtoService.listarProdutos(cod);
        } catch (Exception e) {
            System.out.println("Falha ao listar os produtos cadastrados.");
            return null;
        }
    }

    public Produto buscarPorId(Long id) {
        try {
            return produtoService.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na busca");
        } catch (Exception e) {
            System.out.println("Falha ao buscar o produto pelo ID.");
        }
        return null;
    }

    public void excluirProduto(String id) {
        try {
            produtoService.excluirProdutos(id);
            System.out.println("roduto removido com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na exclusão");
        } catch (Exception e) {
            System.out.println("Falha ao excluir o produto do sistema.");
        }
    }
}

