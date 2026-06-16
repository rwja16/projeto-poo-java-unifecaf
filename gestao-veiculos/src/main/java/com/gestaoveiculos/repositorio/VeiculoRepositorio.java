package com.gestaoveiculos.repositorio;

import com.gestaoveiculos.modelo.Veiculo;
import com.gestaoveiculos.modelo.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositório de Veículos com suporte a filtros avançados.
 *
 * POO aplicada:
 * - Abstração: queries JPQL encapsulam toda a lógica de filtragem
 * - Herança: herda métodos CRUD do JpaRepository
 * - Polimorfismo: parâmetros opcionais via JPQL condicional
 */
@Repository
public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {

    // ==================== FILTROS SIMPLES ====================

    List<Veiculo> findByStatus(StatusVeiculo status);

    List<Veiculo> findByManufactureYear(Integer year);

    List<Veiculo> findByManufactureYearBetween(Integer yearStart, Integer yearEnd);

    List<Veiculo> findByVehicleModelId(Long modelId);

    List<Veiculo> findByVehicleModelBrandId(Long brandId);

    List<Veiculo> findByColorIgnoreCase(String color);

    // ==================== FILTROS POR PREÇO ====================

    List<Veiculo> findByPriceLessThanEqual(BigDecimal maxPrice);

    List<Veiculo> findByPriceGreaterThanEqual(BigDecimal minPrice);

    List<Veiculo> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // ==================== FILTROS COMBINADOS ====================

    List<Veiculo> findByVehicleModelBrandIdAndStatus(Long brandId, StatusVeiculo status);

    List<Veiculo> findByVehicleModelIdAndStatus(Long modelId, StatusVeiculo status);

    // ==================== QUERY CUSTOMIZADA COM MÚLTIPLOS FILTROS ====================

    /**
     * Busca avançada com todos os filtros opcionais combinados.
     * Parâmetros null são ignorados na query.
     */
    @Query("""
        SELECT v FROM Veiculo v
        JOIN FETCH v.vehicleModel vm
        JOIN FETCH vm.brand b
        WHERE (:brandId IS NULL OR b.id = :brandId)
          AND (:modelId IS NULL OR vm.id = :modelId)
          AND (:status IS NULL OR v.status = :status)
          AND (:yearMin IS NULL OR v.manufactureYear >= :yearMin)
          AND (:yearMax IS NULL OR v.manufactureYear <= :yearMax)
          AND (:priceMin IS NULL OR v.price >= :priceMin)
          AND (:priceMax IS NULL OR v.price <= :priceMax)
          AND (:color IS NULL OR LOWER(v.color) LIKE LOWER(CONCAT('%', :color, '%')))
        ORDER BY v.price ASC
    """)
    List<Veiculo> findWithFilters(
        @Param("brandId") Long brandId,
        @Param("modelId") Long modelId,
        @Param("status") StatusVeiculo status,
        @Param("yearMin") Integer yearMin,
        @Param("yearMax") Integer yearMax,
        @Param("priceMin") BigDecimal priceMin,
        @Param("priceMax") BigDecimal priceMax,
        @Param("color") String color
    );

    // ==================== ESTATÍSTICAS ====================

    @Query("SELECT COUNT(v) FROM Veiculo v WHERE v.status = :status")
    Long countByStatus(@Param("status") StatusVeiculo status);

    @Query("SELECT AVG(v.price) FROM Veiculo v WHERE v.status = 'DISPONIVEL'")
    BigDecimal averagePriceAvailable();

    @Query("SELECT MIN(v.price) FROM Veiculo v WHERE v.status = 'DISPONIVEL'")
    BigDecimal minPriceAvailable();

    @Query("SELECT MAX(v.price) FROM Veiculo v WHERE v.status = 'DISPONIVEL'")
    BigDecimal maxPriceAvailable();
}
