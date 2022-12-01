package sn.modelsis.cdmp.entitiesDtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateImmatriculation;
    private String centreFiscal;
    @NotEmpty
    private String ninea;
    private String raisonSocial;
    private boolean atd;
    private boolean nantissement;
    private boolean hasninea;
    private boolean isactive;
    private boolean interdictionBancaire;
    private boolean identificationBudgetaire;
    private String formeJuridique;
    @NotEmpty
    @Email
    private String email;
    private Set<DocumentDto> documents ;

    private String  enseigne ;
    private String cniRepresentant ;
    private String localite;
    private int controle;
    private String activitePrincipale;
    private String autorisationMinisterielle;
    private String registre;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateCreation;
    private Long capitalsocial;
    private int effectifPermanent;
    private Long chiffresDaffaires;
    private int nombreEtablissementSecondaires;
    private Long utilisateurid;
 

}

