package sn.modelsis.cdmp.entitiesDtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import sn.modelsis.cdmp.entities.TypeMarche;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@SuperBuilder
@Data
@NoArgsConstructor
@ToString
public class BonEngagementDto implements Serializable {
    private Long idBonEngagement;
    private double montantCreance;
    @Enumerated( EnumType.STRING)
    private TypeMarche typeMarche;
    private String reference;
    private String naturePrestation;
    private String natureDepense;
    private String objetDepense;
    private String imputation;
    private String nomMarche;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateBonEngagement;
    private String identificationComptable;
    private String typeDepense;
    private String modeReglement;
    private String exercice;
    private String designationBeneficiaire;
    private String destinationAction;
    private String destinationActivite;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateSoumissionServiceDepensier;
    private Set<DocumentDto> documents = new HashSet<>();


}
