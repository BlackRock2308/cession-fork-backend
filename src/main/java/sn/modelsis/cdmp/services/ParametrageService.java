/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;
import sn.modelsis.cdmp.entities.Parametrage;

/**
 * @author SNDIAGNEF
 *
 */
public interface ParametrageService {

    /**
     * 
     * @param parametrage
     * @return
     */
    Parametrage save(Parametrage parametrage);

    /**
     * 
     * @return
     */
    List<Parametrage> findAll();

    /**
     * 
     * @param id
     * @return
     */
    Optional<Parametrage> getParametrage(Long id);

    /**
     * 
     * @param id
     */
    void delete(Long id);

}
