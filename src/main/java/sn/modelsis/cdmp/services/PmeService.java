package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Pme;
import java.util.List;
import java.util.Optional;

public interface PmeService {
    /**
     * @param pme
     * @return
     */
    Pme save(Pme pme);

    /**
     * @return
     */
    List<Pme> findAll();

    /**
     * @param id
     * @return
     */
    Optional<Pme> getPme(Long id);

    /**
     * @param id
     */
    void delete(Long id);
}
