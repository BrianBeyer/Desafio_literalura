package br.com.alura.Literatura.principal;

import br.com.alura.Literatura.model.*;
import br.com.alura.Literatura.repository.AutorRepository;
import br.com.alura.Literatura.repository.LivroRepository;
import br.com.alura.Literatura.service.ConsumoApi;
import br.com.alura.Literatura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private LivroRepository repositorio;
    private AutorRepository repositorioAutor;

    public Principal(AutorRepository repositorioAutor, LivroRepository repositorio) {
        this.repositorioAutor = repositorioAutor;
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                    Escolha o número da sua opção:
                    
                    1- Buscar livro pelo título
                    2- Listar livros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos em um determinado ano
                    5- Listar livros em um determinado idioma
                    0- Para sair
                    """;
            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine();


                switch (opcao) {
                    case 1:
                        buscarLivro();
                        break;
                    case 2:
                        livrosRegistrados();
                        break;
                    case 3:
                        autoresRegistrados();
                        break;
                    case 4:
                        autoresVivos();
                        break;
                    case 5:
                        livrosIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo do programa...");
                        break;
                    default:
                        System.out.println("Digite um valor válido");
                }
            } catch (Exception e) {
                System.out.println("Digite um número válido!");
                leitura.nextLine();
                opcao = -1;
            }

        }
    }

    private void livrosIdioma() {
        System.out.println("""
                Insira o idioma para buscar:
                es - espanhol
                en - ingês
                fr - francês
                pt - português
                """);
        var idiomaEscolhido = leitura.nextLine().trim().toLowerCase();
       try{
           Idioma idioma = Idioma.fromCodigo(idiomaEscolhido);
           List<Livro> livrosEncontrados = repositorio.findByIdioma(idioma);
           if (livrosEncontrados.isEmpty()) {
               System.out.println("Não existem livros nesse idioma no banco de dados.");
           } else {
               System.out.println("Livros em "+ idioma + " = ");
               livrosEncontrados.forEach(System.out::println);
           }

       }catch (IllegalArgumentException e){
           System.out.print("Digite um idioma válido!");
       }


    }

    private void autoresVivos() {
        System.out.println("Insira o ano para pesquisar: ");
    try {
        var ano = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autoresVivos = repositorioAutor.findAutoresVivosNoAno(ano);
        if(autoresVivos.isEmpty()){
            System.out.println("Nenhum autor vivo no ano "+ ano);
        }else {
            System.out.println("Autores vivos em "+ano);
            autoresVivos.forEach(System.out::println);
        }
    }catch (Exception e){
        System.out.println("Digite um valor valido: \n");
        leitura.nextLine();
    }

    }

    private void autoresRegistrados() {
       List<Autor> autores = repositorioAutor.findAll();
       if(autores.isEmpty()){
           System.out.println("Nenhum autor encontrado!");
       }else{
           autores.forEach(System.out::println);
       }
    }

    private void livrosRegistrados() {
        List<Livro> livros = repositorio.findAll();
        if(livros.isEmpty()){
            System.out.println("Nenhum livro encontrado!");
        }else{
            livros.forEach(System.out::println);
        }

    }

    private void buscarLivro() {
        System.out.println("Digite o nome do livro que deseja buscar: ");
        String nomeLivro = leitura.nextLine();
        try {
            String url = ENDERECO + nomeLivro.replace(" ", "+").toLowerCase().trim();
            String json = consumo.obterDados(url);

            RespostaApi respostaApi = conversor.obterDados(json, RespostaApi.class);

            if (respostaApi.resultados() == null || respostaApi.resultados().isEmpty()) {
                System.out.println("Nenhum livro encontrado!");
                return;
            }

            DadosLivro dadosLivro = respostaApi.resultados().get(0);
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            Optional<Autor> autorExistente = repositorioAutor.findByNomeAutor(dadosAutor.nomeAutor());
            Autor autor = autorExistente.orElseGet(() -> repositorioAutor.save(new Autor(dadosAutor)));

            Livro livro = new Livro(dadosLivro, autor);
            repositorio.save(livro);

            System.out.println("Livro inserido com sucesso!");
            System.out.println(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());

        }
    }
}