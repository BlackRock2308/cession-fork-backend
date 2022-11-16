package sn.modelsis.cdmp.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;

import java.util.Date;

public interface DemandeCessionRepository extends JpaRepository<DemandeCession,Long> {


     Page<DemandeCession> findAllByStatut_Libelle(org.springframework.data.domain.Pageable pageable, String statut);

     List<DemandeCession> findAllByPmeIdPME(Long id);

    Page<DemandeCession> findAllByPmeIdPME(org.springframework.data.domain.Pageable pageable, Long id);


    @Query("select p from DemandeCession  p where p.idDemande=:idDemande")
    DemandeCession findByDemandeId(Long idDemande);
    @Query(nativeQuery = true,
            value = "select * from public.statistiqueDemandeByStatutAndMoth(:statutDemande, :dateDemande)")
    Integer getDemandeByStautAntMonth(@Param("statutDemande") String statutDemande, @Param("dateDemande") LocalDate dateDemande);


    List<DemandeCession> findByNumeroDemandeContaining(String numeroDemande);

    Page<DemandeCession> findAllByPmeIdPMEAndStatut_Libelle(org.springframework.data.domain.Pageable pageable,Long idPME, String statut);

    @Query("select p from DemandeCession as p where" +
            " (:referenceBE is null or p.bonEngagement.reference like %:referenceBE%) " +
            "or (:numeroDemande is null or p.numeroDemande like %:numeroDemande% )" +
            "or (:nomMarche is null or p.bonEngagement.nomMarche like %:nomMarche%)" +
            "or (:statutLibelle is null or p.statut.libelle like %:statutLibelle%) "
    )
    List<DemandeCession> findByReferenceBE(@Param("referenceBE") String referenceBE,
                                           @Param("numeroDemande") String numeroDemande,
                                           @Param("nomMarche") String nomMarche,
                                           @Param("statutLibelle") String statutLibelle);


    @Query("select p from DemandeCession as p where" +
            " (:statutLibelle is null or p.statut.libelle like %:statutLibelle%) ")
    List<DemandeCession> findByLibelleStatutDemande(@Param("statutLibelle") String statutLibelle);


    @Query("select p from DemandeCession as p where p.dateDemandeCession = :demandeDate")
    List<DemandeCession> findDemandeCessionByDate(@Param("demandeDate") LocalDateTime demandeDate);


    List<DemandeCession> findDemandeCessionByDateDemandeCession(@Param("demandeDate") LocalDateTime demandeDate);
//@Query("select * from demandecession where datedemandecession='2022-11-15 12:32:27.869177';")

//    @Query(value = "SELECT e FROM EmployeeProjectView as e WHERE e.employeeId=:employeeId and (:inputString is null or e.lastName like %:inputString% ) and " +
//            "(:inputString is null or e.firstName like %:inputString%) and (:inputString is null or concat(e.projectId,'') like %:inputString%) and " +
//            " (:inputString is null or e.projectName like %:inputString%) and  (:inputString is null or concat(e.projectBudget,'') like %:inputString%) and "+
//            " (:inputString is null or e.projectLocation like %:inputString%)"
//    )
//    Page<EmployeeProjectView> findAllByInputString(Long employeeId, String inputString, Pageable pageable);


}
