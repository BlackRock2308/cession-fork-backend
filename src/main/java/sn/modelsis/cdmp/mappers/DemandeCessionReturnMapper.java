package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.NewDemandeCessionDto;

@Mapper(componentModel = "spring")
public interface DemandeCessionReturnMapper extends GenericMapper<DemandeCession, NewDemandeCessionDto>  {
    //DemandeCessionDto asDTO(DemandeCession demandeCession);
}
