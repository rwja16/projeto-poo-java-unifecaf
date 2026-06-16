package com.gestaoveiculos.servico;

import com.gestaoveiculos.dto.ModeloDTO;
import com.gestaoveiculos.excecao.RegraNegocioException;
import com.gestaoveiculos.excecao.RecursoNaoEncontradoException;
import com.gestaoveiculos.modelo.Marca;
import com.gestaoveiculos.modelo.Modelo;
import com.gestaoveiculos.repositorio.MarcaRepositorio;
import com.gestaoveiculos.repositorio.ModeloRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de Modelos de Veículos.
 *
 * POO aplicada:
 * - Associação: Modelo sempre associado a uma Marca
 * - Encapsulamento: regras de associação protegidas nesta camada
 */
@Service
@Transactional
public class ModeloServico {

    private final ModeloRepositorio modelRepository;
    private final MarcaRepositorio brandRepository;

    public ModeloServico(ModeloRepositorio modelRepository,
                               MarcaRepositorio brandRepository) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
    }

    // ==================== CREATE ====================

    public ModeloDTO create(ModeloDTO dto) {
        Marca brand = brandRepository.findById(dto.getBrandId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", dto.getBrandId()));

        // Regra: modelo com mesmo nome não pode ser duplicado para a mesma marca
        if (modelRepository.existsByNameIgnoreCaseAndBrandId(dto.getName(), dto.getBrandId())) {
            throw new RegraNegocioException(
                "Já existe o modelo '" + dto.getName() + "' para a marca " + brand.getName()
            );
        }

        Modelo model = new Modelo(dto.getName(), dto.getCategory(), brand);
        Modelo saved = modelRepository.save(model);
        return ModeloDTO.fromEntity(saved);
    }

    // ==================== READ ====================

    @Transactional(readOnly = true)
    public List<ModeloDTO> findAll() {
        return modelRepository.findAll()
            .stream()
            .map(ModeloDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ModeloDTO findById(Long id) {
        Modelo model = modelRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", id));
        return ModeloDTO.fromEntity(model);
    }

    @Transactional(readOnly = true)
    public List<ModeloDTO> findByBrand(Long brandId) {
        return modelRepository.findByBrandIdWithBrand(brandId)
            .stream()
            .map(ModeloDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ModeloDTO> findByCategory(String category) {
        return modelRepository.findByCategoryIgnoreCase(category)
            .stream()
            .map(ModeloDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ModeloDTO> findByName(String name) {
        return modelRepository.findByNameContainingIgnoreCase(name)
            .stream()
            .map(ModeloDTO::fromEntity)
            .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================

    public ModeloDTO update(Long id, ModeloDTO dto) {
        Modelo model = modelRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", id));

        if (dto.getBrandId() != null && !dto.getBrandId().equals(model.getBrand().getId())) {
            Marca newBrand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", dto.getBrandId()));
            model.setBrand(newBrand);
        }

        if (dto.getName() != null) model.setName(dto.getName());
        if (dto.getCategory() != null) model.setCategory(dto.getCategory());

        Modelo updated = modelRepository.save(model);
        return ModeloDTO.fromEntity(updated);
    }

    // ==================== DELETE ====================

    public void delete(Long id) {
        Modelo model = modelRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", id));

        if (!model.getVehicles().isEmpty()) {
            throw new RegraNegocioException(
                "Não é possível excluir o modelo '" + model.getName() +
                "' pois ele possui " + model.getVehicles().size() + " veículo(s) cadastrado(s)."
            );
        }

        modelRepository.delete(model);
    }
}
