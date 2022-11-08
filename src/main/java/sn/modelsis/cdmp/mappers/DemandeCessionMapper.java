package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;

@Mapper(componentModel = "spring")
public interface DemandeCessionMapper  {
    DemandeCessionDto asDTO(DemandeCession demandeCession);
}
