package com.gestaoveiculos.dto;

import com.gestaoveiculos.modelo.Marca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para Marca.
 *
 * POO aplicada:
 * - Encapsulamento: isola a camada de apresentação do modelo de domínio
 * - Abstração: expõe apenas os dados necessários para cada operação
 */
public class MarcaDTO {

    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 100)
    private String countryOfOrigin;

    private LocalDateTime createdAt;
    private int totalModels;

    // ==================== CONSTRUTORES ====================

    public MarcaDTO() {}

    /** Converte entidade Marca para MarcaDTO */
    public static MarcaDTO fromEntity(Marca brand) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setDescription(brand.getDescription());
        dto.setCountryOfOrigin(brand.getCountryOfOrigin());
        dto.setCreatedAt(brand.getCreatedAt());
        dto.setTotalModels(brand.getModels() != null ? brand.getModels().size() : 0);
        return dto;
    }

    /** Converte MarcaDTO para entidade Marca */
    public Marca toEntity() {
        Marca brand = new Marca();
        brand.setName(this.name);
        brand.setDescription(this.description);
        brand.setCountryOfOrigin(this.countryOfOrigin);
        return brand;
    }

    // ==================== GETTERS E SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCountryOfOrigin() { return countryOfOrigin; }
    public void setCountryOfOrigin(String countryOfOrigin) { this.countryOfOrigin = countryOfOrigin; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getTotalModels() { return totalModels; }
    public void setTotalModels(int totalModels) { this.totalModels = totalModels; }
}
