package br.com.alura.LiterAlura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
