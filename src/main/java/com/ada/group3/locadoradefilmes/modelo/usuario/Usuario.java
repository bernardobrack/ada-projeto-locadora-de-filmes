package com.ada.group3.locadoradefilmes.modelo.usuario;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(columnDefinition = "UUID default RANDOM_UUID()")
    private UUID uuid;

    private String nome;
    private String CPF;
    private String senha;
    private String email;

    @Column(columnDefinition = "boolean default false")
    private Boolean temAtraso;

}
