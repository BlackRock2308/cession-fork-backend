package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.modelsis.cdmp.entities.Pme;

import java.util.Optional;

public interface PmeRepository extends JpaRepository<Pme, Long> {
    @Query("select p from Pme p where p.ninea=:ninea")
    Optional<Pme> findByNinea(@Param("ninea") String ninea);

    @Query("select p from Pme  p where p.rccm=:rccm")
    Optional<Pme> findByRccm(@Param("rccm") String telephonePME);

    @Query("select p from Pme  p where p.email=:email")
    Pme findByMail(@Param("email") String email);

    @Query("select p from Pme  p where p.telephonePME=:telephonePME")
    Optional<Pme> findByPhone(@Param("telephonePME") String telephonePME);

    Optional<Pme> findPmeByUtilisateurIdUtilisateur(Long id);


}
