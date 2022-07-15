package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Demande;

import java.util.List;
import java.util.Optional;

public interface DemandeService {
    /**
     *
     * @param demande
     * @return
     */
    Demande save(Demande demande);

    /**
     *
     * @return
     */
    List<Demande> findAll();

    /**
     *
     * @param id
     * @return
     */
    Optional<Demande> getDemande(Long id);

    /**
     *
     * @param id
     */
    void delete(Long id);

}
