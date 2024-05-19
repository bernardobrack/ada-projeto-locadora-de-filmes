package com.ada.group3.locadoradefilmes.modelo.filme;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class FilmeConceito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(columnDefinition = "uuid default random_uuid()", nullable = false)
    private UUID uuid;
    private String nome;
    private String genero;
    private String descricao;

    @OneToMany(
            mappedBy = "filmeConceito"
    )
    private List<FilmeReal> filmesReais;

    public FilmeConceito(long l, UUID uuid, String nomeAntigo, String descriçãoAntiga) {
    }
}
