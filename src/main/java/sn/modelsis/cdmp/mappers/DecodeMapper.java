package sn.modelsis.cdmp.mappers;

import org.mapstruct.Mapper;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

@Mapper(componentModel = "spring")
public interface DecodeMapper extends GenericMapper<ParametrageDecote, ParametrageDecoteDTO>{
}
