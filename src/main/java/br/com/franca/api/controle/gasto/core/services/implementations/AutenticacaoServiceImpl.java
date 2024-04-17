package br.com.franca.api.controle.gasto.core.services.implementations;

import br.com.franca.api.controle.gasto.core.dtos.AuthDTO;
import br.com.franca.api.controle.gasto.core.entites.Usuario;
import br.com.franca.api.controle.gasto.core.repositories.UsuarioRepository;
import br.com.franca.api.controle.gasto.core.services.AutenticacaoService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Classe de implementação dos métodos de serviço de autenticação
 * implementa a interface UserDetailsService do Spring Security
 */

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    /*Token de validade 11 dias.. realizar a conversao do tempo para milessegundos 11 dias --> 959990000*/
    /*Tokemn expira em 10 minutos. */
    private static final long EXPIRATION_TIME = 600000;

    /*Chave de senha pra unir com o JWT */
    private static final String SECRET = "83cb3e74a81e53350dc27e6a368fc3c2";
    /* Prefixo do token */
    private static final String TOKEN_PREFIX = "Bearer";

    /*Retorno da requisição  */
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }


    @Override
    public String obterToken(AuthDTO authDto) {
        Usuario usuario = usuarioRepository.findByLogin(authDto.login());
        return geraTokenJwt(usuario);
    }

    public  String geraTokenJwt(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(geraDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao tentar gerar o token! " +exception.getMessage());
        }
    }

    @Override
    public String validaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }
    private Instant geraDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}