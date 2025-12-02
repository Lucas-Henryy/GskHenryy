package classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbProduto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    private Long id;

    @Column(name = "nomeProduto")
    private String nome;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "codigo")
    private int codigo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "quantidade")
    private int quantidade;

    @ManyToOne 
    @JoinColumn(name = "categoria_id", unique = true)
    private Categoria categoria;

    public Produto(String nome, Double preco, int codigo, String descricao, int quantidade, Categoria categoria) {
       
        if (getNome()== null || getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        
        if (getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }

        if (getCodigo() <= 0 ) {
            throw new IllegalArgumentException("O código do produto é obrigatório.");
        }
        
        if (getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade do produto deve ser zero ou maior.");
        }

        if (getDescricao() == null || getDescricao().isBlank()) {
            throw new IllegalArgumentException("A descrição do produto é obrigatória.");
        }

        if (getCategoria() == null || getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("A categoria do produto é obrigatória.");
        }

        this.nome = nome;
        this.preco = preco;
        this.codigo = codigo;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


}
