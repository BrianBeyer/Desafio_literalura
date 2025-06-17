package br.com.alura.Literatura.model;

public enum Idioma {
    ESPANHOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt");

    private final String sigla;

    Idioma(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }
    public static Idioma fromCodigo(String sigla) {
        for (Idioma idioma : values()) {
            if (idioma.sigla.equals(sigla)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma "+sigla+" inválido!");
    }


}
