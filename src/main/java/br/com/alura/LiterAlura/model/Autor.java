package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dadosAutor) {
        String[] autor = dadosAutor.nome().split(",");
        this.nome = autor[0] + " " + autor[0] ;
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        this.livros.add(livro);
        livro.setAutor(this);
    }

    public List<String> allLivros() {
        return livros.stream().map(Livro::getTitulo).collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return
                "Autor = " + nome +
                ", anoNascimento = " + anoNascimento +
                ", anoFalecimento = " + anoFalecimento;
    }
}
