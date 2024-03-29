package sn.modelsis.cdmp.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.TypePaiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiquePaiementCDMPDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiquePaiementPMEDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.ConventionRepository;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PaiementRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.services.PaiementService;
import sn.modelsis.cdmp.util.DtoConverter;
import sn.modelsis.cdmp.util.ObjetMontantMois;
import sn.modelsis.cdmp.util.Util;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;

    private final BonEngagementRepository bonEngagementRepository;
    private final DemandeCessionRepository demandeCessionRepository;
    private final ConventionRepository conventionRepository;
    private final StatutRepository statutRepository;


    @Override
    public Paiement savePaiement(PaiementDto paiementDto) {
        return paiementRepository.save(DtoConverter.convertToEntity(paiementDto));
    }

    @Override
    public Paiement addPaiementToDemandeCession(PaiementDto paiementDto) {

        Statut newDemandeStatus = statutRepository.findByCode("CONVENTION_ACCEPTEE");
        Paiement paiement = new Paiement();
        DemandeCession demandeCession = initPaiement(paiementDto);
        demandeCession.setStatut(newDemandeStatus);
        String statusLibelle = demandeCession.getStatut().getLibelle() ;
        if(! statusLibelle.equals("CONVENTION_ACCEPTEE"))
            throw new CustomException("Vous devez d'abord ajouter la convention le status du paiement doit etre CONVENTION ACCEPTEE ");

         DemandeCession demandeCessionSaved = demandeCessionRepository.save(demandeCession);
          if (demandeCessionSaved!=null)
              return demandeCessionSaved.getPaiement();
      return paiement;

    }

    @Override
    public void update(Long idPaiement,double montant,TypePaiement typePaiement) {

        log.info("paiement:{} ",idPaiement);
        paiementRepository.findById(idPaiement).ifPresentOrElse(
                (value)
                        -> {
                    Statut statut =value.getDemandeCession().getStatut();
                    log.info("statut:{} ",statut.getLibelle());
                    log.info("soldePME:{} ",value.getSoldePME());

                    if (statut.getLibelle()=="PME_EN_ATTENTE_DE_PAIEMENT" || statut.getLibelle()=="CDMP_EN_ATTENTE_DE_PAIEMENT" || statut.getLibelle()=="PME_PARTIELLEMENT_PAYEE" || statut.getLibelle()=="CDMP_PARTIELLEMENT_PAYEE"){
                        if (typePaiement==TypePaiement.SICA_CDMP) {
                            value.setMontantRecuCDMP(value.getMontantRecuCDMP()+montant);

                            value.getDemandeCession().setStatut(this.statutRepository.findByLibelle("CDMP_PARTIELLEMENT_PAYEE"));
                            if (value.getMontantRecuCDMP()==value.getDemandeCession().getBonEngagement().getMontantCreance()){
                                value.getDemandeCession().setStatut(this.statutRepository.findByLibelle("CDMP_TOTALEMENT_PAYEE"));
                            }

                        }
                        if (typePaiement==TypePaiement.CDMP_PME) {
                            log.info("soldePME:{} ",value.getSoldePME());
                            value.setSoldePME(value.getSoldePME()-montant);
                            value.getDemandeCession().setStatut(this.statutRepository.findByLibelle("PME_PARTIELLEMENT_PAYEE"));
                            if (value.getSoldePME()==0){
                                value.getDemandeCession().setStatut(this.statutRepository.findByLibelle("PME_TOTALEMENT_PAYEE"));
                            }

                        }
                        log.info("paiementDemande:{} ",value.getDemandeCession().getIdDemande());

                        paiementRepository.save(value);
                    }
                    else {
                        throw new CustomException("Un paiement ne peut être effectué à cet étape.");
                    }

                },
                () -> {
                    throw new CustomException("le paiement n'est pas encore enregistré");
                     }
        );

    }

    @Override
    public List<Paiement> findAllByPME(Long idMPE) {
        return paiementRepository.findAllByDemandeCessionPmeIdPME(idMPE);
    }

    @Override
    public List<Paiement> findAll(){
        return paiementRepository.findAll();
    }

    @Override
    public Optional<Paiement> getPaiement(Long id) {
        return paiementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        paiementRepository.deleteById(id);

    }

    @Override
    public Paiement savePaiement(Paiement paiement){
        return paiementRepository.save(paiement);
    }

    @Override
    public StatistiquePaiementCDMPDto getStatistiquePaiementCDMP(int annee)  {
        LocalDateTime today = LocalDateTime.of(annee, 1, 1,0,0,0);
        LocalDateTime today2 = LocalDateTime.now();
        StatistiquePaiementCDMPDto statistiquePaiementCDMPDto = new StatistiquePaiementCDMPDto();
            statistiquePaiementCDMPDto.setYear(annee);
            Double[] donnes = new Double[4];
            int nbre = 12;
            if(today2.getYear() == annee){
                nbre = today2.getMonthValue();
            }
            if(today2.getYear() < annee){
                nbre = 0;
            }
            for(int i=0 ; i<nbre;i++){
                donnes = Util.donneStatistiquePaiementCDMP(paiementRepository.getStatistiquePaiementCDMP(today));
                statistiquePaiementCDMPDto.getCmulRembourses().add(new ObjetMontantMois(donnes[2],today));
                statistiquePaiementCDMPDto.getCumulDecotes().add(new ObjetMontantMois(donnes[0],today));
                statistiquePaiementCDMPDto.getCumulMontantCreance().add(new ObjetMontantMois(donnes[1],today));
                statistiquePaiementCDMPDto.getCumulSoldes().add(new ObjetMontantMois(donnes[3],today));
                today= today.plusMonths(1);
        }
        return statistiquePaiementCDMPDto;
    }

    @Override
    public StatistiquePaiementPMEDto getStatistiqueAllPaiementPME(int annee) {
        LocalDateTime today =LocalDateTime.of(annee, 1, 1,0,0,0);
        LocalDateTime today2 = LocalDateTime.now();
        StatistiquePaiementPMEDto statistiquePaiementPMEDto = new StatistiquePaiementPMEDto();
            statistiquePaiementPMEDto.setYear(annee);
            Double[] donnes = new Double[3];
        int nbre = 12;
        if(today2.getYear() == annee){
            nbre = today2.getMonthValue();
        }
        if(today2.getYear() < annee){
            nbre = 0;
        }
        for(int i=0 ; i<nbre;i++){
                donnes = Util.donneStatistiquePaiementPME(paiementRepository.getStatistiquePaiementPME(today));
                statistiquePaiementPMEDto.getCumulDebourses().add(new ObjetMontantMois(donnes[2],today));
                statistiquePaiementPMEDto.getCumulMontantCreance().add(new ObjetMontantMois(donnes[1],today));
                statistiquePaiementPMEDto.getCumulSoldes().add(new ObjetMontantMois(donnes[0],today));
                statistiquePaiementPMEDto.getCumulDecotes().add(new ObjetMontantMois(donnes[3], today));
                today= today.plusMonths(1);
        }
        return statistiquePaiementPMEDto;
    }

    @Override
    public StatistiquePaiementPMEDto getStatistiquePaiementByPME(int annee, Long idPME ) {
        LocalDateTime today = LocalDateTime.of(annee, 1, 1, 0, 0, 0);
        StatistiquePaiementPMEDto statistiquePaiementPMEDto = new StatistiquePaiementPMEDto();
            statistiquePaiementPMEDto.setYear(annee);
            Double[] donnes = new Double[4];
            LocalDateTime today2 = LocalDateTime.now();
        int nbre = 12;
        if(today2.getYear() == annee){
            nbre = today2.getMonthValue();
        }
        if(today2.getYear() < annee){
            nbre = 0;
        }
        for(int i=0 ; i<nbre;i++){
                donnes = Util.donneStatistiquePaiementPME(paiementRepository.getStatistiquePaiementByPME(idPME,today));
                statistiquePaiementPMEDto.getCumulDebourses().add(new ObjetMontantMois(donnes[2], today));
                statistiquePaiementPMEDto.getCumulMontantCreance().add(new ObjetMontantMois(donnes[1], today));
                statistiquePaiementPMEDto.getCumulSoldes().add(new ObjetMontantMois(donnes[0], today));
                statistiquePaiementPMEDto.getCumulDecotes().add(new ObjetMontantMois(donnes[3], today));
                today = today.plusMonths(1);
        }
        return statistiquePaiementPMEDto;
    }

    private double setDecote(DemandeCession demandeCession){
        double decote = 0 ;
        Set<Convention> conventions=  demandeCession.getConventions();
        for (Convention convention :conventions ) {
            if (convention.isActiveConvention()){
                if (convention.getValeurDecoteByDG() != 0){
                    decote=convention.getValeurDecoteByDG();
                    log.info("Decote Convention DG : {}", decote);
                }else {
                    decote=convention.getValeurDecote();
                    log.info("Decote Convention default : {}", decote);
                }
            }
        }
        return decote ;

    }
    private DemandeCession  initPaiement(PaiementDto paiementDto ){
        DemandeCession demandeCession = demandeCessionRepository.findById(paiementDto.getDemandeId()).orElse(null);
        BonEngagement bonEngagement = demandeCession.getBonEngagement() ;
        double montantCreanceInitial = bonEngagement.getMontantCreance();
        double montantCreance=bonEngagement.getMontantCreance();
        double decote = 0 ;
        decote = setDecote(demandeCession);
        Paiement paiement = DtoConverter.convertToEntity(paiementDto);
        paiement.setDemandeCession(demandeCession);
        paiement.setNomMarche(bonEngagement.getNomMarche());
        paiement.setRaisonSocial(demandeCession.getPme().getRaisonSocial() );
        paiement.setSoldePME(montantCreance- (montantCreance*decote) );
        paiement.setMontantRecuCDMP(0);
        paiement.setMontantCreance(paiement.getSoldePME());
        paiement.setMontantCreanceInitial(montantCreanceInitial);
        paiement = setStatusPaiement(paiement);
        demandeCession.setPaiement(paiement);
        return demandeCession;
    }

    private Paiement setStatusPaiement(Paiement paiement){
        Statut statutCDMP = statutRepository.findByCode("CDMP_EN_ATTENTE_DE_PAIEMENT");
        Statut statutPme = statutRepository.findByCode("PME_EN_ATTENTE_DE_PAIEMENT");
        paiement.setStatutCDMP(statutCDMP);
        paiement.setStatutPme(statutPme);
        return paiement;
    }
}


