package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import sn.modelsis.cdmp.entities.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class DemandeAdhesionDto {

    private Long idDemande;
    private Long idPME;
    private Date dateDemandeAdhesion;
    private StatutDto statut;
    private Set<DocumentDto> documents = new HashSet<>();
    private PmeDto pme;

    //    @NotBlank
//    private String ninea;
//    @NotBlank
//    private String rccm;


}
