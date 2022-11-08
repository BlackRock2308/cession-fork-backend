package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;

@Mapper(componentModel = "spring")
public interface CreanceMapper {

    @Mapping(target = "idCreance", expression = "java(demandeCessionDto.getIdDemande())")
    @Mapping(target = "ninea", expression = "java(demandeCessionDto.getPme().getNinea())")
    @Mapping(target = "rccm", expression = "java(demandeCessionDto.getPme().getRccm())")
    @Mapping(target = "raisonSocial", expression = "java(demandeCessionDto.getPme().getRaisonSocial())")
    @Mapping(target = "typeMarche", expression = "java(demandeCessionDto.getBonEngagement().getTypeMarche())")
    @Mapping(target = "nomMarche", expression = "java(demandeCessionDto.getBonEngagement().getNomMarche())")
    @Mapping(target = "montantCreance", expression = "java(demandeCessionDto.getBonEngagement().getMontantCreance())")
    @Mapping(target = "dateDemandeCession", expression = "java(demandeCessionDto.getDateDemandeCession())")
    //@Mapping(target = "decote", expression = "java(demandeCessionDto.getConventions().getDecote())")
    //@Mapping(target = "dateMarche", expression = "java(java(java.time.LocalDateTime.now()))")
    //@Mapping(target = "soldePME", expression = "java(demandeCessionDto.getPaiementDto().getSoldePME())")
    CreanceDto mapToDto(DemandeCessionDto demandeCessionDto);
}
