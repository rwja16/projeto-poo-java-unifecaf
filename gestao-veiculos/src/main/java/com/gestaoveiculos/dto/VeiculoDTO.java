package com.gestaoveiculos.dto;

import com.gestaoveiculos.modelo.Veiculo;
import com.gestaoveiculos.modelo.StatusVeiculo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO principal para Veículo — usado em criação e resposta completa.
 *
 * POO aplicada:
 * - Encapsulamento: separa o modelo de domínio da camada de transporte
 * - Contém inner class estática para atualização parcial (AtualizacaoDTO)
 */
public class VeiculoDTO {

    private Long id;

    @NotBlank(message = "A cor é obrigatória")
    @Size(max = 50)
    private String color;

    @NotNull(message = "O ano de fabricação é obrigatório")
    @Min(value = 1886)
    @Max(value = 2100)
    private Integer manufactureYear;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull(message = "A quilometragem é obrigatória")
    @Min(value = 0)
    private Integer mileage;

    private StatusVeiculo status;

    @Size(max = 500)
    private String notes;

    @NotNull(message = "O modelo do veículo é obrigatório")
    private Long vehicleModelId;

    // Campos de leitura (preenchidos na resposta)
    private String vehicleModelName;
    private String brandName;
    private Long brandId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ==================== CONSTRUTORES ====================

    public VeiculoDTO() {}

    /** Converte entidade Veiculo para DTO completo */
    public static VeiculoDTO fromEntity(Veiculo vehicle) {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(vehicle.getId());
        dto.setColor(vehicle.getColor());
        dto.setManufactureYear(vehicle.getManufactureYear());
        dto.setPrice(vehicle.getPrice());
        dto.setMileage(vehicle.getMileage());
        dto.setStatus(vehicle.getStatus());
        dto.setNotes(vehicle.getNotes());
        dto.setCreatedAt(vehicle.getCreatedAt());
        dto.setUpdatedAt(vehicle.getUpdatedAt());

        if (vehicle.getVehicleModel() != null) {
            dto.setVehicleModelId(vehicle.getVehicleModel().getId());
            dto.setVehicleModelName(vehicle.getVehicleModel().getName());

            if (vehicle.getVehicleModel().getBrand() != null) {
                dto.setBrandId(vehicle.getVehicleModel().getBrand().getId());
                dto.setBrandName(vehicle.getVehicleModel().getBrand().getName());
            }
        }
        return dto;
    }

    // ==================== INNER CLASS: UPDATE DTO ====================

    /**
     * DTO para atualização parcial de veículo.
     * Permite atualizar apenas preço, quilometragem e status.
     *
     * POO: encapsula os dados permitidos para atualização
     */
    public static class AtualizacaoDTO {

        @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
        private BigDecimal price;

        @Min(value = 0, message = "Quilometragem não pode ser negativa")
        private Integer mileage;

        private StatusVeiculo status;

        @Size(max = 500)
        private String notes;

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }

        public Integer getMileage() { return mileage; }
        public void setMileage(Integer mileage) { this.mileage = mileage; }

        public StatusVeiculo getStatus() { return status; }
        public void setStatus(StatusVeiculo status) { this.status = status; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    // ==================== GETTERS E SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getManufactureYear() { return manufactureYear; }
    public void setManufactureYear(Integer manufactureYear) { this.manufactureYear = manufactureYear; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getMileage() { return mileage; }
    public void setMileage(Integer mileage) { this.mileage = mileage; }

    public StatusVeiculo getStatus() { return status; }
    public void setStatus(StatusVeiculo status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getVehicleModelId() { return vehicleModelId; }
    public void setVehicleModelId(Long vehicleModelId) { this.vehicleModelId = vehicleModelId; }

    public String getVehicleModelName() { return vehicleModelName; }
    public void setVehicleModelName(String vehicleModelName) { this.vehicleModelName = vehicleModelName; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
