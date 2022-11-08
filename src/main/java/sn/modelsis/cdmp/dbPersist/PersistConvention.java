package sn.modelsis.cdmp.dbPersist;

import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.ConventionService;

import java.util.Optional;

public class PersistConvention {

    private final DemandeCessionRepository demandeCessionRepository;

    private final ConventionService conventionService;
    private final PmeRepository pmeRepository;

    public PersistConvention(DemandeCessionRepository demandeCessionRepository, ConventionService conventionService, PmeRepository pmeRepository) {
        this.demandeCessionRepository = demandeCessionRepository;
        this.conventionService = conventionService;
        this.pmeRepository = pmeRepository;

        Optional<DemandeCession> dC1 = demandeCessionRepository.findById(1L);
        Optional<Pme> pme = this.pmeRepository.findById(1L);
        if(dC1.isPresent() && dC1 != null && pme.isPresent() && pme != null){

            Convention c1 = new Convention();
            c1.setDemandeCession(dC1.get());
            c1.setPme(pme.get());
            conventionService.save(c1);

            Convention c2 = new Convention();
            c2.setDemandeCession(dC1.get());
            c2.setPme(pme.get());
            conventionService.save(c2);
        }
        Optional<DemandeCession> dC2 = demandeCessionRepository.findById(2L);
        if (dC2.isPresent() && dC2 != null && pme.isPresent() && pme != null){
            Convention c3 = new Convention();
            c3.setDemandeCession(dC1.get());
            c3.setPme(pme.get());
            conventionService.save(c3);
        }

        Optional<DemandeCession> dC3 = demandeCessionRepository.findById(3L);
        if(dC3.isPresent() && dC3 != null && pme.isPresent() && pme != null){
            Convention c4 = new Convention();
            conventionService.save(c4);
        }



    }
}
