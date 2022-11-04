package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.Statuts;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CreanceDto {

    private Long idCreance;

    private String nomMarche; //Bon engagement

    private String raisonSocial; //Pme

    //private double soldePME; //Paiement

    private double montantCreance; //Bon engagement

    private Statut statut; //DemandeCession

    private String rccm; //PME

    private Date dateDemandeCession; //DemandeCession




}
