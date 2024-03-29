package sn.modelsis.cdmp.entitiesDtos;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DetailPaiementDto {


    private Long id;

    private String modePaiement;

    private LocalDateTime datePaiement;

    private String comptable;

    private Long montant;

    private String reference;

    private Long idDemande;

    private String typepaiement;

    private PaiementDto paiementDto;
    
    private Set<DocumentDto> documents = new HashSet<>();


}
