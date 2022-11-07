package sn.modelsis.cdmp.entitiesDtos;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.Statuts;

@Data
@NoArgsConstructor
@ToString
public class PaiementDto {

    private Long idPaiement;

    private double montantRecuCDMP;

    private double soldePME;

    private Long demandeId;

    private String raisonSocial;

    private String nomMarche;

    private double montantCreance;

    private StatutDto statutDto;

    private Long demandecessionid;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    //private LocalDateTime datePaiement;

}
