package sn.modelsis.cdmp.entitiesDtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString @Getter @Setter
public class DemandeAdhesionDto {

    private Long idDemande;
    private Long idPME;
    private Date dateDemandeAdhesion;
    private StatutDto statut;
    private Set<DocumentDto> documents = new HashSet<>();
    private PmeDto pme;


}
