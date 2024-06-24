package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.model.*;
import br.com.alura.LiterAlura.service.ConsumoApi;
import br.com.alura.LiterAlura.service.ConverteDados;
import br.com.alura.LiterAlura.service.LivroService;
import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private final LivroService livroService;

    public Principal(LivroService livroService) {
        this.livroService = livroService;
    }

    public void exibeMenu() {

        int opcao = -1;

        while (opcao !=0) {
            System.out.println("""
                    **************************************
                    1 - Busca de livo por título
                    2 - Listagem de todos os  livros
                    3 - Lista de autores
                    4 - Listar autores vivos em determinado ano
                    5 - Livros por idioma
                    0 - Sair
                    """);

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                   listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosEm();
                    break;
                case 5:
                    livrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Finalizando a aplicação");
                    break;
                default:
                    System.out.println("Opção invalida");
                    break;
            }
        }
    }


    private DadosLivro getDadosLivro() {
          System.out.println("Digite o nome do livro");
          var nomeLivro = leitura.nextLine();
          var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
          DadosResult dadosResult = conversor.obterDados(json, DadosResult.class);
          DadosLivro dadosLivro = dadosResult.dadosLivro().get(0);

          return dadosLivro;
      }


    private void buscarLivro() {
        DadosLivro dadosLivro = getDadosLivro();
        Livro livro = new Livro(dadosLivro);
        System.out.println(livro);


        var primeiroAutor = dadosLivro.autores().get(0);
        DadosAutor dadosAutor = new DadosAutor(primeiroAutor.nome(), primeiroAutor.anoNascimento(), primeiroAutor.anoFalecimento());
        Autor autor = new Autor(dadosAutor);
        System.out.println(autor);
        livro.setAutor(autor);
        livroService.saveLivro(livro);
        System.out.println(livro);

    }

    private void listarLivros () {
        var livros = livroService.getAllLivros();
        if (!livros.isEmpty()) {
            livros.forEach(System.out::println);
        } else {
            System.out.println("Nenhum um livro cadastrado");
        }
    }

    private void listarAutores() {
        var autores = livroService.getAllAutores();
        if(!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("Nenhum um autor cadastrado");
        }
    }

    private void listarAutoresVivosEm() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        String ano = leitura.nextLine();

        var autoresVivosEm = livroService.getAutoresVivosEm(ano);
        if(!autoresVivosEm.isEmpty()) {
            autoresVivosEm.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor registrado estava vivo este ano");
        }
    }

    private void livrosPorIdioma() {
        var livrosPorIdioma = livroService.getLivrosPorIdioma();
        livrosPorIdioma.forEach(System.out::println);
    }



}
