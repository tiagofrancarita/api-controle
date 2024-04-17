package br.com.franca.api.controle.gasto.core.controllers;


import br.com.franca.api.controle.gasto.core.dtos.AuthDTO;
import br.com.franca.api.controle.gasto.core.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("v1/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutenticacaoService autenticacaoService;



    @PostMapping("/logar")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<?> logar(@RequestBody AuthDTO authDto){

        var usuarioAutenticationToken = new UsernamePasswordAuthenticationToken(authDto.login(), authDto.senha());
        try {
            authenticationManager.authenticate(usuarioAutenticationToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "Usuário ou senha inválidos"));
        }

        return ResponseEntity.ok(Map.of("token", autenticacaoService.obterToken(authDto)));

    }
}