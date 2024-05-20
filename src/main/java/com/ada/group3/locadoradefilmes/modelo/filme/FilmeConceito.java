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
    private Long id;
    @Column(columnDefinition = "uuid default random_uuid()", nullable = false)
    private UUID uuid;
    private String nome;
    private String genero;
    private String descricao;

    @OneToMany(mappedBy = "filmeConceito")
    private List<FilmeReal> filmesReais;

    public FilmeConceito(Long id, UUID uuid, String nome, String genero, String descricao) {
        this.id = id;
        this.uuid = uuid;
        this.nome = nome;
        this.genero = genero;
        this.descricao = descricao;
    }

    public FilmeConceito(Long id, UUID uuid, String nome, String descricao) {
        this.id = id;
        this.uuid = uuid;
        this.nome = nome;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "FilmeConceito{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
