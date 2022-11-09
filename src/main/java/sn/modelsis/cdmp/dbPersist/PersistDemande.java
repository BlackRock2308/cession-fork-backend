package sn.modelsis.cdmp.dbPersist;

import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.DemandeAdhesionService;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.util.DtoConverter;


import java.util.Optional;

public class PersistDemande {

    private  final DemandeCessionService demandeCessionService;

    private final DemandeAdhesionService demandeAdhesionService;

    private final PmeRepository pmeRepository;

    private final BonEngagementRepository bonEngagementRepository;


    public PersistDemande(DemandeCessionService demandeCessionService, DemandeAdhesionService demandeAdhesionService, PmeRepository pmeRepository, BonEngagementRepository bonEngagementRepository){
        this.demandeCessionService = demandeCessionService;
        this.demandeAdhesionService = demandeAdhesionService;
        this.pmeRepository = pmeRepository;
        this.bonEngagementRepository = bonEngagementRepository;

        Optional<Pme> pme = this.pmeRepository.findById(1L);
        if(pme.isPresent() && pme != null) {

            DemandeAdhesion dA1 = new DemandeAdhesion();
            dA1.setPme(pme.get());
            //this.demandeAdhesionService.saveAdhesion(dA1);

            DemandeAdhesion dA2 = new DemandeAdhesion();
            dA1.setPme(pme.get());
            //this.demandeAdhesionService.saveAdhesion(dA2);

            DemandeAdhesion dA3 = new DemandeAdhesion();
            dA1.setPme(pme.get());
            //this.demandeAdhesionService.saveAdhesion(dA3);


            Optional<BonEngagement> bE1 = this.bonEngagementRepository.findById(1L);
            if(bE1.isPresent() && bE1 != null){
                DemandeCession dC1 = new DemandeCession();
                dC1.setPme(pme.get());
                dC1.setBonEngagement(bE1.get());
                demandeCessionService.saveCession(dC1);

                DemandeCession dC2 = new DemandeCession();
                dC2.setPme(pme.get());
                dC2.setBonEngagement(bE1.get());
                this.demandeCessionService.saveCession(dC2);
            }

            Optional<BonEngagement> bE2 = this.bonEngagementRepository.findById(1L);
            if(bE2.isPresent() && bE2 != null){
                DemandeCession dC3 = new DemandeCession();
                dC3.setPme(pme.get());
                dC3.setBonEngagement(bE2.get());
                this.demandeCessionService.save(dC3);
            }



        }
    }

}
