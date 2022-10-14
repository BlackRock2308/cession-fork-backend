package sn.modelsis.cdmp.entitiesDtos;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.Paiement;

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

    private String typepaiement;

    private Paiement paiement;


}
