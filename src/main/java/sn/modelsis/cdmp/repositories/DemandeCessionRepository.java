package sn.modelsis.cdmp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Statuts;

import java.util.Date;
import java.util.List;

public interface DemandeCessionRepository extends JpaRepository<DemandeCession,Long> {


     Page<DemandeCession> findAllByStatut_Libelle(org.springframework.data.domain.Pageable pageable, String statut);

     List<DemandeCession> findAllByPmeIdPME(Long id);

    Page<DemandeCession> findAllByPmeIdPME(org.springframework.data.domain.Pageable pageable, Long id);


    @Query("select p from DemandeCession  p where p.idDemande=:idDemande")
    DemandeCession findByDemandeId(Long idDemande);
    @Query(nativeQuery = true,
            value = "select * from public.statistiqueDemandeByStatutAndMoth(:statut, :datedemandecession)")
    Integer DemandeByStautAntMonth(@Param("statut") String statut, @Param("datedemandecession") Date dateDemande);






}
