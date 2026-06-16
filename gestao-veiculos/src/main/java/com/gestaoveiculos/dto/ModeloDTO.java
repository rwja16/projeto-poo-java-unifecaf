package com.gestaoveiculos.dto;

import com.gestaoveiculos.modelo.Modelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO para Modelo de Veículo.
 */
public class ModeloDTO {

    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório")
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 50)
    private String category;

    @NotNull(message = "O ID da marca é obrigatório")
    private Long brandId;

    private String brandName;
    private LocalDateTime createdAt;
    private int totalVehicles;

    // ==================== CONSTRUTORES ====================

    public ModeloDTO() {}

    /** Converte entidade Modelo para DTO */
    public static ModeloDTO fromEntity(Modelo model) {
        ModeloDTO dto = new ModeloDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setCategory(model.getCategory());
        dto.setCreatedAt(model.getCreatedAt());

        if (model.getBrand() != null) {
            dto.setBrandId(model.getBrand().getId());
            dto.setBrandName(model.getBrand().getName());
        }

        dto.setTotalVehicles(model.getVehicles() != null ? model.getVehicles().size() : 0);
        return dto;
    }

    // ==================== GETTERS E SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getTotalVehicles() { return totalVehicles; }
    public void setTotalVehicles(int totalVehicles) { this.totalVehicles = totalVehicles; }
}
