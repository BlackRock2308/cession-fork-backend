package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Pme;

import java.util.Optional;

public interface PmeRepository extends JpaRepository<Pme, Long> {
    //Optional<Pme> findPmeById(Long idPME);
}
