package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.util.StatistiquePaiementCDMP;
import sn.modelsis.cdmp.util.StatistiquePaiementPME;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    @Query(nativeQuery = true,
            value = "select * from public.getStatistiquePaiementCDMP(:monthData)")
    StatistiquePaiementCDMP getStatistiquePaiementCDMP(@Param("monthData") LocalDateTime monthData);

    @Query(nativeQuery = true,
            value = "select * from public.getStatistiquePaiementPME(:monthData)")
    StatistiquePaiementPME getStatistiquePaiementPME(@Param("monthData") LocalDateTime monthData);

}
