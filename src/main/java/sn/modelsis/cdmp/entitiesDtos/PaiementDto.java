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

    private double montantCreanceInitial;

    private StatutDto statutCDMP;

    private StatutDto statutPme;

//    private DemandeCessionDto demandeCession;

    private Long demandecessionid;

    private Set<DetailPaiementDto> detailPaiements = new HashSet<>();

    public double getMontantRecuCDMP() {
        return montantRecuCDMP;
    }

    public void setMontantRecuCDMP(double montantRecuCDMP) {
        this.montantRecuCDMP = montantRecuCDMP;
    }
}
