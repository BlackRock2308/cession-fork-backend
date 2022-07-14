/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;
import sn.modelsis.cdmp.entities.Agent;


/**
 * @author SNDIAGNEF
 *
 */
public interface AgentService {

    /**
     * 
     * @param agent
     * @return
     */
    Agent save(Agent agent);

    /**
    * 
    * @return
    */
    List<Agent> findAll();

    /**
     * 
     * @param id
     * @return
     */
    Optional<Agent> getAgent(Long id);

    /**
     * 
     * @param id
     */
    void delete(Long id);
    
    
}
