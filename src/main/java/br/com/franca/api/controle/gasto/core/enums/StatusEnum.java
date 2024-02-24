package br.com.franca.api.controle.gasto.core.enums;

/**
 * Enum que representa os tipos de status que um usuário pode ter.
 *
 * @since 1.0
 */

public enum StatusEnum {

    ATIVO("A"),
    INATIVO("I"),
    BLOQUEADO("B");

    private String status;

    private StatusEnum(String status) {
        this.status = status;
    }
}
