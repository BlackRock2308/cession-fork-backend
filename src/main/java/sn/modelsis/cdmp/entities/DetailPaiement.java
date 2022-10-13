package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "detailspaiement")
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
    private int montant;

    @Column(name = "reference")
    private String reference;
//
//    @Column(name = "type")
//    private String type;

    @Column(name = "typepaiement")
    private String typepaiement;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paiementid", nullable = true)
    private Paiement paiement;



}
