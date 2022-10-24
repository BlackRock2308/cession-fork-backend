package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private String nomMarche;
    private Date dateBonEngagement;
    private String identificationComptable;
    private Set<DocumentDto> documents = new HashSet<>();
}
