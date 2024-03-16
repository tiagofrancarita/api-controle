package br.com.franca.api.controle.gasto.core.services;

import br.com.franca.api.controle.gasto.core.dtos.AuthDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AutenticacaoService extends UserDetailsService {

    public String gerarToken(AuthDTO authDTO);
}
