package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro getByTitulo(String titulo);

    @Query("SELECT DISTINCT l.idioma FROM Livro l ORDER BY l.idioma")
    List<String> idiomas();

    @Query("SELECT l FROM Livro l WHERE idioma = :idioma")
    List<Livro> livrosPorIdioma(String idioma);
}
