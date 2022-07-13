package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class BonEngagementDto {
    private Long id;
    private double montantCreance;
    private String reference;
    private String naturePrestation;
    private String natureDepense;
    private String objetDepense;
    private String imputation;
    private Date dateBonEngagement;
    private String identificationComptable;
}
