package sn.modelsis.cdmp.entitiesDtos;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AnalyseRisqueDto {

    private String nomMarche;
    private String raisonSocial;
    private String reference;
    private Date dateDemandeCession;
    private String statut;



}
