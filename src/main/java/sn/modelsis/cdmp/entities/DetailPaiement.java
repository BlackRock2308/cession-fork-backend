package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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


    @Column(name = "modepaiement" ,nullable = true)
    private String modePaiement;



    @Column(name = "datepaiement")
    private LocalDateTime datePaiement;

    @Column(name = "comptable")
    private String comptable;

    @Column(name = "montant")
    private Long montant;

    @Column(name = "reference")
    private String reference;

    @Column(name = "typepaiement")
    private String typepaiement;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paiementid", nullable = true)
    private Paiement paiement;



}
