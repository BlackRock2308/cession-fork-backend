package sn.modelsis.cdmp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.modelsis.cdmp.entities.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {


    List<Paiement> findAllByDemandeCessionPmeIdPME(Long idPME);

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
