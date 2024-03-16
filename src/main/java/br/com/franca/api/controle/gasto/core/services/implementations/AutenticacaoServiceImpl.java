package br.com.franca.api.controle.gasto.core.services.implementations;

import br.com.franca.api.controle.gasto.core.dtos.AuthDTO;
import br.com.franca.api.controle.gasto.core.entites.Usuario;
import br.com.franca.api.controle.gasto.core.repositories.UsuarioRepository;
import br.com.franca.api.controle.gasto.core.services.AutenticacaoService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

    private Logger log = LoggerFactory.getLogger(AutenticacaoServiceImpl.class);



    /**
     * Método que busca um usuário pelo login
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("------------------------------- Buscando usuário pelo login: {}-----------------------", " LOGIN: " + username);

        return usuarioRepository.findByLogin(username);
    }

    @Override
    public String gerarToken(AuthDTO authDTO) {

      Usuario usuario = usuarioRepository.findByLogin(authDTO.login());

        return gerarTokenJWT(usuario);
    }

    public String gerarTokenJWT(Usuario usuario) {

        try {
            log.info("------------------------------- Gerando token JWT para o usuário: {}-----------------------", usuario.getLogin());

            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            String jwt = JWT.create()
                    .withIssuer("API Controle de Gastos")
                    .withExpiresAt(gerarExpiracaoToken())
                    .withSubject(usuario.getLogin())
                    .withClaim("role", usuario.getRole().name())
                    .withClaim("id", usuario.getId())
                    .sign(algorithm);

            String token = TOKEN_PREFIX + " " + jwt;

            log.info("------------------------------- Token JWT gerado com sucesso para o usuário: {}-----------------------", usuario.getLogin());


            //return "{\"Authorization\": \"" + token + "\"}";

            return token;

        } catch (Exception e) {
            log.error("Erro ao gerar token JWT: {}", e.getMessage());
            throw new RuntimeException("Erro ao gerar token");
        }
    }


    private Instant gerarExpiracaoToken() {
        return LocalDateTime.now()
                .plusSeconds(EXPIRATION_TIME)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}