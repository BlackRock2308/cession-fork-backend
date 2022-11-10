package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class StatistiqueDemandeCession {
   Integer nombreDemandeAccepte;
   Integer nombreDemandeRejete;
   Date mois;
}
