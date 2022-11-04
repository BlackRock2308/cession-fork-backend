/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;


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
    ObservationDto save(Observation observation);

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
