package sn.modelsis.cdmp.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.TypePaiement;
import sn.modelsis.cdmp.entitiesDtos.DetailPaiementDto;
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
import sn.modelsis.cdmp.util.*;

import javax.xml.crypto.Data;

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
        DemandeCession demandeCession = demandeCessionRepository.findById(paiementDto.getDemandeId()).orElse(null);

        String statusLibelle = demandeCession.getStatut().getLibelle() ;
        Statut statutCDMP = statutRepository.findByCode("CDMP_EN_ATTENTE_DE_PAIEMENT");
        Statut newDemandeStatus = statutRepository.findByCode("CONVENTION_ACCEPTEE");
        Statut statutPme = statutRepository.findByCode("PME_EN_ATTENTE_DE_PAIEMENT");
        Paiement paiement = DtoConverter.convertToEntity(paiementDto);
        BonEngagement bonEngagement = demandeCession.getBonEngagement() ;
        double montantCreanceInitial = bonEngagement.getMontantCreance();
        double montantCreance=bonEngagement.getMontantCreance();
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
            //TODO
        //if(! statusLibelle.equals("CONVENTION_ACCEPTEE"))
         //  throw new CustomException("Vous devez d'abord ajouter la convention le status du paiement doit etre CONVENTION ACCEPTEE ");
            paiement.setDemandeCession(demandeCession);
            paiement.setSoldePME(montantCreance- (montantCreance*decote) );
            paiement.setMontantRecuCDMP(0);
            paiement.setMontantCreance(paiement.getSoldePME());
            paiement.setStatutCDMP(statutCDMP);
            paiement.setStatutPme(statutPme);
            paiement.setMontantCreanceInitial(montantCreanceInitial);
            demandeCession.setStatut(newDemandeStatus);
            demandeCession.setPaiement(paiement);
            demandeCessionRepository.save(demandeCession);

      return demandeCession.getPaiement();

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
        LocalDateTime today = LocalDateTime.now();
        StatistiquePaiementCDMPDto statistiquePaiementCDMPDto = new StatistiquePaiementCDMPDto();
        if(annee <= today.getYear()){
            statistiquePaiementCDMPDto.setYear(annee);
            Double[] donnes = new Double[4];
            today= LocalDateTime.of(annee, 1, 1,0,0,0);
            for(int i=0 ; i<12;i++){
                donnes = Util.donneStatistiquePaiementCDMP(paiementRepository.getStatistiquePaiementCDMP(today));
                statistiquePaiementCDMPDto.getCmulRembourses().add(new ObjetMontantMois(donnes[2],today));
                statistiquePaiementCDMPDto.getCumulDecotes().add(new ObjetMontantMois(donnes[0],today));
                statistiquePaiementCDMPDto.getCumulMontantCreance().add(new ObjetMontantMois(donnes[1],today));
                statistiquePaiementCDMPDto.getCumulSoldes().add(new ObjetMontantMois(donnes[3],today));
                today= today.plusMonths(1);
            }
        }
        return statistiquePaiementCDMPDto;
    }

    @Override
    public StatistiquePaiementPMEDto getStatistiqueAllPaiementPME(int annee) {
        LocalDateTime today = LocalDateTime.now();
        StatistiquePaiementPMEDto statistiquePaiementPMEDto = new StatistiquePaiementPMEDto();
        if(annee <= today.getYear()){
            statistiquePaiementPMEDto.setYear(annee);
            Double[] donnes = new Double[3];
            today= LocalDateTime.of(annee, 1, 1,0,0,0);
            for(int i=0 ; i<12;i++){
                donnes = Util.donneStatistiquePaiementPME(paiementRepository.getStatistiquePaiementPME(today));
                statistiquePaiementPMEDto.getCmulDebourses().add(new ObjetMontantMois(donnes[2],today));
                statistiquePaiementPMEDto.getCumulMontantCreance().add(new ObjetMontantMois(donnes[1],today));
                statistiquePaiementPMEDto.getCumulSoldes().add(new ObjetMontantMois(donnes[0],today));
                today= today.plusMonths(1);
            }
        }
        return statistiquePaiementPMEDto;
    }

    @Override
    public StatistiquePaiementPMEDto getStatistiquePaiementByPME(int annee, Long idPME ) {
        LocalDateTime today = LocalDateTime.now();
        StatistiquePaiementPMEDto statistiquePaiementPMEDto = new StatistiquePaiementPMEDto();
        if (annee <= today.getYear()) {
            statistiquePaiementPMEDto.setYear(annee);
            Double[] donnes = new Double[3];
            today = LocalDateTime.of(annee, 1, 1, 0, 0, 0);
            for (int i = 0; i < 12; i++) {
                donnes = Util.donneStatistiquePaiementPME(paiementRepository.getStatistiquePaiementByPME(idPME,today));
                statistiquePaiementPMEDto.getCmulDebourses().add(new ObjetMontantMois(donnes[2], today));
                statistiquePaiementPMEDto.getCumulMontantCreance().add(new ObjetMontantMois(donnes[1], today));
                statistiquePaiementPMEDto.getCumulSoldes().add(new ObjetMontantMois(donnes[0], today));
                today = today.plusMonths(1);
            }
        }
        return statistiquePaiementPMEDto;
    }
}


