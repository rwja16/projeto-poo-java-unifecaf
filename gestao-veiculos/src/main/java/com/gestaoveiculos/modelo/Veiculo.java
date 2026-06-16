package com.gestaoveiculos.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade principal que representa um Veículo no estoque.
 *
 * POO aplicada:
 * - Encapsulamento: todos os atributos são privados
 * - Validações encapsuladas via anotações Bean Validation
 * - Relacionamentos com Marca e Modelo demonstram associação/composição
 * - Métodos de negócio (vender, reservar) encapsulam regras de domínio
 */
@Entity
@Table(name = "vehicles")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A cor é obrigatória")
    @Size(max = 50, message = "A cor deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String color;

    @NotNull(message = "O ano de fabricação é obrigatório")
    @Min(value = 1886, message = "Ano inválido (mínimo: 1886)")
    @Max(value = 2100, message = "Ano inválido (máximo: 2100)")
    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "Formato de preço inválido")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @NotNull(message = "A quilometragem é obrigatória")
    @Min(value = 0, message = "Quilometragem não pode ser negativa")
    @Column(nullable = false)
    private Integer mileage;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVeiculo status;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Column(length = 500)
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relacionamento: muitos Veículos pertencem a um Modelo
    @NotNull(message = "O modelo do veículo é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_model_id", nullable = false)
    private Modelo vehicleModel;

    // ==================== CONSTRUTORES ====================

    public Veiculo() {
        this.status = StatusVeiculo.DISPONIVEL; // Status padrão ao criar
    }

    public Veiculo(String color, Integer manufactureYear, BigDecimal price,
                   Integer mileage, Modelo vehicleModel) {
        this.color = color;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.mileage = mileage;
        this.vehicleModel = vehicleModel;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    // ==================== MÉTODOS DE NEGÓCIO ====================

    /**
     * Registra a venda do veículo, alterando seu status.
     * Encapsula a regra de negócio de venda.
     */
    public void sell() {
        if (this.status != StatusVeiculo.DISPONIVEL && this.status != StatusVeiculo.RESERVADO) {
            throw new IllegalStateException(
                "Veículo não pode ser vendido. Status atual: " + this.status.getDescricao()
            );
        }
        this.status = StatusVeiculo.VENDIDO;
    }

    /**
     * Reserva o veículo para um cliente.
     */
    public void reserve() {
        if (this.status != StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException(
                "Apenas veículos disponíveis podem ser reservados. Status atual: " + this.status.getDescricao()
            );
        }
        this.status = StatusVeiculo.RESERVADO;
    }

    /**
     * Torna o veículo disponível novamente.
     */
    public void makeAvailable() {
        if (this.status == StatusVeiculo.VENDIDO || this.status == StatusVeiculo.DESCONTINUADO) {
            throw new IllegalStateException(
                "Veículo vendido ou descontinuado não pode ser disponibilizado."
            );
        }
        this.status = StatusVeiculo.DISPONIVEL;
    }

    /**
     * Verifica se o veículo está disponível para venda.
     */
    public boolean isAvailable() {
        return this.status == StatusVeiculo.DISPONIVEL;
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Modelo getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(Modelo vehicleModel) { this.vehicleModel = vehicleModel; }

    @Override
    public String toString() {
        return "Veiculo{id=" + id +
               ", model=" + (vehicleModel != null ? vehicleModel.getName() : "N/A") +
               ", year=" + manufactureYear +
               ", color='" + color + "'" +
               ", price=" + price +
               ", status=" + status + "}";
    }
}
