package com.gestaoveiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal do Sistema de Gestão de Estoque de Veículos.
 *
 * Esta aplicação implementa um CRUD completo orientado a objetos,
 * aplicando os pilares da POO:
 * - Encapsulamento: atributos privados com getters/setters
 * - Herança: entidades compartilham comportamentos via BaseEntity
 * - Polimorfismo: repositórios e serviços com contratos comuns
 * - Abstração: interfaces de serviço definindo contratos
 */
@SpringBootApplication
public class GestaoVeiculosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoVeiculosApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Sistema de Gestão de Veículos - ONLINE");
        System.out.println("  API disponível em: http://localhost:8080/api");
        System.out.println("==============================================");
    }
}
