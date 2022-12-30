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

	public static TextConventionDto convertToDto(TextConvention textConvention) {
		TextConventionDto textConventionDto = null;
		if (null != textConvention) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			textConventionDto = modelMapper.map(textConvention, TextConventionDto.class);
		}
		return textConventionDto;
	}

	public static TextConvention convertToEntity(TextConventionDto textConventionDto) {
		TextConvention textConvention = null;
		if (null != textConventionDto) {
			textConvention = modelMapper.map(textConventionDto, TextConvention.class);
		}
		return textConvention;
	}
	
	public static ConventionDto convertToDto(Convention convention) {
	  ConventionDto conventionDto = null;
      if (null != convention) {
          modelMapper.getConfiguration().setAmbiguityIgnored(true);
          conventionDto = modelMapper.map(convention, ConventionDto.class);
		  conventionDto.setIdDemande(convention.getDemandeCession().getIdDemande());
		  conventionDto.setTextConventionDto(convertToDto(convention.getTextConvention()));

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
		ObservationDto observationDto = new ObservationDto();
		if (null != observation) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			observationDto = modelMapper.map(observation, ObservationDto.class);
			observationDto.setDemandeid(observation.getDemande().getIdDemande());
			observationDto.setUtilisateurid(observation.getUtilisateur().getIdUtilisateur());
			observationDto.setDemandeid(observation.getDemande().getIdDemande());
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
			if(null!=pme.getUtilisateur())
			pmeDto.setUtilisateurid(pme.getUtilisateur().getIdUtilisateur());
		}
		return pmeDto;
	}
	
	public static  MinistereDepensierDto convertToDto(MinistereDepensier md) {
       MinistereDepensierDto mdDto = null;
        if(null != md) {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            mdDto = modelMapper.map(md, MinistereDepensierDto.class);
          
        }
        return mdDto;
    }

	public static  MinistereDepensier convertToEntity(MinistereDepensierDto mdDto) {
		MinistereDepensier md = null;
		if(null != mdDto) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			md = modelMapper.map(mdDto, MinistereDepensier.class);

		}
		return md;
	}

	public static  CentreDesServicesFiscauxDto convertToDto(CentreDesServicesFiscaux md) {
		CentreDesServicesFiscauxDto mdDto = null;
		if(null != md) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			mdDto = modelMapper.map(md, CentreDesServicesFiscauxDto.class);

		}
		return mdDto;
	}

	public static  CentreDesServicesFiscaux convertToEntity(CentreDesServicesFiscauxDto mdDto) {
		CentreDesServicesFiscaux md = null;
		if(null != mdDto) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			md = modelMapper.map(mdDto, CentreDesServicesFiscaux.class);

		}
		return md;
	}

	public static  FormeJuridiqueDto convertToDto(FormeJuridique fj) {
		FormeJuridiqueDto fjDto = null;
		if(null != fj) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			fjDto = modelMapper.map(fj, FormeJuridiqueDto.class);

		}
		return fjDto;
	}

	public static  FormeJuridique convertToEntity(FormeJuridiqueDto fjDto) {
		FormeJuridique fj = null;
		if(null != fjDto) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			fj = modelMapper.map(fjDto, FormeJuridique.class);

		}
		return fj;
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
			demandecessionDto.setPme(DtoConverter.convertToDto(demandecession.getPme()));
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

			demandeadhesion.setPme(pme);
			demandeadhesion.setIdDemande(demandeadhesionDto.getIdDemande());
		}
		return demandeadhesion;
	}

	public static  DemandeAdhesionDto convertToDto(DemandeAdhesion demandeadhesion) {
		DemandeAdhesionDto demandeadhesionDto = null;
		if(null != demandeadhesion) {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			demandeadhesionDto = modelMapper.map(demandeadhesion, DemandeAdhesionDto.class);
			//convert les attributs ambigues

			demandeadhesionDto.setIdDemande(demandeadhesion.getIdDemande());
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
		//	paiementDto.setDemandeId(paiement.getDemandeCession().getIdDemande());
		//	paiementDto.setMontantCreance(paiement.getDemandeCession().getBonEngagement().getMontantCreance());
		//	paiementDto.setStatutDto(DtoConverter.convertToDto(paiement.getDemandeCession().getStatut()));
		paiementDto.setMontantCreance(paiement.getMontantCreance());

		}
		return paiementDto;
	}

	public static Paiement convertToEntity(PaiementDto paiementDto) {
		Paiement paiement = new Paiement();
		if(null != paiementDto) {
			paiement = modelMapper.map(paiementDto, Paiement.class);
			//renseigner les mapping ambigue

		}
		return paiement;
	}


	public static  RecevabiliteDto convertToRecevabiliteDto(DemandeCession demandeCession) {
		RecevabiliteDto recevabiliteDto = null;
		if(null != demandeCession) {
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
			demandeCession = modelMapper.map(recevabiliteDto, DemandeCession.class);

		}
		return demandeCession;
	}

//	public static CreanceDto convertToCreanceDto(DemandeCessionDto demandeCessionDto) {
//		if ( demandeCessionDto == null ) {
//			return null;
//		}
//
//		CreanceDto creanceDto = new CreanceDto();
//
//		creanceDto.setIdCreance( demandeCessionDto.getIdDemande() );
//		creanceDto.setNinea( demandeCessionDto.getPme().getNinea() );
//		creanceDto.setRccm( demandeCessionDto.getPme().getRccm() );
//		creanceDto.setRaisonSocial( demandeCessionDto.getPme().getRaisonSocial() );
//		creanceDto.setTypeMarche( demandeCessionDto.getBonEngagement().getTypeMarche() );
//		creanceDto.setNomMarche( demandeCessionDto.getBonEngagement().getNomMarche() );
//		creanceDto.setMontantCreance( demandeCessionDto.getBonEngagement().getMontantCreance() );
//		creanceDto.setDateDemandeCession( demandeCessionDto.getDateDemandeCession() );
//		creanceDto.setDateMarche( demandeCessionDto.getDateDemandeCession() );
//		creanceDto.setStatut( demandeCessionDto.getStatut() );
//		if(demandeCessionDto.getPaiement() != null){
//			creanceDto.setSoldePME( demandeCessionDto.getPaiement().getSoldePME() );
//			creanceDto.setMontantDebourse( demandeCessionDto.getPaiement().getMontantRecuCDMP() );
//		}
//
//		return creanceDto;
//	}

//	public static Page<CreanceDto> convertToListCreanceDto(DemandeCession demandeCession) {
//		CreanceDto creanceDto = null;
//		if(null != demandeCession) {
//			modelMapper.getConfiguration().setAmbiguityIgnored(true);
//			creanceDto =modelMapper.map(demandeCession, CreanceDto.class);
//			//renseigner les mapping ambigue
//			creanceDto.setIdCreance(demandeCession.getIdDemande());
//			creanceDto.setNomMarche(demandeCession.getBonEngagement().getNomMarche());
//			creanceDto.setRaisonSocial(demandeCession.getPme().getRaisonSocial());
//			creanceDto.setDateDemandeCession(demandeCession.getDateDemandeCession());
//			creanceDto.setMontantCreance(demandeCession.getBonEngagement().getMontantCreance());
//			creanceDto.setRccm(demandeCession.getPme().getRccm());
//			creanceDto.setStatut(demandeCession.getStatut());
//		}
//		return (Page<CreanceDto>) creanceDto ;
//	}

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
