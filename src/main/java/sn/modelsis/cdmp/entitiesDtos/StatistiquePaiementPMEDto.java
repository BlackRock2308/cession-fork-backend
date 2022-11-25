package sn.modelsis.cdmp.entitiesDtos;

import lombok.*;
import sn.modelsis.cdmp.util.ObjetMontantMois;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StatistiquePaiementPMEDto {
    int year;

    List<ObjetMontantMois> cumulSoldes = new ArrayList<>();

    List<ObjetMontantMois> cumulMontantCreance = new ArrayList<>();

    List<ObjetMontantMois> cumulDebourses = new ArrayList<>();
    List<ObjetMontantMois> cumulDecotes = new ArrayList<>();
}

