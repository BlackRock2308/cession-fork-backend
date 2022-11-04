package sn.modelsis.cdmp.entitiesDtos;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DemandeDto {
    private Long idDemande;
    private PmeDto pme;
    private String numeroDemande;
    private StatutDto statut;
    private BonEngagementDto bonEngagement;
    private ConventionDto convention;
    private Set<DocumentDto> documents = new HashSet<>();
}
