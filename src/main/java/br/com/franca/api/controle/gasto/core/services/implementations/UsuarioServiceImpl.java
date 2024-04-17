package br.com.franca.api.controle.gasto.core.services.implementations;

import br.com.franca.api.controle.gasto.core.entites.Usuario;
import br.com.franca.api.controle.gasto.core.enums.StatusEnum;
import br.com.franca.api.controle.gasto.core.repositories.UsuarioRepository;
import br.com.franca.api.controle.gasto.core.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;
import java.util.List;

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
     * @param usuario
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) throws Exception {
        log.info("-----------------Cadastro de usuários-----------------");
        log.info("-----------------Iniciando o processo de cadastro de usuário-----------------");

        // Validar login e e-mail
        validarUsuario(usuario);

        log.info("-----------------Validação dos dados realizada com sucesso-----------------");

        log.info("-----------------Criptografando a senha-----------------");
        criptografarSenha(usuario);

        configurarDadosUsuario(usuario);

        log.info("-----------------Salvando usuário-----------------");

        usuario = usuarioRepository.save(usuario);

        if (usuario.getId() != null) {
            log.info("-----------------Usuario Cadastrado-----------------");
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } else {
            log.error("-----------------Erro ao cadastrar usuário-----------------");
            throw new Exception("-----------------Erro ao cadastrar usuário-----------------");
        }
    }

    private void validarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            log.error("-----------------Usuário nulo-----------------");
            throw new Exception("-----------------Usuário nulo-----------------");
        }

        if (usuario.getId() == null && usuarioRepository.existeLogin(usuario.getLogin()) != null) {
            log.error("-----------------Usuário já cadastrado com esse login, LOGIN: " + usuario.getLogin() + "-----------------");
            throw new Exception("-----------------Usuário já cadastrado com esse login, LOGIN: " + usuario.getLogin() + "-----------------");
        }

        if (usuario.getId() == null && usuarioRepository.existeEmail(usuario.getEmail()) != null) {
            log.error("-----------------Usuário já cadastrado com esse e-mail, E-MAIL: " + usuario.getEmail() + "-----------------");
            throw new Exception("-----------------Usuário já cadastrado com esse e-mail, E-MAIL: " + usuario.getEmail() + "-----------------");
        }
    }

    private void criptografarSenha(Usuario usuario) {
        var senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        log.info("-----------------Senha criptografada----------------- SENHA: " + usuario.getSenha());
    }

    private void configurarDadosUsuario(Usuario usuario) {

        usuario.setDataCadastro(LocalDateTime.now());
        LocalDateTime dataExpiracaoSenha = usuario.getDataCadastro().plusDays(90);
        usuario.setDataExpiracaoSenha(dataExpiracaoSenha);

        usuario.setNumeroTentativas(0);
        usuario.setStatus(StatusEnum.A);
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
        System.out.println(usuarios);

        if (usuarios.isEmpty()) {
            log.error("-----------------Nenhum usuário cadastrado-----------------");
            throw new RuntimeException("Nenhum usuário cadastrado");
        }

        return usuarios;
    }
}