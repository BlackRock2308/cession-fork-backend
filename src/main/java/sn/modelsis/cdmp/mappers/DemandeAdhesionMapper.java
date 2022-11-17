package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;

@Mapper(componentModel = "spring")
public interface DemandeAdhesionMapper extends GenericMapper<DemandeAdhesion,DemandeAdhesionDto> {
}
