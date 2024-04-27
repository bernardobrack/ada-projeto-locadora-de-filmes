package com.ada.group3.locadoradefilmes.modelo.filme;

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
public class FilmeReal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAlugado;

    @Column(columnDefinition = "uuid default random_uuid()")
     private UUID uuid;

  @ManyToOne
  @JoinColumn(
          name = "filme_conceito_id",
          nullable = false

  )
  private FilmeConceito filmeConceito;

  @OneToMany(
          mappedBy = "filme"
  )
  private List<Aluguel> alugueis;


}
