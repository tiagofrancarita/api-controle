package br.com.franca.api.controle.gasto.core.repositories;

import br.com.franca.api.controle.gasto.core.entites.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface que representa o repositório de dados da entidade Usuario.
 *
 * @see Usuario
 */

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

    @Query(value = "select usuario from Usuario usuario where usuario.login = ?1")
    public Usuario existeLogin(String login);

    @Query(value = "select usuario from Usuario usuario where usuario.email = ?1")
    public Usuario existeEmail(String email);

}