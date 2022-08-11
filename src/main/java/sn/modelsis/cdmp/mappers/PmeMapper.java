package sn.modelsis.cdmp.mappers;

import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;

public interface PmeMapper {
    PmeDto pmeToPmeDto(Pme pme);
    Pme pmeDtoToPme(PmeDto pmeDto);
}


