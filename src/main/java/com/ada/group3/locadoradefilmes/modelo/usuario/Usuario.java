package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.modelo.aluguel.Aluguel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String lastName;
    @CPF
    private String cpf;
    @Email
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;
    @Column(columnDefinition = "boolean default false")
    private Boolean isLate;
    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired;
    @Column(columnDefinition = "boolean default false")
    private boolean accountLocked;
    @Column(columnDefinition = "boolean default false")
    private boolean credentialsExpired;
    @OneToMany(
            mappedBy = "usuario"
    )
    private List<Aluguel> alugueis;
    public enum Role {
        ADMIN,CLIENTE
    }

    public Usuario() {
        alugueis = new ArrayList<>();
        active = true;
        isLate = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name())));
    }


    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
