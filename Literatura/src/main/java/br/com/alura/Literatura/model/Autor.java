package br.com.alura.Literatura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nomeAutor;
    private Integer anoNascimento;
    private Integer anoMorte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dadosAutor) {
        this.nomeAutor = dadosAutor.nomeAutor();
        this.anoMorte = dadosAutor.anoMorte();
        this.anoNascimento = dadosAutor.anoNascimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        StringBuilder livrosTitulos = new StringBuilder();
        for (Livro livro : livros) {
            if (livrosTitulos.length() > 0) livrosTitulos.append(", ");
            livrosTitulos.append(livro.getTitulo());
        }

        return String.format("""
            *** AUTOR ***
            Autor: %s
            Ano de nascimento: %s
            Ano de falecimento: %s
            Livros: [%s]
            """,
                nomeAutor,
                anoNascimento != null ? anoNascimento : "Sem dados",
                anoMorte != null ? anoMorte : "Sem dados",
                livrosTitulos.toString()
        );
    }
}
