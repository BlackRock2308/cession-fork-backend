package sn.modelsis.cdmp.entitiesDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmeDto {

    private Long idPME;
    private String prenomRepresentant;
    private String nomRepresentant;
    @NotNull @NotBlank
    private String rccm;
    private String adressePME;
    private String telephonePME;
    private Date dateImmatriculation;
    private String centreFiscal;
    @NotEmpty
    private String ninea;
    private String raisonSocial;
    private String atd;
    private String nantissement;
    private String interdictionBancaire;
    private String formeJuridique;
    @NotEmpty
    @Email
    private String email;
    private  int codePin;
    private String urlImageProfile;
    private String urlImageSignature;
    //private Set<DemandeDto> demandes;
    private Set<DocumentDto> documents = new HashSet<>();

}

