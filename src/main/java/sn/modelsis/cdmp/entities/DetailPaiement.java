package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "detailsPaiement")
@Getter
@Setter
@NoArgsConstructor
public class DetailPaiement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "modePaiement")
    private ModePaiement modePaiement;

    @Column(name = "datePaiement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime datePaiement;

    @Column(name = "comptable")
    private String comptable;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "reference")
    private String reference;
//
//    @Column(name = "type")
//    private String type;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "idpaiement", nullable = true)
//    private Paiement paiement;



}
