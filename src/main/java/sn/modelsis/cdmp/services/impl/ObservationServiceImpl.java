/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.ObservationDto;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.mappers.ObservationMapper;
import sn.modelsis.cdmp.repositories.DemandeCessionRepository;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.repositories.ObservationRepository;
import sn.modelsis.cdmp.repositories.StatutRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.DemandeCessionService;
import sn.modelsis.cdmp.services.ObservationService;
import sn.modelsis.cdmp.util.DtoConverter;

/**
 * @author SNDIAGNEF
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ObservationServiceImpl implements ObservationService{
  private final ObservationRepository observationRepository;

  private final DemandeRepository demandeRepository;

  private final UtilisateurRepository utilisateurRepository;

  private final StatutRepository statutRepository;

  private final DemandeCessionRepository demandeCessionRepository;

  private final ObservationMapper observationMapper;

  @Autowired
  private DemandeCessionService demandeCessionService;


  @Override
  public ObservationDto saveNewObservation(ObservationDto observation) {
    Observation newObservation;

    Statut statut = statutRepository.findByLibelle(observation.getStatut().getLibelle());

    try{
      log.info("ObservationService.saveNewObservation request  params : {}", observation);
      newObservation = observationMapper.mapToDto(observation);
      newObservation.setStatut(statut);
      Utilisateur utilisateur = utilisateurRepository.findById(observation.getUtilisateurid()).orElse(null);
      DemandeCession demandeCession = demandeCessionRepository.findById(observation.getDemandeid()).orElse(null);
      log.debug("ObservationService.saveNewObservation request params : {}", newObservation);
      newObservation.setDemande(demandeCession);
      newObservation.setUtilisateur(utilisateur);
      observationRepository.save(newObservation);
    } catch (Exception ex){
      log.info("Exception occured while adding new Observation to database.Exception message : {}", ex.getMessage());
      throw new CustomException("Exception occured while adding an Observation");
    }
    return DtoConverter.convertToDto(newObservation);
	}

  @Override
  public ObservationDto saveObservationRejetConvention(ObservationDto observation) {
    ObservationDto observationDto = saveNewObservation(observation);
    demandeCessionService.updateStatut(observationDto.getDemandeid(),observationDto.getStatut().getLibelle());
    return  observationDto;
  }

  @Override
  public List<Observation> findAll(){
    return observationRepository.findAll();
  }

  @Override
  public Optional<Observation> getObservation(Long id) {
    log.debug("ObservationService.getObservation request params : {}", id);

    return observationRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    observationRepository.deleteById(id);

  }

  @Override
  public List<Observation> findObservationsByDemandeCession(Long idDemande){

    Optional <DemandeCession> searchDemand = demandeCessionRepository.findById(idDemande);

    log.info("ObservationService.findObservationsByDemandeCession request params : {}", searchDemand.get().getIdDemande());

    List<Observation> observationList = observationRepository.findAllObservationByDemandeIdDemande(idDemande);
    log.info("ObservationService.findObservationsByDemandeCession request params : {}", observationList.stream().collect(Collectors.toList()));

    return observationList;
  }

}
