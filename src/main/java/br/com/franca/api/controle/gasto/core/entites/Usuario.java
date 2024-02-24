package br.com.franca.api.controle.gasto.core.entites;

import br.com.franca.api.controle.gasto.core.enums.RoleEnum;
import br.com.franca.api.controle.gasto.core.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entidade que representa a tabela de usuários no banco de dados
 */


@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(255)")
    private String nome;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(255)", unique = true)
    private String email;

    @Column(name = "login", nullable = false, columnDefinition = "VARCHAR(255)", unique = true)
    private String login;

    @Column(name = "senha", nullable = false, columnDefinition = "VARCHAR(255)")
    private String senha;

    @Column(name = "confirma_senha", nullable = false, columnDefinition = "VARCHAR(255)")
    private String confirmaSenha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "CHAR(1)")
    private StatusEnum status = StatusEnum.ATIVO;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_bloqueio", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataBloqueio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_desbloqueio", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataDesbloqueio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inativacao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataInativacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_reativacao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataReativacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_expiracao_senha", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataExpiracaoSenha = dataCadastro.plusDays(90);

    @Column(name = "numero_tentativas", nullable = false, columnDefinition = "INT")
    private Integer numeroTentativas = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(255)")
    private RoleEnum role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == RoleEnum.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}