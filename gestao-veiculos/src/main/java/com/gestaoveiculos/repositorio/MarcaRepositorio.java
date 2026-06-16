package com.gestaoveiculos.repositorio;

import com.gestaoveiculos.modelo.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de Marcas — camada de acesso a dados.
 *
 * POO aplicada:
 * - Polimorfismo: estende JpaRepository, herdando todos os métodos CRUD
 * - Abstração: a implementação real é gerada pelo Spring Data em tempo de execução
 */
@Repository
public interface MarcaRepositorio extends JpaRepository<Marca, Long> {

    // Busca marca pelo nome (case-insensitive)
    Optional<Marca> findByNameIgnoreCase(String name);

    // Verifica se já existe marca com esse nome
    boolean existsByNameIgnoreCase(String name);

    // Busca marcas pelo país de origem
    List<Marca> findByCountryOfOriginIgnoreCase(String countryOfOrigin);

    // Busca marcas que contenham o termo no nome
    List<Marca> findByNameContainingIgnoreCase(String name);

    // Busca marcas com seus modelos carregados
    @Query("SELECT DISTINCT b FROM Marca b LEFT JOIN FETCH b.models WHERE b.id = :id")
    Optional<Marca> findByIdWithModels(@Param("id") Long id);
}
