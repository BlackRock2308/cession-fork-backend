/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    ObservationDto saveNewObservation(ObservationDto observation);

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

   // List<Observation> findObservationsByDemandeCession(Long idDemande);

    @Query("select p from Observation p where p.idDemande=:idDemande")
    List<Observation> findObservationsByDemandeCession(@Param("idDemande") Long idDemande);


    
}
