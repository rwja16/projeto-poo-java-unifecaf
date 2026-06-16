package com.gestaoveiculos.controlador;

import com.gestaoveiculos.dto.RespostaApi;
import com.gestaoveiculos.dto.VeiculoDTO;
import com.gestaoveiculos.dto.FiltroVeiculoDTO;
import com.gestaoveiculos.modelo.StatusVeiculo;
import com.gestaoveiculos.servico.VeiculoServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controller REST principal — gerencia os Veículos.
 *
 * POO aplicada:
 * - Encapsulamento: toda lógica de negócio fica no VeiculoServico
 * - Abstração: o controller só conhece o contrato do serviço
 *
 * Endpoints:
 *   POST   /api/vehicles              -> cadastra veículo
 *   GET    /api/vehicles              -> lista todos
 *   GET    /api/vehicles/{id}         -> busca por ID
 *   GET    /api/vehicles/filter       -> busca com filtros
 *   GET    /api/vehicles/statistics   -> estatísticas do estoque
 *   PUT    /api/vehicles/{id}         -> atualiza preço/km/status
 *   PATCH  /api/vehicles/{id}/sell    -> registra venda
 *   PATCH  /api/vehicles/{id}/reserve -> reserva veículo
 *   DELETE /api/vehicles/{id}         -> remove veículo
 */
@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VeiculoControlador {

    private final VeiculoServico vehicleService;

    public VeiculoControlador(VeiculoServico vehicleService) {
        this.vehicleService = vehicleService;
    }

    // ==================== CREATE ====================

    @PostMapping
    public ResponseEntity<RespostaApi<VeiculoDTO>> create(@Valid @RequestBody VeiculoDTO dto) {
        VeiculoDTO created = vehicleService.create(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(RespostaApi.success(created, "Veículo cadastrado com sucesso"));
    }

    // ==================== READ ====================

    @GetMapping
    public ResponseEntity<RespostaApi<List<VeiculoDTO>>> findAll(
            @RequestParam(required = false) StatusVeiculo status) {

        List<VeiculoDTO> vehicles = (status != null)
            ? vehicleService.findByStatus(status)
            : vehicleService.findAll();

        return ResponseEntity.ok(RespostaApi.success(vehicles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi<VeiculoDTO>> findById(@PathVariable Long id) {
        VeiculoDTO vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(RespostaApi.success(vehicle));
    }

    // ==================== FILTRO AVANÇADO ====================

    @GetMapping("/filter")
    public ResponseEntity<RespostaApi<List<VeiculoDTO>>> filter(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long modelId,
            @RequestParam(required = false) StatusVeiculo status,
            @RequestParam(required = false) Integer yearMin,
            @RequestParam(required = false) Integer yearMax,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) String color) {

        FiltroVeiculoDTO filter = new FiltroVeiculoDTO();
        filter.setBrandId(brandId);
        filter.setModelId(modelId);
        filter.setStatus(status);
        filter.setYearMin(yearMin);
        filter.setYearMax(yearMax);
        filter.setPriceMin(priceMin);
        filter.setPriceMax(priceMax);
        filter.setColor(color);

        List<VeiculoDTO> vehicles = vehicleService.findWithFilters(filter);
        return ResponseEntity.ok(
            RespostaApi.success(vehicles, vehicles.size() + " veículo(s) encontrado(s)")
        );
    }

    // ==================== ESTATÍSTICAS ====================

    @GetMapping("/statistics")
    public ResponseEntity<RespostaApi<Map<String, Object>>> statistics() {
        Map<String, Object> stats = vehicleService.getStatistics();
        return ResponseEntity.ok(RespostaApi.success(stats));
    }

    // ==================== UPDATE ====================

    @PutMapping("/{id}")
    public ResponseEntity<RespostaApi<VeiculoDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoDTO.AtualizacaoDTO dto) {

        VeiculoDTO updated = vehicleService.update(id, dto);
        return ResponseEntity.ok(RespostaApi.success(updated, "Veículo atualizado com sucesso"));
    }

    // ==================== AÇÕES DE NEGÓCIO ====================

    @PatchMapping("/{id}/sell")
    public ResponseEntity<RespostaApi<VeiculoDTO>> sell(@PathVariable Long id) {
        VeiculoDTO sold = vehicleService.sell(id);
        return ResponseEntity.ok(RespostaApi.success(sold, "Venda registrada com sucesso"));
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<RespostaApi<VeiculoDTO>> reserve(@PathVariable Long id) {
        VeiculoDTO reserved = vehicleService.reserve(id);
        return ResponseEntity.ok(RespostaApi.success(reserved, "Veículo reservado com sucesso"));
    }

    // ==================== DELETE ====================

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaApi<Void>> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.ok(RespostaApi.success(null, "Veículo removido com sucesso"));
    }
}
