package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.PmeRepository;

import java.util.Date;
import java.util.List;

@Service
public class PmeService {
    private PmeRepository pmeRepository;
    public PmeService(PmeRepository pmeRepository) {
        this.pmeRepository = pmeRepository;
    }

    public Pme addPme(Pme pme) {
      return pmeRepository.save(pme);
    }

    public List<Pme> findAllPme(){
        return pmeRepository.findAll();
    }

    /* public Pme findPmeById(Long id) {
        return pmeRepository.findPmeById(id)
                .orElseThrow(()->new NotFoundException("Pme not found"));
    }*/
    public Pme updatePme(Pme pme, Long idPme) {
        Pme pmeUpdate = pmeRepository.findById(idPme).get();
        pmeUpdate.setPrenomRepresentant(pme.getPrenomRepresentant());
        pmeUpdate.setNomRepresentant(pme.getNomRepresentant());
        pmeUpdate.setRccm(pme.getRccm());
        pmeUpdate.setAdressePME(pme.getAdressePME());
        pmeUpdate.setTelephonePME(pme.getTelephonePME());
        pmeUpdate.setDateImmatriculation(pme.getDateImmatriculation());
        pmeUpdate.setCentreFiscal(pme.getCentreFiscal());
        pmeUpdate.setNinea(pme.getNinea());
        pmeUpdate.setRaisonSocial(pme.getRaisonSocial());
        pmeUpdate.setAtd(pme.getAtd());
        pmeUpdate.setNantissement(pme.getNantissement());
        pmeUpdate.setInterdictionBancaire(pme.getInterdictionBancaire());
        pmeUpdate.setFormeJuridique(pme.getFormeJuridique());
        pmeUpdate.setEmail(pme.getEmail());
        pmeUpdate.setCodePin(pme.getCodePin());
        pmeUpdate.setUrlImageProfile(pme.getUrlImageProfile());
        pmeUpdate.setUrlImageSignature(pme.getUrlImageSignature());
        return pmeRepository.save(pmeUpdate);
    }

    public void deletePme(Long id){
        pmeRepository.deleteById(id);
    }
}
