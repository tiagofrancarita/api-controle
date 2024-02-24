package br.com.franca.api.controle.gasto.core.services.implementations;

import br.com.franca.api.controle.gasto.core.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe de implementação dos métodos de serviço de autenticação
 * implementa a interface UserDetailsService do Spring Security
 */

@Service
public class AutenticacaoServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;



    /**
     * Método que busca um usuário pelo login
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return usuarioRepository.findByLogin(username);
    }
}
