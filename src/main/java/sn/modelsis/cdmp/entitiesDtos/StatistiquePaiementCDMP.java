package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class StatistiquePaiementCDMP {

    int year;

    List<ObjetMontantMois> cumulDecotes = new ArrayList<>();

    List<ObjetMontantMois> cumulSoldes = new ArrayList<>();

    List<ObjetMontantMois> cumulMontantCreance = new ArrayList<>();

    List<ObjetMontantMois> cmulRembourses = new ArrayList<>();
}
