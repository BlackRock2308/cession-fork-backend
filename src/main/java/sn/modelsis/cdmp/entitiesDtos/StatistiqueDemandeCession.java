package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@ToString
public class StatistiqueDemandeCession {
   Integer nombreDemandeAccepte;
   Integer nombreDemandeRejete;
   LocalDate mois;
}
