package br.com.alura.Literatura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
