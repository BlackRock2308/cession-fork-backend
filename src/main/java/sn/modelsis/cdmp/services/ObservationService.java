/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;
import sn.modelsis.cdmp.entities.Observation;


/**
 * @author SNDIAGNEF
 *
 */
public interface ObservationService {

    /**
     * 
     * @param observation
     * @return
     */
    Observation save(Observation observation);

    /**
    * 
    * @return
    */
    List<Observation> findAll();

    /**
     * 
     * @param id
     * @return
     */
    Optional<Observation> getObservation(Long id);

    /**
     * 
     * @param id
     */
    void delete(Long id);
    
    
}
