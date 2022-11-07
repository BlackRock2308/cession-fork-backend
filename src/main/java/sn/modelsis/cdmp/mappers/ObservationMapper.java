package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;

@Mapper(componentModel = "spring")
public interface ObservationMapper {

   // @Mapping(target = "statut", expression = "java(observation.getIdDemande.get().getStatut())")
//    @Mapping(target = "dateObservation", expression = "java(observation.getDateDemandeCession())")
//    @Mapping(target = "demandeCessionDto", expression = "java(observation.getDemandeCessionDto())")
    ObservationDto mapToDto(Observation observation);
}
