package sn.modelsis.cdmp.Util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.*;

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

	public static  PmeDto convertToDto(Pme pme) {
		PmeDto pmeDto = null;
		if(null != pme) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			pmeDto = modelMapper.map(pme, PmeDto.class);
		}
		return pmeDto;
	}

	public static Pme convertToEntity(PmeDto pmeDto) {
		Pme pme = null;
		if(null != pmeDto) {
			pme = modelMapper.map(pmeDto, Pme.class);
		}
		return pme;
	}

	public static  BonEngagementDto convertToDto(BonEngagement bonEngagement) {
		BonEngagementDto bonEngagementDto = null;
		if(null != bonEngagement) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			bonEngagementDto = modelMapper.map(bonEngagement, BonEngagementDto.class);
		}
		return bonEngagementDto;
	}

	public static BonEngagement convertToEntity(BonEngagementDto bonEngagementDto) {
		BonEngagement bonEngagement = null;
		if(null != bonEngagementDto) {
			bonEngagement = modelMapper.map(bonEngagementDto, BonEngagement.class);
		}
		return bonEngagement;
	}

	public static  DemandeDto convertToDto(Demande demande) {
		DemandeDto demandeDto = null;
		if(null != demande) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			demandeDto = modelMapper.map(demande, DemandeDto.class);
		}
		return demandeDto;
	}

	public static Demande convertToEntity(DemandeDto demandeDto) {
		Demande demande = null;
		if(null != demandeDto) {
			demande = modelMapper.map(demandeDto, Demande.class);
		}
		return demande;
	}
	public static  StatutDto convertToDto(Statut statut) {
		StatutDto statutDto = null;
		if(null != statut) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			statutDto = modelMapper.map(statut, StatutDto.class);
		}
		return statutDto;
	}

	public static Statut convertToEntity(StatutDto statutDto) {
		Statut statut = new Statut();
		if(null != statutDto) {
			statut = modelMapper.map(statutDto, Statut.class);
		}
		return statut;
	}

	public static  DetailPaiementDto convertToDto(DetailPaiement detailPaiement) {
		DetailPaiementDto detailPaiementDto = null;
		if(null != detailPaiement) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			detailPaiementDto = modelMapper.map(detailPaiement, DetailPaiementDto.class);
		}
		return detailPaiementDto;
	}

	public static DetailPaiement convertToEntity(DetailPaiementDto detailPaiementDto) {
		DetailPaiement detailPaiement = new DetailPaiement();
		if(null != detailPaiementDto) {
			detailPaiement = modelMapper.map(detailPaiementDto, DetailPaiement.class);
		}
		return detailPaiement;
	}

	public static  PaiementDto convertToDto(Paiement paiement) {
		PaiementDto paiementDto = null;
		if(null != paiement) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			paiementDto = modelMapper.map(paiement, PaiementDto.class);
		}
		return paiementDto;
	}

	public static Paiement convertToEntity(PaiementDto paiementDto) {
		Paiement paiement = new Paiement();
		if(null != paiementDto) {
			paiement = modelMapper.map(paiementDto, Paiement.class);
		}
		return paiement;
	}

}
