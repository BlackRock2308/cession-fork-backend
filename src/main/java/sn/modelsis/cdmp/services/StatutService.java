package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Statut;

import java.util.List;
import java.util.Optional;

public interface StatutService {
    /**
     * @param statut
     * @return
     */
    Statut save(Statut statut);

    /**
     * @return
     */
    List<Statut> findAll();

    /**
     * @param id
     * @return
     */
    Optional<Statut> getStatut(Long id);

    /**
     * @param id
     */
    void delete(Long id);

}
