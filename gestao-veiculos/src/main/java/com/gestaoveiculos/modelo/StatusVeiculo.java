package com.gestaoveiculos.modelo;

/**
 * Enum que representa os possíveis status de um veículo no estoque.
 * Aplica o conceito de abstração ao encapsular os estados válidos.
 */
public enum StatusVeiculo {

    DISPONIVEL("Disponível"),
    VENDIDO("Vendido"),
    RESERVADO("Reservado"),
    DESCONTINUADO("Descontinuado"),
    EM_MANUTENCAO("Em Manutenção");

    private final String descricao;

    StatusVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
