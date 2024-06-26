package com.ada.group3.locadoradefilmes.modelo.aluguel;

import com.ada.group3.locadoradefilmes.modelo.filme.FilmeReal;
import com.ada.group3.locadoradefilmes.modelo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "Aluguel")
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(columnDefinition = "UUID default RANDOM_UUID()")
    private UUID uuid;
    private LocalDateTime horarioAluguel;
    private LocalDateTime horarioDevolucao;

    @ManyToOne
    @JoinColumn(
            name = "usuario_id",
            nullable = false
    )
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(
            name = "filme_concreto_id",
            nullable = false
    )
    private FilmeReal filme;

}
