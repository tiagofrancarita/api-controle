package br.com.franca.api.controle.gasto.core.services.implementations;

import br.com.franca.api.controle.gasto.core.dtos.UsuariosDto;
import br.com.franca.api.controle.gasto.core.entites.Usuario;
import br.com.franca.api.controle.gasto.core.enums.StatusEnum;
import br.com.franca.api.controle.gasto.core.repositories.UsuarioRepository;
import br.com.franca.api.controle.gasto.core.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Classe de implementação dos métodos de serviço de usuário
 * implementa a interface UsuarioService
 */

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método que salva um usuário
     * @param usuariosDto
     * @return
     * @throws Exception
     */
    @Override
    public UsuariosDto salvarUsuario(UsuariosDto usuariosDto) throws Exception {
        try {
            log.info("-----------------Cadastro de usuários-----------------");
            log.info("-----------------Iniciando o processo de cadastro de usuário-----------------");

            // Validar login e e-mail
            if (loginExistente(usuariosDto.login())) {
                log.error("----------------- Login já existente -----------------");
                throw new Exception("----------------- Login já existente -----------------");
            }

            if (emailExistente(usuariosDto.email())) {
                log.error("----------------- E-mail já existente -----------------");
                throw new Exception("----------------- E-mail já existente -----------------");
            }

            log.info("-----------------Validação dos dados realizada com sucesso-----------------");

            var senhaCriptografada = passwordEncoder.encode(usuariosDto.senha());

            Usuario usuarioEntity = criarUsuarioAPartirDto(usuariosDto);
            usuarioEntity.setSenha(senhaCriptografada);
            LocalDateTime dataExpiracaoSenha = usuarioEntity.getDataCadastro().plusDays(90);
            usuarioEntity.setDataExpiracaoSenha(dataExpiracaoSenha);
            usuarioEntity.setNumeroTentativas(0);
            usuarioEntity.setStatus(StatusEnum.A);
            usuarioEntity.setDataCadastro(LocalDateTime.now());

            // Salvar o usuário no banco de dados
            usuarioEntity = usuarioRepository.save(usuarioEntity);

            // Inclua a senha criptografada na resposta
            return new UsuariosDto(
                    usuarioEntity.getId(),
                    usuarioEntity.getNome(),
                    usuarioEntity.getEmail(),
                    usuarioEntity.getLogin(),
                    senhaCriptografada, // Incluindo a senha criptografada na resposta
                    usuarioEntity.getConfirmaSenha(),
                    usuarioEntity.getStatus(),
                    usuarioEntity.getDataCadastro(),
                    usuarioEntity.getDataBloqueio(),
                    usuarioEntity.getDataDesbloqueio(),
                    usuarioEntity.getDataInativacao(),
                    usuarioEntity.getDataReativacao(),
                    usuarioEntity.getDataExpiracaoSenha(),
                    usuarioEntity.getNumeroTentativas(),
                    usuarioEntity.getRole()
            );

        } catch (Exception e) {
            log.error("-----------------Erro ao cadastrar usuário-----------------", e);
            throw new Exception("Erro ao cadastrar usuário");
        }
    }

    /**
     * Método que lista todos usuários cadastrados
     * @return List<Usuario>
     * @throws Exception
     */
    @Override
    public List<Usuario> listarUsuarios() throws Exception {
        log.info("-----------------Iniciando a Listagem de usuários-----------------");

        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            log.error("-----------------Nenhum usuário cadastrado-----------------");
            throw new RuntimeException("Nenhum usuário cadastrado");
        }

        return usuarios;
    }

    /**
     * Método que valida se um login ja existe
     * @param login
     * @return Usuario
     * @throws Exception
     */
    private boolean loginExistente(String login) {
        Optional<Usuario> usuario = usuarioRepository.validaLogin(login);
        return usuario.isPresent();
    }

    /**
     * Método que valida se um e-mail ja existe
     * @param login
     * @return Usuario
     * @throws Exception
     */
    private boolean emailExistente(String login) {
        Optional<Usuario> usuario = usuarioRepository.validaEmail(login);
        return usuario.isPresent();
    }

    /**
     * Método que cria um objeto Usuario a partir de um DTO
     * @param usuariosDto
     * @return Usuario
     */

    private Usuario criarUsuarioAPartirDto(UsuariosDto usuariosDto) {
        return new Usuario(
                usuariosDto.id(),
                usuariosDto.nome(),
                usuariosDto.email(),
                usuariosDto.login(),
                usuariosDto.senha(),
                usuariosDto.confirmaSenha(),
                usuariosDto.status(),
                usuariosDto.dataCadastro(),
                usuariosDto.dataBloqueio(),
                usuariosDto.dataDesbloqueio(),
                usuariosDto.dataInativacao(),
                usuariosDto.dataReativacao(),
                usuariosDto.dataExpiracaoSenha(),
                usuariosDto.numeroTentativas(),
                usuariosDto.role()

        );
    }
}