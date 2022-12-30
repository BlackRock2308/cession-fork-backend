package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.modelsis.cdmp.entities.CentreDesServicesFiscaux;

@Repository
public interface CentreDesServicesFiscauxRepository extends JpaRepository<CentreDesServicesFiscaux, Long> {
    CentreDesServicesFiscaux findByCode(@Param("code") String code);
}
