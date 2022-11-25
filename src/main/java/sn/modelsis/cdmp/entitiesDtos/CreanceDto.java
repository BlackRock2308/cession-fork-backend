package sn.modelsis.cdmp.entitiesDtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.TypeMarche;

@Data
@NoArgsConstructor
@ToString
public class CreanceDto implements Serializable {

    private Long idCreance;

    private String ninea; //PME

    private String rccm; //PME

    private String raisonSocial; //Pme

    private TypeMarche typeMarche; //Bon engagement

    private String nomMarche; //Bon engagement

    private double montantCreance; //Bon engagement

    private Double decote; //Convention

    private double soldePME; //Paiement

    private StatutDto statut; //DemandeCession

    private LocalDateTime dateDemandeCession; //DemandeCession

    private LocalDateTime dateMarche; //DemandeCession

    private double montantDebourse;
    
    private double montantRembourse;

    private double soldeSICA;





}
