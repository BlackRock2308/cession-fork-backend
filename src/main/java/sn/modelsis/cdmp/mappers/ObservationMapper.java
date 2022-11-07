package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;

@Mapper(componentModel = "spring")
public interface ObservationMapper {

    @Mapping(target = "dateObservation", expression = "java(java.time.LocalDateTime.now())")
    Observation mapToDto(ObservationDto observation);
}