package com.ada.group3.locadoradefilmes.modelo.filme;

import com.ada.group3.locadoradefilmes.modelo.aluguel.AluguelClass;
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
    private boolean isAlugado;

    @Column(columnDefinition = "UUID default RANDOM_UUID()")
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
  private List<AluguelClass> alugueis;


}
