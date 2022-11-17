package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;

@Mapper(componentModel = "spring")
public interface MyPmeMapper extends GenericMapper<PmeDto, Pme> {
}
