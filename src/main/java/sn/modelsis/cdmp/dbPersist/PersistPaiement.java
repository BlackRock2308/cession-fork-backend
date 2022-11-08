package sn.modelsis.cdmp.dbPersist;

import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.services.DetailPaiementService;
import sn.modelsis.cdmp.services.PaiementService;

import java.util.Optional;

public class PersistPaiement {

private final DemandeCessionRepository demandeCessionRepository;

private final PaiementService paiementService;

private final DetailPaiementService detailPaiementService;



   public PersistPaiement(DemandeCessionRepository demandeCessionRepository, PaiementService paiementService, DetailPaiementService detailPaiementService) {
       this.demandeCessionRepository = demandeCessionRepository;
       this.paiementService = paiementService;
       this.detailPaiementService = detailPaiementService;

       Paiement p1 = new Paiement();

       Paiement p2 = new Paiement();

       Paiement p3 = new Paiement();

       Optional<DemandeCession> dC1 = this.demandeCessionRepository.findById(1L);
       if (dC1.isPresent() && dC1 != null) {
           PaiementDto paiementDto = new PaiementDto();
           paiementDto.setNomMarche("Contruction");
           paiementDto.setMontantCreance(50000);
           paiementDto.setMontantRecuCDMP(0);
           paiementDto.setSoldePME(0);
           paiementDto.setRaisonSocial("GFPOIOI");
           paiementDto.setDemandecessionid(dC1.get().getIdDemande());
           p1 = paiementService.save(paiementDto).getPaiement();
       }


       Optional<DemandeCession> dC2 = this.demandeCessionRepository.findById(2L);
       if (dC2.isPresent() && dC2 != null) {
           PaiementDto paiementDto = new PaiementDto();
           paiementDto.setNomMarche("Contruction");
           paiementDto.setMontantCreance(50000);
           paiementDto.setMontantRecuCDMP(0);
           paiementDto.setSoldePME(0);
           paiementDto.setRaisonSocial("GFPOIOI");
           paiementDto.setDemandecessionid(dC2.get().getIdDemande());
           p2 = paiementService.save(paiementDto).getPaiement();
       }
       Optional<DemandeCession> dC3 = this.demandeCessionRepository.findById(3L);
       if (dC3.isPresent() && dC3 != null) {
           PaiementDto paiementDto = new PaiementDto();
           paiementDto.setNomMarche("Contruction");
           paiementDto.setMontantCreance(50000);
           paiementDto.setMontantRecuCDMP(0);
           paiementDto.setSoldePME(0);
           paiementDto.setRaisonSocial("GFPOIOI");
           paiementDto.setDemandecessionid(dC3.get().getIdDemande());
           p3 = paiementService.save(paiementDto).getPaiement();
       }

       DetailPaiement pme1 = new DetailPaiement();
       pme1.setPaiement(p1);
       detailPaiementService.paiementPME(pme1);
       DetailPaiement cdmp1 = new DetailPaiement();
       cdmp1.setPaiement(p1);
       detailPaiementService.paiementCDMP(cdmp1);

       DetailPaiement pme2 = new DetailPaiement();
       pme1.setPaiement(p2);
       detailPaiementService.paiementPME(pme2);
       DetailPaiement cdmp2 = new DetailPaiement();
       cdmp1.setPaiement(p2);
       detailPaiementService.paiementCDMP(cdmp2);


       DetailPaiement pme3 = new DetailPaiement();
       pme1.setPaiement(p3);
       detailPaiementService.paiementPME(pme3);
       DetailPaiement cdmp3 = new DetailPaiement();
       cdmp1.setPaiement(p3);
       detailPaiementService.paiementCDMP(cdmp3);


   }
}
