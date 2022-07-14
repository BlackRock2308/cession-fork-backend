package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.exceptions.NotFoundException;
import sn.modelsis.cdmp.repositories.StatutRepository;

import java.util.List;

@Service
public class StatutService {
    private StatutRepository statutRepository;
    public StatutService(StatutRepository statutRepository) {
        this.statutRepository = statutRepository;
    }

    public Statut addStatut(Statut statut) {
        return statutRepository.save(statut);
    }

    public List<Statut> findAllStatut(){
        return  statutRepository.findAll();
    }

    /*public Statut findStatutById(Long id){
        return statutRepository.findStatutById(id)
                .orElseThrow(()->new NotFoundException("Statut not find"));
    }*/

    public Statut updateStatut(Statut statut, Long idStatut){
        Statut statutUpdate = statutRepository.findById(idStatut).get();
        statutUpdate.setLibelle(statut.getLibelle());
        return  statutRepository.save(statutUpdate);
    }

    public void deleteStatut(Long idStatut){
        statutRepository.deleteById(idStatut);
    }
}
