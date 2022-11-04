package sn.modelsis.cdmp.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.*;
import sn.modelsis.cdmp.services.PaiementService;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class PaiementServiceImpl implements PaiementService {


    private final Logger log = LoggerFactory.getLogger(PaiementServiceImpl.class);

    private final PaiementRepository paiementRepository;

    private final BonEngagementRepository bonEngagementRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository,
                               BonEngagementRepository bonEngagementRepository) {
        this.paiementRepository = paiementRepository;
        this.bonEngagementRepository = bonEngagementRepository;
    }

    @Autowired
    private DemandeCessionRepository demandeCessionRepository;

    @Autowired
    private ConventionRepository conventionRepository;

    @Autowired
    private StatutRepository statutRepository;


    @Override
    public Paiement save(PaiementDto paiementDto, double montant, TypePaiement typePaiement) {


        //log.info("Demande:{} ",demande.isPresent());
        Paiement paiement=new Paiement();

        if (paiementRepository.findAll().stream().filter(result -> paiementDto.getDemandecessionid()==result.getDemandeCession().getIdDemande())!=null){
            throw new CustomException("Paiement for this demande Already exist");
        }


        demandeCessionRepository.findById(paiementDto.getDemandecessionid()).ifPresentOrElse(
                (value)
                        -> {

                    paiement.setDemandeCession(value);
                    log.info("Paiement:{} ",paiement.getDemandeCession().getIdDemande());

                    double montantCreance=value.getBonEngagement().getMontantCreance();
                    Statut statut=value.getStatut();
                    log.info("statut:{} ",statut.getLibelle());

                    /*double soldePME= paiement.getSoldePME();
                    double montantRecuCDMP=paiement.getMontantRecuCDMP();

                     */

                    if (statut.getLibelle()=="CONVENTION_ACCEPTEE"){
                        Convention conventionAcceptee=conventionRepository.findConventionValideByDemande(paiement.getDemandeCession().getIdDemande());
                        double decote=conventionAcceptee.getDecote();
                        paiement.setSoldePME((montantCreance*decote)/100);
                        paiement.setMontantRecuCDMP(0);


                        value.setStatut(this.statutRepository.findByLibelle("PME_EN_ATTENTE_DE_PAIEMENT"));
                        demandeCessionRepository.save(value);
                    }

                                       else {
                        throw new CustomException("Un paiement ne peut être effectué à cet étape.");
                    }

                },
                ()
                        -> {
                    throw new CustomException("la demande n'existe pas");
                }

        );
        /*

         */


        return paiementRepository.save(paiement);
    }

    @Override
    public void update(Long idPaiement,double montant,TypePaiement typePaiement) {


        //log.info("Demande:{} ",demande.isPresent());
        //Paiement paiement=new Paiement();


        log.info("paiement:{} ",idPaiement);
        paiementRepository.findById(idPaiement).ifPresentOrElse(
                (value)
                        -> {

                    //paiement.setDemandecession(value);

                    //double montantCreance=value.getBonEngagement().getMontantCreance();
                    Statut statut =value.getDemandeCession().getStatut();
                    log.info("statut:{} ",statut.getLibelle());
                    log.info("soldePME:{} ",value.getSoldePME());


                    /*double soldePME= paiement.getSoldePME();
                    double montantRecuCDMP=paiement.getMontantRecuCDMP();

                     */

                    /*if (statutLibelle==Statuts.CONVENTION_ACCEPTEE){
                        Convention conventionAcceptee=conventionRepository.findConventionValideByDemande(paiement.getDemandecession().getIdDemande());
                        double decote=conventionAcceptee.getDecote();
                        paiement.setSoldePME((montantCreance*decote)/100);
                        paiement.setMontantRecuCDMP(0);


                        value.setStatut(this.statutRepository.findByLibelle(Statuts.PME_EN_ATTENTE_DE_PAIEMENT));
                        demandeCessionRepository.save(value);
                    }

                     */

                    if (statut.getLibelle()=="PME_EN_ATTENTE_DE_PAIEMENT" || statut.getLibelle()=="CDMP_EN_ATTENTE_DE_PAIEMENT" || statut.getLibelle()=="PME_PARTIELLEMENT_PAYEE" || statut.getLibelle()=="CDMP_PARTIELLEMENT_PAYEE"){
                        //paiement=paiementRepository.findById(idPaiement).orElse(null);
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
                ()
                        -> {
                    throw new CustomException("le paiement n'est pas encore enregistré");
                     }
        );
        /*

         */

    }

    @Override
    public List<Paiement> findAll(){
        /*List<DemandeCession> demandes=new DemandeCession();

        demandes.addAll(this.demandeCessionRepository.findAllByStatut_Libelle(Statuts.CDMP_EN_ATTENTE_DE_PAIEMENT));
        demandes.addAll(this.demandeCessionRepository.findAllByStatut_Libelle(Statuts.PME_EN_ATTENTE_DE_PAIEMENT));
        demandes.addAll(this.demandeCessionRepository.findAllByStatut_Libelle(Statuts.CDMP_PARTIELLEMENT_PAYEE));
        demandes.addAll(this.demandeCessionRepository.findAllByStatut_Libelle(Statuts.PME_PARTIELLEMENT_PAYEE));
        demandes.addAll(this.demandeCessionRepository.findAllByStatut_Libelle(Statuts.CDMP_TOTALEMENT_PAYEE));
        demandes.addAll(this.demandeCessionRepository.findAllByStatut_Libelle(Statuts.PME_TOTALEMENT_PAYEE));
        */

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

}


