package sn.modelsis.cdmp.util;

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

	public static DemandeCession convertToEntity(DemandeCessionDto demandecessionDto) {
		DemandeCession demandecession = null;
		if(null != demandecessionDto) {
			demandecession = modelMapper.map(demandecessionDto, DemandeCession.class);
		}
		return demandecession;
	}

	public static  DemandeCessionDto convertToDto(DemandeCession demandecession) {
		DemandeCessionDto demandecessionDto = null;
		if(null != demandecession) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			demandecessionDto = modelMapper.map(demandecession, DemandeCessionDto.class);
		}
		return demandecessionDto;
	}

	public static DemandeAdhesion convertToEntity(DemandeAdhesionDto demandeadhesionDto) {
		DemandeAdhesion demandeadhesion = null;
		if(null != demandeadhesionDto) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			demandeadhesion = modelMapper.map(demandeadhesionDto, DemandeAdhesion.class);
			//convert les attributs ambigues
			Pme pme=demandeadhesion.getPme();
			pme.setNinea(demandeadhesionDto.getNinea());
			pme.setRccm(demandeadhesionDto.getRccm());
			demandeadhesion.setPme(pme);
		}
		return demandeadhesion;
	}

	public static  DemandeAdhesionDto convertToDto(DemandeAdhesion demandeadhesion) {
		DemandeAdhesionDto demandeadhesionDto = null;
		if(null != demandeadhesion) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			demandeadhesionDto = modelMapper.map(demandeadhesion, DemandeAdhesionDto.class);
			//convert les attributs ambigues
			demandeadhesionDto.setNinea(demandeadhesion.getPme().getNinea());
			demandeadhesionDto.setRccm(demandeadhesion.getPme().getRccm());
		}
		return demandeadhesionDto;
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
	
	public static DocumentDto convertToDto(Documents document) {
	    DocumentDto documentDto = null;
	    if (null != document) {
	      modelMapper.getConfiguration().setAmbiguityIgnored(true);
	      documentDto = modelMapper.map(document, DocumentDto.class);
	    }
	    return documentDto;
	  }

	  public static Documents convertToEntity(DocumentDto documentDto) {
	    Documents document = null;
	    if (null != documentDto) {
	      document = modelMapper.map(documentDto, Documents.class);
	    }
	    return document;
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
			//renseigner les mapping ambigue
			paiementDto.setDemandeId(paiement.getDemandeCession().getIdDemande());
			paiementDto.setMontantCreance(paiement.getDemandeCession().getBonEngagement().getMontantCreance());
			paiementDto.setStatutLibelle(paiement.getDemandeCession().getStatut().getLibelle());
			paiementDto.setRaisonSocial(paiement.getDemandeCession().getPme().getRaisonSocial());
		}
		return paiementDto;
	}

	public static Paiement convertToEntity(PaiementDto paiementDto) {
		Paiement paiement = new Paiement();
		if(null != paiementDto) {
			//modelMapper.getConfiguration().setAmbiguityIgnored(true);
			paiement = modelMapper.map(paiementDto, Paiement.class);
			//renseigner les mapping ambigue

		}
		return paiement;
	}

	public static  RoleUtilisateurDto convertToDto(RoleUtilisateur roleUtilisateur) {
		RoleUtilisateurDto roleUtilisateurDto = null;
		if(null != roleUtilisateur) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			roleUtilisateurDto = modelMapper.map(roleUtilisateur, RoleUtilisateurDto.class);
		}
		return roleUtilisateurDto;
	}

	public static RoleUtilisateur convertToEntity(RoleUtilisateurDto roleUtilisateurDto) {
		RoleUtilisateur roleUtilisateur = new RoleUtilisateur();
		if(null != roleUtilisateurDto) {
			roleUtilisateur = modelMapper.map(roleUtilisateurDto, RoleUtilisateur.class);
		}
		return roleUtilisateur;
	}

	public static  RecevabiliteDto convertToRecevabiliteDto(DemandeCession demandeCession) {
		RecevabiliteDto recevabiliteDto = null;
		if(null != recevabiliteDto) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			recevabiliteDto = modelMapper.map(demandeCession, RecevabiliteDto.class);

			recevabiliteDto.setNinea(demandeCession.getPme().getNinea());
			recevabiliteDto.setRccm(demandeCession.getPme().getRccm());
			recevabiliteDto.setNomMarche(demandeCession.getBonEngagement().getNomMarche());
			recevabiliteDto.setRaisonSociale(demandeCession.getPme().getRaisonSocial());
		}
		return recevabiliteDto;
	}

	public static DemandeCession convertToEntity(RecevabiliteDto recevabiliteDto) {
		DemandeCession demandeCession = new DemandeCession();
		if(null != recevabiliteDto) {
			//modelMapper.getConfiguration().setAmbiguityIgnored(true);
			demandeCession = modelMapper.map(recevabiliteDto, DemandeCession.class);

			/*Pme pme=demandeCession.getPme();
			pme.setNinea(recevabiliteDto.getNinea());
			pme.setRccm(recevabiliteDto.getRccm());
			pme.setRaisonSocial(recevabiliteDto.getRaisonSociale());
			BonEngagement be=demandeCession.getBonEngagement();
			be.setNomMarche(recevabiliteDto.getNomMarche());

			demandeCession.setPme(pme);
			demandeCession.setBonEngagement(be);*/
		}
		return demandeCession;
	}

	public static  CreanceDto convertToCreanceDto(DemandeCession demandeCession) {
		CreanceDto creanceDto = null;
		if(null != demandeCession) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			creanceDto =modelMapper.map(demandeCession, CreanceDto.class);
			//renseigner les mapping ambigue
			creanceDto.setIdCreance(demandeCession.getIdDemande());
			creanceDto.setNomMarche(demandeCession.getBonEngagement().getNomMarche());
			creanceDto.setRaisonSocial(demandeCession.getPme().getRaisonSocial());
			creanceDto.setDateDemandeCession(demandeCession.getDateDemandeCession());
			creanceDto.setMontantCreance(demandeCession.getBonEngagement().getMontantCreance());
			creanceDto.setRccm(demandeCession.getPme().getRccm());
			creanceDto.setStatutLibelle(demandeCession.getStatut().getLibelle());
		}
		return creanceDto;
	}

	public static  UtilisateurDto convertToDto(Utilisateur utilisateur) {
		UtilisateurDto utilisateurDto = null;
		if(null != utilisateur) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			utilisateurDto = modelMapper.map(utilisateur, UtilisateurDto.class);
		}
		return utilisateurDto;
	}

	public static Utilisateur convertToEntity(UtilisateurDto utilisateurDto) {
		Utilisateur utilisateur = new Utilisateur();
		if(null != utilisateurDto) {
			utilisateur = modelMapper.map(utilisateurDto, Utilisateur.class);
		}
		return utilisateur;
	}

}
