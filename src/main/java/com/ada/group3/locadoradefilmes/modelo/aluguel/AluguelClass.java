package com.ada.group3.locadoradefilmes.modelo.aluguel;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AluguelClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    //private Long idUser; Alterar para Join Collumn
    @Column(columnDefinition = "UUID default RANDOM_UUID()")
    private UUID uuid;
    private LocalDateTime Aluguel;
    private LocalDateTime Devolução;






}
