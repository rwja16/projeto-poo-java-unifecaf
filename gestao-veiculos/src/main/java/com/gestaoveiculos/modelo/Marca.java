package com.gestaoveiculos.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma Marca de veículo.
 *
 * POO aplicada:
 * - Encapsulamento: atributos privados, acesso via getters/setters
 * - Relacionamento bidirecional com Modelo (composição)
 */
@Entity
@Table(name = "brands")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Column(length = 255)
    private String description;

    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relacionamento: uma Marca tem muitos Modelos
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Modelo> models = new ArrayList<>();

    // ==================== CONSTRUTORES ====================

    public Marca() {}

    public Marca(String name, String description, String countryOfOrigin) {
        this.name = name;
        this.description = description;
        this.countryOfOrigin = countryOfOrigin;
    }

    // ==================== LIFECYCLE HOOKS ====================

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public List<Modelo> getModels() { return models; }
    public void setModels(List<Modelo> models) { this.models = models; }

    @Override
    public String toString() {
        return "Marca{id=" + id + ", name='" + name + "', country='" + countryOfOrigin + "'}";
    }
}
