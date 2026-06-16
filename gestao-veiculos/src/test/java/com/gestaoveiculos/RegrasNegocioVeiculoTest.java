package com.gestaoveiculos;

import com.gestaoveiculos.modelo.Veiculo;
import com.gestaoveiculos.modelo.StatusVeiculo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes das regras de negócio da entidade Veiculo.
 *
 * Aqui a gente testa os métodos de negócio (vender, reservar) sem
 * precisar subir o banco — só a lógica pura da classe.
 */
class RegrasNegocioVeiculoTest {

    private Veiculo novoVeiculo() {
        return new Veiculo("Prata", 2022, new BigDecimal("100000.00"), 30000, null);
    }

    @Test
    void veiculoNovoDeveComecarDisponivel() {
        Veiculo v = novoVeiculo();
        assertEquals(StatusVeiculo.DISPONIVEL, v.getStatus());
        assertTrue(v.isAvailable());
    }

    @Test
    void deveVenderVeiculoDisponivel() {
        Veiculo v = novoVeiculo();
        v.sell();
        assertEquals(StatusVeiculo.VENDIDO, v.getStatus());
        assertFalse(v.isAvailable());
    }

    @Test
    void naoDeveVenderVeiculoJaVendido() {
        Veiculo v = novoVeiculo();
        v.sell();
        // tentar vender de novo deve dar erro
        assertThrows(IllegalStateException.class, v::sell);
    }

    @Test
    void deveReservarVeiculoDisponivel() {
        Veiculo v = novoVeiculo();
        v.reserve();
        assertEquals(StatusVeiculo.RESERVADO, v.getStatus());
    }

    @Test
    void naoDeveReservarVeiculoVendido() {
        Veiculo v = novoVeiculo();
        v.sell();
        assertThrows(IllegalStateException.class, v::reserve);
    }

    @Test
    void podeVenderVeiculoReservado() {
        Veiculo v = novoVeiculo();
        v.reserve();
        v.sell();
        assertEquals(StatusVeiculo.VENDIDO, v.getStatus());
    }
}
