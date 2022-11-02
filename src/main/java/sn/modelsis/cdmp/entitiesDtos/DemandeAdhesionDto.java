package sn.modelsis.cdmp.entitiesDtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DemandeAdhesionDto {

    private Long id;
    private String ninea;
    private String rccm;
    private Date dateDemandeAdhesion;
    private StatutDto statut;
    private Set<DocumentDto> documents = new HashSet<>();

    private Long pmeId;
    //private Set<ObservationDto> observations = new HashSet<>();
    //

}
