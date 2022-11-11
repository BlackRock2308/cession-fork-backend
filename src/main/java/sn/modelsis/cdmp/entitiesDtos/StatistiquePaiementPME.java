package sn.modelsis.cdmp.entitiesDtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class StatistiquePaiementPME {
    int year;

    List<ObjetMontantMois> cumulSoldes = new ArrayList<>();

    List<ObjetMontantMois> cumulMontantCreance = new ArrayList<>();

    List<ObjetMontantMois> cmulDebourses = new ArrayList<>();
}

