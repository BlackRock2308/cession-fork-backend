package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.modelsis.cdmp.entities.Convention;

/**
 * @author SNDIAGNEF
 *
 */
public interface ConventionRepository extends JpaRepository<Convention, Long> {
    @Query(value = "SELECT * FROM Convention  as x join demandecession ON demandecession.id = x.demandeid WHERE x.demandeid=:idDemande ORDER BY x.dateConvention DESC LIMIT 1",
            nativeQuery = true)
    Convention findConventionValideByDemande(@Param("idDemande") Long idDemande);


    @Modifying
    @Query("DELETE FROM Convention c where c.idConvention =:#{#id}")
    void deleteConvention(Long id);
    
    @Modifying
    @Query("DELETE FROM Documents c where c.id =:#{#id}")
    void deleteDocument(Long id);


}
