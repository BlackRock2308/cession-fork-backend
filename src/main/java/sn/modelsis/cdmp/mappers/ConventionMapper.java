package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;

@Mapper(componentModel = "spring")
public interface ConventionMapper extends GenericMapper<Convention, ConventionDto> {

    @Mapping(target = "dateConvention", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "valeurDecote", expression = "java(convention.getDecote().getDecoteValue())")
    @Mapping(target = "valeurDecoteByDG", expression = "java(convention.getValeurDecoteByDG())")
    @Mapping(target = "idDemande", expression = "java(convention.getDemandeCession().getIdDemande())")
   // @Mapping(target = "utilisatuerId", expression = "java(convention.getUtilisateur().getIdUtilisateur())")
    ConventionDto asDTO(Convention convention);

}
