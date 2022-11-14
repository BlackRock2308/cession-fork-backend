package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import sn.modelsis.cdmp.util.ObjetMontantMois;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StatistiquePaiementCDMPDto {

    int year;

    List<ObjetMontantMois> cumulDecotes = new ArrayList<>();

    List<ObjetMontantMois> cumulSoldes = new ArrayList<>();

    List<ObjetMontantMois> cumulMontantCreance = new ArrayList<>();

    List<ObjetMontantMois> cmulRembourses = new ArrayList<>();
}
