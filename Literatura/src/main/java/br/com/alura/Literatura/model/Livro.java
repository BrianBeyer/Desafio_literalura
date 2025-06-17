package br.com.alura.Literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private Integer downloads;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        this.autor = autor;
        this.downloads = dadosLivro.downloads();

        if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()) {
            String idiomaStr = dadosLivro.idiomas().get(0);
            try {
                this.idioma = Idioma.fromCodigo(idiomaStr);
            } catch (IllegalArgumentException e) {
                this.idioma = null;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format("""
            *** LIVRO ***
            Título: %s
            Autor: %s
            Idioma: %s
            Número de downloads: %s
            """,
                titulo,
                autor != null ? autor.getNomeAutor() : "Autor desconhecido",
                idioma != null ? idioma.getSigla() : "Sem dados",
                downloads != null ? downloads : 0
        );
    }
}
