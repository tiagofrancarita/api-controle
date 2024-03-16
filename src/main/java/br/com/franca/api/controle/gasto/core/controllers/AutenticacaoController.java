package br.com.franca.api.controle.gasto.core.controllers;


import br.com.franca.api.controle.gasto.core.dtos.AuthDTO;
import br.com.franca.api.controle.gasto.core.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutenticacaoService autenticacaoService;



    @PostMapping("/logar")
    @ResponseStatus(HttpStatus.OK)
    public String logar(@RequestBody AuthDTO authDto){

        var usuarioAutenticationToken = new UsernamePasswordAuthenticationToken(authDto.login(), authDto.senha());
        authenticationManager.authenticate(usuarioAutenticationToken);

        return autenticacaoService.gerarToken(authDto);

    }
}
