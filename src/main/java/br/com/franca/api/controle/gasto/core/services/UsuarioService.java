package br.com.franca.api.controle.gasto.core.services;

import br.com.franca.api.controle.gasto.core.dtos.UsuariosDTO;
import br.com.franca.api.controle.gasto.core.entites.Usuario;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Interface de serviço para a entidade Usuario.
 *
 * @since 1.0
 * @version 1.0
 * @see Usuario
 * @see UsuariosDTO
 */


public interface UsuarioService {

     ResponseEntity<Usuario> salvarUsuario(Usuario usuario) throws Exception;
     List<Usuario> listarUsuarios() throws Exception;

}