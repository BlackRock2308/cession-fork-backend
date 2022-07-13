package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.repositories.PmeRepository;

@Service
public class PmeService {
    private PmeRepository pmeRepository;
    public PmeService(PmeRepository pmeRepository) {
        this.pmeRepository = pmeRepository;
    }

    public void addPme(Pme pme) {

    }
    public void updatePme(Pme pme, Long idPme) {
        Pme pmeUpdate = pmeRepository.findById(idPme).get();
        pmeUpdate.setPrenomRepresentant(pme.getPrenomRepresentant());
        pmeUpdate.setNomRepresentant(pme.getNomRepresentant());
        pmeUpdate.setAdressePME(pme.getAdressePME());
        pmeUpdate.setAtd(pme.getAtd());
        pmeUpdate.setTelephonePME(pme.getTelephonePME());
        pmeUpdate.setCentreFiscal(pme.getCentreFiscal());
    }
}
