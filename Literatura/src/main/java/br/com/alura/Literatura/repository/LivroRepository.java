package br.com.alura.Literatura.repository;

import br.com.alura.Literatura.model.Idioma;
import br.com.alura.Literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIdioma(Idioma idioma);

}
