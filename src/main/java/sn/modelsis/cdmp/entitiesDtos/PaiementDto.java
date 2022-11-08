package sn.modelsis.cdmp.entitiesDtos;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
public class PaiementDto implements Serializable {

    private Long cdmp;

    private Long idPaiement;

    private double montantRecuCDMP;

    private double soldePME;

    private Long demandeId;

    private String raisonSocial;

    private String nomMarche;

    private double montantCreance;

    private StatutDto statutDto;

//    private DemandeCessionDto demandeCession;

    private Long demandecessionid;

    private Set<DetailPaiementDto> detailPaiements = new HashSet<>();
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    //private LocalDateTime datePaiement;

}
