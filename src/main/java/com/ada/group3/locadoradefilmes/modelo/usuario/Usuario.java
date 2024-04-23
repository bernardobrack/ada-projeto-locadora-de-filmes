package com.ada.group3.locadoradefilmes.modelo.usuario;

import com.ada.group3.locadoradefilmes.modelo.aluguel.Aluguel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    @Column(unique = true)
    private String login;
    private String senha;
    @Column(columnDefinition = "boolean default true")
    private Boolean ativo;

    @Column(columnDefinition = "boolean default false")
    private Boolean temAtraso;

    @OneToMany(
            mappedBy = "usuario"
    )
    private List<Aluguel> alugueis;

}
