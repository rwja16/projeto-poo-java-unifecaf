package com.gestaoveiculos.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso não é encontrado no banco de dados.
 *
 * POO aplicada:
 * - Herança: estende RuntimeException
 * - Encapsulamento: carrega contexto do erro (recurso e ID)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {

    private final String resourceName;
    private final Long resourceId;

    public RecursoNaoEncontradoException(String resourceName, Long resourceId) {
        super(resourceName + " com ID " + resourceId + " não encontrado(a)");
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public RecursoNaoEncontradoException(String message) {
        super(message);
        this.resourceName = null;
        this.resourceId = null;
    }

    public String getResourceName() { return resourceName; }
    public Long getResourceId() { return resourceId; }
}
