package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento >= :ano AND :ano >= a.anoNascimento")
    List<Autor> autoresVivosEm(String ano);
}
