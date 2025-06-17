package br.com.alura.Literatura.repository;

import br.com.alura.Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano")
    List<Autor> findAutoresVivosNoAno(@Param("ano")Integer ano);
    Optional<Autor>findByNomeAutor(String nomeAutor);
}
