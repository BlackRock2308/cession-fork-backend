package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.modelsis.cdmp.entities.Paiement;

import java.time.LocalDateTime;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    @Query(nativeQuery = true,
            value = "select * from public.getStatistiquePaiementCDMP(:monthData)")
    String getStatistiquePaiementCDMP(@Param("monthData") LocalDateTime monthData);

    @Query(nativeQuery = true,
            value = "select * from public.getStatistiquePaiementPME(:monthData)")
    String getStatistiquePaiementPME(@Param("monthData") LocalDateTime monthData);

    @Query(nativeQuery = true,
            value = "select * from public.getStatistiquePaiementByPME(:idPME,:monthData)")
    String getStatistiquePaiementByPME(@Param("idPME") Long idPME, @Param("monthData") LocalDateTime monthData);

}
