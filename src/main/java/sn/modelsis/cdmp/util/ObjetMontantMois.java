package sn.modelsis.cdmp.util;



import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class ObjetMontantMois {
    double montant;
    LocalDateTime mois;

    public ObjetMontantMois(double montant, LocalDateTime mois){
        this.mois = mois;
        this.montant = montant;
    }
}
