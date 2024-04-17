package br.com.franca.api.controle.gasto.core.controllers;

import br.com.franca.api.controle.gasto.core.entites.Usuario;
import br.com.franca.api.controle.gasto.core.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Classe responsável por controlar as requisições de usuários
 * @autor Tiago Franca
 * @since 1.0
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200") // Substitua "http://localhost:4200" pelo URL do seu frontend Angular
@RestController
@RequestMapping("v1/usuarios")
public class UsuariosControllers {

    private Logger log = LoggerFactory.getLogger(UsuariosControllers.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/salvarUsuario")
    private ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) throws Exception {

        usuarioService.salvarUsuario(usuario);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("/listarUsuarios")
    private List<Usuario> listarUsuarios() throws Exception {

        log.info("Listando usuários");

        return usuarioService.listarUsuarios();

    }

}
