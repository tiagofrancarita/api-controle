package br.com.franca.api.controle.gasto.core.enums;

/**
 * Enum que representa os tipos de roles que um usuário pode ter.
 *
 *
 * @since 1.0
 */

public enum RoleEnum {

    ADMIN("admin"),
    USER("user");

    private String role;

    private RoleEnum(String role) {
        this.role = role;
    }
}
