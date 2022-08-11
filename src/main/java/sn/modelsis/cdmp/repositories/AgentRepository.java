package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Agent;
/**
 * @author SNDIAGNEF
 *
 */
public interface AgentRepository extends JpaRepository<Agent, Long> {
}
