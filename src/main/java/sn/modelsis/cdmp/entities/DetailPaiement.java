package sn.modelsis.cdmp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "modePaiement")
    private ModePaiement modePaiement;

    @Column(name = "datePaiement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime datePaiement;

    @Column(name = "comptable")
    private String comptable;

    @Column(name = "montant",nullable = false)
    private double montant;

    @Column(name = "reference")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "typepaiement")
    private TypePaiement typepaiement;

    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "dp")
    private Set<DPaiementDocuments> documents = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="paiementId")
    private Paiement paiement;



}
