package br.com.franca.api.controle.gasto.core.enums;

/**
 * Enum que representa os tipos de status que um usuário pode ter.
 *
 * @since 1.0
 */

public enum StatusEnum {

    A("ATIVO"),
    I("INATIVO"),
    B("BLOQUEADO");

    private String status;

    private StatusEnum(String status) {
        this.status = status;
    }
}
