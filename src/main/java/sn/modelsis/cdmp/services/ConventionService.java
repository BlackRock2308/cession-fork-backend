/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;
import sn.modelsis.cdmp.entities.Convention;


/**
 * @author SNDIAGNEF
 *
 */
public interface ConventionService {

    /**
     * 
     * @param convention
     * @return
     */
    Convention save(Convention convention);

    /**
    * 
    * @param pageable
    * @return
    */
    List<Convention> findAll();

    /**
     * 
     * @param id
     * @return
     */
    Optional<Convention> getConvention(Long id);

    /**
     * 
     * @param id
     */
    void delete(Long id);
    
    
}
