package com.gestaoveiculos.servico;

import com.gestaoveiculos.dto.VeiculoDTO;
import com.gestaoveiculos.dto.FiltroVeiculoDTO;
import com.gestaoveiculos.excecao.RecursoNaoEncontradoException;
import com.gestaoveiculos.modelo.Veiculo;
import com.gestaoveiculos.modelo.Modelo;
import com.gestaoveiculos.modelo.StatusVeiculo;
import com.gestaoveiculos.repositorio.ModeloRepositorio;
import com.gestaoveiculos.repositorio.VeiculoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço de Veículos — coração do sistema CRUD.
 *
 * POO aplicada:
 * - Encapsulamento: regras de negócio do veículo isoladas aqui
 * - Injeção de Dependência: repositórios injetados pelo Spring
 * - Reutiliza métodos de negócio da entidade Veiculo (sell, reserve, etc.)
 */
@Service
@Transactional
public class VeiculoServico {

    private final VeiculoRepositorio vehicleRepository;
    private final ModeloRepositorio modelRepository;

    public VeiculoServico(VeiculoRepositorio vehicleRepository,
                          ModeloRepositorio modelRepository) {
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
    }

    // ==================== CREATE ====================

    public VeiculoDTO create(VeiculoDTO dto) {
        Modelo model = modelRepository.findById(dto.getVehicleModelId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", dto.getVehicleModelId()));

        Veiculo vehicle = new Veiculo();
        vehicle.setColor(dto.getColor());
        vehicle.setManufactureYear(dto.getManufactureYear());
        vehicle.setPrice(dto.getPrice());
        vehicle.setMileage(dto.getMileage());
        vehicle.setNotes(dto.getNotes());
        vehicle.setVehicleModel(model);

        // Se status não informado, usa DISPONIVEL (padrão da entidade)
        vehicle.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusVeiculo.DISPONIVEL);

        Veiculo saved = vehicleRepository.save(vehicle);
        return VeiculoDTO.fromEntity(saved);
    }

    // ==================== READ ====================

    @Transactional(readOnly = true)
    public List<VeiculoDTO> findAll() {
        return vehicleRepository.findAll()
            .stream()
            .map(VeiculoDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VeiculoDTO findById(Long id) {
        Veiculo vehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo", id));
        return VeiculoDTO.fromEntity(vehicle);
    }

    @Transactional(readOnly = true)
    public List<VeiculoDTO> findByStatus(StatusVeiculo status) {
        return vehicleRepository.findByStatus(status)
            .stream()
            .map(VeiculoDTO::fromEntity)
            .collect(Collectors.toList());
    }

    // ==================== FILTRO AVANÇADO ====================

    /**
     * Busca veículos aplicando vários filtros ao mesmo tempo.
     * Filtros nulos são ignorados.
     */
    @Transactional(readOnly = true)
    public List<VeiculoDTO> findWithFilters(FiltroVeiculoDTO filter) {
        List<Veiculo> vehicles = vehicleRepository.findWithFilters(
            filter.getBrandId(),
            filter.getModelId(),
            filter.getStatus(),
            filter.getYearMin(),
            filter.getYearMax(),
            filter.getPriceMin(),
            filter.getPriceMax(),
            filter.getColor()
        );

        return vehicles.stream()
            .map(VeiculoDTO::fromEntity)
            .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza preço, quilometragem e status do veículo.
     * Atualização parcial: só altera os campos informados.
     */
    public VeiculoDTO update(Long id, VeiculoDTO.AtualizacaoDTO dto) {
        Veiculo vehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo", id));

        if (dto.getPrice() != null) {
            vehicle.setPrice(dto.getPrice());
        }
        if (dto.getMileage() != null) {
            vehicle.setMileage(dto.getMileage());
        }
        if (dto.getStatus() != null) {
            vehicle.setStatus(dto.getStatus());
        }
        if (dto.getNotes() != null) {
            vehicle.setNotes(dto.getNotes());
        }

        Veiculo updated = vehicleRepository.save(vehicle);
        return VeiculoDTO.fromEntity(updated);
    }

    // ==================== AÇÕES DE NEGÓCIO ====================

    /** Marca o veículo como vendido (usa o método de negócio da entidade) */
    public VeiculoDTO sell(Long id) {
        Veiculo vehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo", id));
        vehicle.sell();
        return VeiculoDTO.fromEntity(vehicleRepository.save(vehicle));
    }

    /** Reserva o veículo */
    public VeiculoDTO reserve(Long id) {
        Veiculo vehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo", id));
        vehicle.reserve();
        return VeiculoDTO.fromEntity(vehicleRepository.save(vehicle));
    }

    // ==================== DELETE ====================

    public void delete(Long id) {
        Veiculo vehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo", id));
        vehicleRepository.delete(vehicle);
    }

    // ==================== ESTATÍSTICAS ====================

    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalVeiculos", vehicleRepository.count());
        stats.put("disponiveis", vehicleRepository.countByStatus(StatusVeiculo.DISPONIVEL));
        stats.put("vendidos", vehicleRepository.countByStatus(StatusVeiculo.VENDIDO));
        stats.put("reservados", vehicleRepository.countByStatus(StatusVeiculo.RESERVADO));

        BigDecimal avg = vehicleRepository.averagePriceAvailable();
        BigDecimal min = vehicleRepository.minPriceAvailable();
        BigDecimal max = vehicleRepository.maxPriceAvailable();

        stats.put("precoMedioDisponiveis", avg != null ? avg : BigDecimal.ZERO);
        stats.put("precoMinimoDisponiveis", min != null ? min : BigDecimal.ZERO);
        stats.put("precoMaximoDisponiveis", max != null ? max : BigDecimal.ZERO);

        return stats;
    }
}
