package com.gestaoveiculos.dto;

import java.time.LocalDateTime;

/**
 * Wrapper padronizado para respostas da API REST.
 *
 * POO aplicada:
 * - Encapsulamento: padroniza o formato de toda resposta da API
 * - Genérico (T): abstrai o tipo do dado retornado
 */
public class RespostaApi<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // ==================== CONSTRUTORES ESTÁTICOS ====================

    public static <T> RespostaApi<T> success(T data, String message) {
        RespostaApi<T> response = new RespostaApi<>();
        response.success = true;
        response.message = message;
        response.data = data;
        response.timestamp = LocalDateTime.now();
        return response;
    }

    public static <T> RespostaApi<T> success(T data) {
        return success(data, "Operação realizada com sucesso");
    }

    public static <T> RespostaApi<T> error(String message) {
        RespostaApi<T> response = new RespostaApi<>();
        response.success = false;
        response.message = message;
        response.data = null;
        response.timestamp = LocalDateTime.now();
        return response;
    }

    // ==================== GETTERS E SETTERS ====================

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
