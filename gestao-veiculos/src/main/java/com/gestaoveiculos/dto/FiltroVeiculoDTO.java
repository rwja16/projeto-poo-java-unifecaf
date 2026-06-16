package com.gestaoveiculos.dto;

import com.gestaoveiculos.modelo.StatusVeiculo;

import java.math.BigDecimal;

/**
 * DTO para encapsular os filtros de busca de veículos.
 *
 * POO aplicada:
 * - Abstração: representa o conceito de "critérios de busca"
 * - Encapsulamento: agrupa todos os parâmetros de filtro em um único objeto
 */
public class FiltroVeiculoDTO {

    private Long brandId;
    private Long modelId;
    private StatusVeiculo status;
    private Integer yearMin;
    private Integer yearMax;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private String color;

    // ==================== CONSTRUTORES ====================

    public FiltroVeiculoDTO() {}

    // ==================== GETTERS E SETTERS ====================

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public Long getModelId() { return modelId; }
    public void setModelId(Long modelId) { this.modelId = modelId; }

    public StatusVeiculo getStatus() { return status; }
    public void setStatus(StatusVeiculo status) { this.status = status; }

    public Integer getYearMin() { return yearMin; }
    public void setYearMin(Integer yearMin) { this.yearMin = yearMin; }

    public Integer getYearMax() { return yearMax; }
    public void setYearMax(Integer yearMax) { this.yearMax = yearMax; }

    public BigDecimal getPriceMin() { return priceMin; }
    public void setPriceMin(BigDecimal priceMin) { this.priceMin = priceMin; }

    public BigDecimal getPriceMax() { return priceMax; }
    public void setPriceMax(BigDecimal priceMax) { this.priceMax = priceMax; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
