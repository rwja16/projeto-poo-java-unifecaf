package com.gestaoveiculos.repositorio;

import com.gestaoveiculos.modelo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório de Modelos de Veículos.
 *
 * POO aplicada:
 * - Herança: herda de JpaRepository (polimorfismo de interface)
 * - Abstração: queries JPQL encapsulam a lógica de busca no banco
 */
@Repository
public interface ModeloRepositorio extends JpaRepository<Modelo, Long> {

    // Busca modelos por marca (por ID da marca)
    List<Modelo> findByBrandId(Long brandId);

    // Busca modelos por nome (case-insensitive)
    List<Modelo> findByNameContainingIgnoreCase(String name);

    // Busca modelos por categoria
    List<Modelo> findByCategoryIgnoreCase(String category);

    // Busca modelos por marca e categoria
    List<Modelo> findByBrandIdAndCategoryIgnoreCase(Long brandId, String category);

    // Verifica se existe modelo com esse nome para a marca informada
    boolean existsByNameIgnoreCaseAndBrandId(String name, Long brandId);

    // Busca modelos com a marca carregada (evita N+1)
    @Query("SELECT vm FROM Modelo vm JOIN FETCH vm.brand WHERE vm.brand.id = :brandId")
    List<Modelo> findByBrandIdWithBrand(@Param("brandId") Long brandId);
}
