package com.gestaoveiculos.controlador;

import com.gestaoveiculos.dto.RespostaApi;
import com.gestaoveiculos.dto.ModeloDTO;
import com.gestaoveiculos.servico.ModeloServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para Modelos de Veículos.
 *
 * Endpoints:
 *   POST   /api/models           -> cria modelo
 *   GET    /api/models           -> lista (filtros opcionais: brandId, category, name)
 *   GET    /api/models/{id}      -> busca por ID
 *   PUT    /api/models/{id}      -> atualiza
 *   DELETE /api/models/{id}      -> remove
 */
@RestController
@RequestMapping("/api/models")
@CrossOrigin(origins = "*")
public class ModeloControlador {

    private final ModeloServico modelService;

    public ModeloControlador(ModeloServico modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<RespostaApi<ModeloDTO>> create(
            @Valid @RequestBody ModeloDTO dto) {

        ModeloDTO created = modelService.create(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(RespostaApi.success(created, "Modelo cadastrado com sucesso"));
    }

    @GetMapping
    public ResponseEntity<RespostaApi<List<ModeloDTO>>> findAll(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name) {

        List<ModeloDTO> models;
        if (brandId != null) {
            models = modelService.findByBrand(brandId);
        } else if (category != null) {
            models = modelService.findByCategory(category);
        } else if (name != null) {
            models = modelService.findByName(name);
        } else {
            models = modelService.findAll();
        }

        return ResponseEntity.ok(RespostaApi.success(models));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi<ModeloDTO>> findById(@PathVariable Long id) {
        ModeloDTO model = modelService.findById(id);
        return ResponseEntity.ok(RespostaApi.success(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaApi<ModeloDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ModeloDTO dto) {

        ModeloDTO updated = modelService.update(id, dto);
        return ResponseEntity.ok(RespostaApi.success(updated, "Modelo atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaApi<Void>> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseEntity.ok(RespostaApi.success(null, "Modelo removido com sucesso"));
    }
}
