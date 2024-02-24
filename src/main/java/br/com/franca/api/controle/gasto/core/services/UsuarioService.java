package br.com.franca.api.controle.gasto.core.services;

import br.com.franca.api.controle.gasto.core.dtos.UsuariosDto;
import br.com.franca.api.controle.gasto.core.entites.Usuario;
import java.util.List;

/**
 * Interface de serviço para a entidade Usuario.
 *
 * @since 1.0
 * @version 1.0
 * @see Usuario
 * @see UsuariosDto
 */


public interface UsuarioService {


     UsuariosDto salvarUsuario(UsuariosDto usuariosDto) throws Exception;

     List<Usuario> listarUsuarios() throws Exception;

}
