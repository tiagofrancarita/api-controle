package br.com.franca.api.controle.gasto.core.repositories;

import br.com.franca.api.controle.gasto.core.entites.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface que representa o repositório de dados da entidade Usuario.
 *
 * @see Usuario
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

    @Query("SELECT u FROM Usuario u WHERE u.login = :login")
    Optional<Usuario> validaLogin(String login);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> validaEmail(String email);

    Usuario findByEmail(String email);





}
