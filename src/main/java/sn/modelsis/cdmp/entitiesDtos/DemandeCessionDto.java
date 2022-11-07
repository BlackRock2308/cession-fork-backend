package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import sn.modelsis.cdmp.entities.*;

import javax.validation.constraints.NotBlank;
import java.util.*;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString @Getter @Setter
public class DemandeCessionDto {

    private Long idDemande;
    private Date dateDemandeCession;
    private StatutDto statut;
    private Set<ObservationDto> observations = new HashSet<>();
    private Set<DocumentDto> documents = new HashSet<>();
    private Set<ConventionDto> conventions = new HashSet<>();
    private PaiementDto paiementDto ;
    @NotBlank
    private BonEngagementDto bonEngagement;
    @NotBlank
    private PmeDto pme;
}
