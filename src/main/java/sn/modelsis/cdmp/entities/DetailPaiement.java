package sn.modelsis.cdmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "detailPaiement")
@Getter
@Setter
@NoArgsConstructor
public class DetailPaiement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "modePaiement" ,nullable = true)
    private ModePaiement modePaiement;



    @Column(name = "datePaiement")
    private String datePaiement;

    @Column(name = "comptable")
    private String comptable;

    @Column(name = "montant")
    private Long montant;

    @Column(name = "reference")
    private String reference;

    @Column(name = "type")
    private String type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpaiement", nullable = true)
    private Paiement paiement;



}
