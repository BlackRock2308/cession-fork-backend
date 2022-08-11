package sn.modelsis.cdmp.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.modelsis.cdmp.Util.DtoConverter;
import sn.modelsis.cdmp.entities.Agent;
import sn.modelsis.cdmp.entitiesDtos.AgentDto;
import sn.modelsis.cdmp.services.AgentService;

/**
 * @author SNDIAGNEF
 * REST Controller to handle Agent
 */
@RestController
@RequestMapping("/api/agents")
public class AgentControllers {

  private final Logger log = LoggerFactory.getLogger(AgentControllers.class);

  @Autowired
  private AgentService agentService;

  @PostMapping()
  public ResponseEntity<AgentDto> addAgent(@RequestBody AgentDto agentDto,
      HttpServletRequest request) {
    Agent agent = DtoConverter.convertToEntity(agentDto);
    Agent result = agentService.save(agent);
    log.info("Agent create. Id:{} ", result.getIdAgent());
    return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.convertToDto(result));
  }

  @PutMapping()
  public ResponseEntity<AgentDto> updateAgent(@RequestBody AgentDto agentDto,
      HttpServletRequest request) {
    Agent agent = DtoConverter.convertToEntity(agentDto);
    Agent result = agentService.save(agent);
    log.info("Agent updated. Id:{}", result.getIdAgent());
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(result));
  }

  @GetMapping
  public ResponseEntity<List<AgentDto>> getAllAgents(HttpServletRequest request) {
    List<Agent> agents = agentService.findAll();
    log.info("All agents .");
    return ResponseEntity.status(HttpStatus.OK)
        .body(agents.stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<AgentDto> getAgent(@PathVariable Long id, HttpServletRequest request) {
    Agent agent = agentService.getAgent(id).orElse(null);
    log.info("Agent . Id:{}", id);
    return ResponseEntity.status(HttpStatus.OK).body(DtoConverter.convertToDto(agent));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<AgentDto> deleteAgent(@PathVariable Long id, HttpServletRequest request) {
    agentService.delete(id);
    log.warn("Agent deleted. Id:{}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}

