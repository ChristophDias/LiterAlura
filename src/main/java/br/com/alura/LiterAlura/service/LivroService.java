package br.com.alura.LiterAlura.service;

import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.Livro;
import br.com.alura.LiterAlura.repository.AutorRepository;
import br.com.alura.LiterAlura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class LivroService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;


    public void saveLivro(Livro livro) {
        Livro livroJaSalvo = livroRepository.getByTitulo(livro.getTitulo());

        if(livroJaSalvo == null) {
            Autor autor = livro.getAutor();

            Autor autorJaSalvo = autorRepository.findByNomeContainingIgnoreCase(autor.getNome());
            if(autorJaSalvo != null) {
                livro.setAutor(autorJaSalvo);
                autorJaSalvo.setLivros(livro);
            } else {
                Autor salvaAutor = autorRepository.save(autor);
                livro.setAutor(salvaAutor);
                salvaAutor.setLivros(livro);
            }

            livroRepository.save(livro);
            System.out.println("Livro Salvro com sucesso!!");
        } else {
            System.out.println("Livro já cadastrado");
        }

    }

    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    public List<Autor> getAllAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> getAutoresVivosEm(String ano) {
        return autorRepository.autoresVivosEm(ano);
    }


    public List<Livro> getLivrosPorIdioma() {
        Scanner scanner = new Scanner(System.in);
        List<String> idiomas = livroRepository.idiomas();
        System.out.print("\nIdiomas disponíveis: { ");
        if(idiomas.size() == 1) {
            idiomas.forEach(System.out::print);
        } else {
            idiomas.forEach(l -> System.out.print(l + ", "));
        }
        System.out.println(" }");
        System.out.println("\nEscoma o idioma para pesquisar: ");
        String idioma = scanner.nextLine();
        if(!idiomas.contains(idioma)) {
            System.out.println("Nenhum livros registrado nesse idioma");
        }
        return livroRepository.livrosPorIdioma(idioma);
    }

}
