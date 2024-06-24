package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Livros")

public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String nomeAutor;
    private String idioma;
    private Integer downloads;

    @ManyToOne
    private Autor autor;


    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = String.join(",", dadosLivro.idioma());
        this.downloads = dadosLivro.downloads();
        this.nomeAutor = dadosLivro.autores().get(0).nome();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro: " + titulo +
                ", autor = " + nomeAutor +
                ", idioma = " + idioma +
                ", downloads = " + downloads + "\n";
    }

}
