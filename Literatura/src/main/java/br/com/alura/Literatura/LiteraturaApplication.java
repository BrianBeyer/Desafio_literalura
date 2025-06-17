package br.com.alura.Literatura;

import br.com.alura.Literatura.model.DadosAutor;
import br.com.alura.Literatura.model.DadosLivro;
import br.com.alura.Literatura.principal.Principal;
import br.com.alura.Literatura.repository.AutorRepository;
import br.com.alura.Literatura.repository.LivroRepository;
import br.com.alura.Literatura.service.ConsumoApi;
import br.com.alura.Literatura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	@Autowired
	private LivroRepository repositorio;
	@Autowired
	private AutorRepository repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioAutor, repositorio);
		principal.exibeMenu();
	}
}
