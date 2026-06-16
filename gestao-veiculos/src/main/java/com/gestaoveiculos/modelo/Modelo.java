package com.gestaoveiculos.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um Modelo de veículo.
 *
 * POO aplicada:
 * - Encapsulamento: dados protegidos, acesso controlado
 * - Associação com Marca (Many-to-One) e Veiculo (One-to-Many)
 */
@Entity
@Table(name = "vehicle_models")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório")
    @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String category; // SUV, Sedan, Hatch, Pickup, etc.

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relacionamento: muitos Modelos pertencem a uma Marca
    @NotNull(message = "A marca é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Marca brand;

    // Relacionamento: um Modelo tem muitos Veículos
    @OneToMany(mappedBy = "vehicleModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Veiculo> vehicles = new ArrayList<>();

    // ==================== CONSTRUTORES ====================

    public Modelo() {}

    public Modelo(String name, String category, Marca brand) {
        this.name = name;
        this.category = category;
        this.brand = brand;
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

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Marca getBrand() { return brand; }
    public void setBrand(Marca brand) { this.brand = brand; }

    public List<Veiculo> getVehicles() { return vehicles; }
    public void setVehicles(List<Veiculo> vehicles) { this.vehicles = vehicles; }

    @Override
    public String toString() {
        return "Modelo{id=" + id + ", name='" + name + "', category='" + category + "'}";
    }
}
