package com.gestaoveiculos.servico;

import com.gestaoveiculos.dto.MarcaDTO;
import com.gestaoveiculos.excecao.RegraNegocioException;
import com.gestaoveiculos.excecao.RecursoNaoEncontradoException;
import com.gestaoveiculos.modelo.Marca;
import com.gestaoveiculos.repositorio.MarcaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de Marcas — camada de regras de negócio.
 *
 * POO aplicada:
 * - Encapsulamento: regras de negócio protegidas na camada de serviço
 * - Injeção de Dependência: MarcaRepositorio injetado pelo Spring (IoC)
 * - Single Responsibility: apenas regras relacionadas a Marcas
 */
@Service
@Transactional
public class MarcaServico {

    private final MarcaRepositorio brandRepository;

    // Injeção via construtor (boa prática, facilita testes)
    public MarcaServico(MarcaRepositorio brandRepository) {
        this.brandRepository = brandRepository;
    }

    // ==================== CREATE ====================

    public MarcaDTO create(MarcaDTO dto) {
        // Regra de negócio: marca com mesmo nome não pode ser duplicada
        if (brandRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RegraNegocioException("Já existe uma marca com o nome: " + dto.getName());
        }

        Marca brand = dto.toEntity();
        Marca saved = brandRepository.save(brand);
        return MarcaDTO.fromEntity(saved);
    }

    // ==================== READ ====================

    @Transactional(readOnly = true)
    public List<MarcaDTO> findAll() {
        return brandRepository.findAll()
            .stream()
            .map(MarcaDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MarcaDTO findById(Long id) {
        Marca brand = brandRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", id));
        return MarcaDTO.fromEntity(brand);
    }

    @Transactional(readOnly = true)
    public List<MarcaDTO> findByName(String name) {
        return brandRepository.findByNameContainingIgnoreCase(name)
            .stream()
            .map(MarcaDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MarcaDTO> findByCountry(String country) {
        return brandRepository.findByCountryOfOriginIgnoreCase(country)
            .stream()
            .map(MarcaDTO::fromEntity)
            .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================

    public MarcaDTO update(Long id, MarcaDTO dto) {
        Marca brand = brandRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", id));

        // Verifica duplicidade apenas se o nome foi alterado
        if (!brand.getName().equalsIgnoreCase(dto.getName()) &&
            brandRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RegraNegocioException("Já existe uma marca com o nome: " + dto.getName());
        }

        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setCountryOfOrigin(dto.getCountryOfOrigin());

        Marca updated = brandRepository.save(brand);
        return MarcaDTO.fromEntity(updated);
    }

    // ==================== DELETE ====================

    public void delete(Long id) {
        Marca brand = brandRepository.findByIdWithModels(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", id));

        // Regra: não pode excluir marca com modelos cadastrados
        if (!brand.getModels().isEmpty()) {
            throw new RegraNegocioException(
                "Não é possível excluir a marca '" + brand.getName() +
                "' pois ela possui " + brand.getModels().size() + " modelo(s) cadastrado(s)."
            );
        }

        brandRepository.delete(brand);
    }
}
