package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
