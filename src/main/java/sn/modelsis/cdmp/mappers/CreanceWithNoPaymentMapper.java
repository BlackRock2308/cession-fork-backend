package sn.modelsis.cdmp.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sn.modelsis.cdmp.entitiesDtos.CreanceDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;

@Mapper(componentModel = "spring")
public interface CreanceWithNoPaymentMapper {

    @BeforeMapping
    default void beforeMapping(@MappingTarget CreanceDto target, DemandeCessionDto source) {
        if ( source.getConventions().isEmpty()){
            target.setDecote(0.0);
        }
        source.getConventions().forEach((e)->{
            if (e.isActiveConvention() == true){
                target.setDecote(e.getValeurDecoteByDG());
            }
        });
        if (source.getPaiement() == null){
            target.setSoldePME(0.0);
            target.setMontantDebourse(0.0);
        } else {
            target.setSoldePME(source.getPaiement().getSoldePME());
            target.setMontantDebourse(source.getPaiement().getMontantRecuCDMP());
        }
    }
    @Mapping(target = "idCreance", expression = "java(demandeCessionDto.getIdDemande())")
    @Mapping(target = "ninea", expression = "java(demandeCessionDto.getPme().getNinea())")
    @Mapping(target = "rccm", expression = "java(demandeCessionDto.getPme().getRccm())")
    @Mapping(target = "raisonSocial", expression = "java(demandeCessionDto.getPme().getRaisonSocial())")
    @Mapping(target = "typeMarche", expression = "java(demandeCessionDto.getBonEngagement().getTypeMarche())")
    @Mapping(target = "nomMarche", expression = "java(demandeCessionDto.getBonEngagement().getNomMarche())")
    @Mapping(target = "montantCreance", expression = "java(demandeCessionDto.getBonEngagement().getMontantCreance())")
    @Mapping(target = "dateDemandeCession", expression = "java(demandeCessionDto.getDateDemandeCession())")
    @Mapping(target = "dateMarche", expression = "java(demandeCessionDto.getDateDemandeCession())")
    @Mapping(target = "statut", expression = "java(demandeCessionDto.getStatut())")
    @Mapping(target = "decote", ignore = true)
    @Mapping(target = "soldePME", ignore = true)
    @Mapping(target = "montantDebourse", ignore = true)
    CreanceDto mapToDto(DemandeCessionDto demandeCessionDto);


}