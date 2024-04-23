package com.ada.group3.locadoradefilmes.modelo.filme;

import jakarta.persistence.*;
import lombok.*;

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


}
