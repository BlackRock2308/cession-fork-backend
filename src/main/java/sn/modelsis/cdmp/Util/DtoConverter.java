package sn.modelsis.cdmp.Util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import sn.modelsis.cdmp.entities.Agent;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entities.Parametrage;
import sn.modelsis.cdmp.entitiesDtos.AgentDto;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDto;

public class DtoConverter {

	protected static final ModelMapper modelMapper = new ModelMapper();

	protected DtoConverter() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	}

	public static AgentDto convertToDto(Agent agent) {
		AgentDto agentDto = null;
		if (null != agent) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			agentDto = modelMapper.map(agent, AgentDto.class);
		}
		return agentDto;
	}

	public static Agent convertToEntity(AgentDto agentDto) {
		Agent agent = null;
		if (null != agentDto) {
			agent = modelMapper.map(agentDto, Agent.class);
		}
		return agent;
	}
	
	public static ConventionDto convertToDto(Convention convention) {
	  ConventionDto conventionDto = null;
      if (null != convention) {
          modelMapper.getConfiguration().setAmbiguityIgnored(true);
          conventionDto = modelMapper.map(convention, ConventionDto.class);
      }
      return conventionDto;
  }

  public static Convention convertToEntity(ConventionDto conventionDto) {
    Convention convention = null;
      if (null != conventionDto) {
        convention = modelMapper.map(conventionDto, Convention.class);
      }
      return convention;
  }

	public static ParametrageDto convertToDto(Parametrage parametrage) {
		ParametrageDto parametrageDto = null;
		if (null != parametrage) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			parametrage = modelMapper.map(parametrage, Parametrage.class);
		}
		return parametrageDto;
	}

	public static Parametrage convertToEntity(ParametrageDto parametrageDto) {
		Parametrage parametrage = null;
		if (null != parametrageDto) {
			parametrage = modelMapper.map(parametrageDto, Parametrage.class);
		}
		return parametrage;
	}

	public static ObservationDto convertToDto(Observation observation) {
		ObservationDto observationDto = null;
		if (null != observation) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			observationDto = modelMapper.map(observation, ObservationDto.class);
		}
		return observationDto;
	}

	public static Observation convertToEntity(ObservationDto observationDto) {
		Observation observation = null;
		if (null != observationDto) {
			observation = modelMapper.map(observationDto, Observation.class);
		}
		return observation;
	}

}
