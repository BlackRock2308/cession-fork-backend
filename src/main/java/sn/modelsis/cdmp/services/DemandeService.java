package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.DemandeRepository;

import java.util.List;

@Service
public class DemandeService {
    private DemandeRepository demandeRepository;

    public DemandeService(DemandeRepository demandeRepository){
        this.demandeRepository = demandeRepository;
    }

    public Demande addDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public List<Demande> findAllDemande(){
        return demandeRepository.findAll();
    }

    /*public Demande findDemandeById(Long id){
        return demandeRepository.findDemandeById(id)
                .orElseThrow(()->new NotFoundException("Demande not find"));
    }*/

    public Demande updateDemande(Demande demande, Long idDemande){
        Demande demandeUpdate = demandeRepository .findById(idDemande).get();
        demandeUpdate.setStatut(demande.getStatut());
        demandeUpdate.setBonEngagement(demande.getBonEngagement());
        return  demandeRepository.save(demandeUpdate);
    }

    public void deleteDemande(Long idDemande){
        demandeRepository.deleteById(idDemande);
    }
}
