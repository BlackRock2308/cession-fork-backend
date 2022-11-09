package sn.modelsis.cdmp.entitiesDtos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmeDto implements Serializable {

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
    private boolean atd;
    private boolean nantissement;
    private boolean interdictionBancaire;
    private boolean identificationBudgetaire;
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

