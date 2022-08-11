/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Agent;
import sn.modelsis.cdmp.repositories.AgentRepository;
import sn.modelsis.cdmp.services.AgentService;

/**
 * @author SNDIAGNEF
 *
 */
@Service
public class AgentServiceImpl implements AgentService{
  
  @Autowired
  private AgentRepository agentRepository;
 
  @Override
  public Agent save(Agent agent) {
    return agentRepository.save(agent);
	}

  @Override
  public List<Agent> findAll(){
    return agentRepository.findAll();
  }

  @Override
  public Optional<Agent> getAgent(Long id) {
    return agentRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    agentRepository.deleteById(id);

  }

}
