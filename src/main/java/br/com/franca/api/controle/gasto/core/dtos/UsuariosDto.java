package br.com.franca.api.controle.gasto.core.dtos;


import br.com.franca.api.controle.gasto.core.enums.RoleEnum;
import br.com.franca.api.controle.gasto.core.enums.StatusEnum;


import java.time.LocalDateTime;

/**
 * DTO que representa a tabela de usuários no banco de dados
 *
 *
 */
public record UsuariosDto(

        String id,
        String nome,
        String email,
        String login,
        String senha,
        String confirmaSenha,
        StatusEnum status,
        LocalDateTime dataCadastro,
        LocalDateTime dataBloqueio,
        LocalDateTime dataDesbloqueio,
        LocalDateTime dataInativacao,
        LocalDateTime dataReativacao,
        LocalDateTime dataExpiracaoSenha,
        Integer numeroTentativas,
        RoleEnum role


) {
    }

