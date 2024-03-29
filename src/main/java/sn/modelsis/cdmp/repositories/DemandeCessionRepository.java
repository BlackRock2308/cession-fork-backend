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


public interface DemandeCessionRepository extends JpaRepository<DemandeCession,Long> {


    Page<DemandeCession> findAllByStatut_LibelleIn(Pageable pageable, String[] statuts);
    
    Page<DemandeCession> findAllByMinisterIdAndStatut_LibelleIn(Pageable pageable, String minstere, String[] statuts);

     List<DemandeCession> findAllByPmeIdPME(Long id);

    Page<DemandeCession> findAllByPmeIdPME(Pageable pageable, Long id);

    @Query("select p from DemandeCession p where p.statut.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE') ")
    Page<DemandeCession> findDemandeCessionByRightLibele(Pageable pageable);

    @Query("select p from DemandeCession  p where p.idDemande=:idDemande")
    DemandeCession findByDemandeId(Long idDemande);

    @Query(nativeQuery = true,
            value = "select * from public.statistiqueDemandeByStatutAndMoth(:statutDemande, :dateDemande)")
    Integer getDemandeByStautAntMonth(@Param("statutDemande") String statutDemande, @Param("dateDemande") LocalDate dateDemande);

    Page<DemandeCession> findAllByPmeIdPMEAndStatut_Libelle(Pageable pageable,Long idPME, String statut);


    /************** Filtering creance by multiple parameters **************/

    @Query("select p from DemandeCession as p where" +
            " (:raisonSocial is null or p.pme.raisonSocial like %:raisonSocial%) ")
    List<DemandeCession> searchCreanceByRaisonSocial(@Param("raisonSocial") String raisonSocial);


    @Query("select p from DemandeCession as p where" +
            " (:nomMarche is null or p.bonEngagement.nomMarche like %:nomMarche%) ")
    List<DemandeCession> searchCreanceByNomMarche(@Param("nomMarche") String nomMarche);

    @Query("select p from DemandeCession as p where" +
            " (:montantCreance is null or p.bonEngagement.montantCreance =:montantCreance) ")
    List<DemandeCession> searchCreanceByMontantCreance(@Param("montantCreance") double montantCreance);




    /************** Filtering DemandeCession by multiple parameters **************/


//    @Query("select p from DemandeCession as p where" +
//            " (:referenceBE is null or p.bonEngagement.reference like %:referenceBE%) " +
//            "or (:numeroDemande is null or p.numeroDemande like %:numeroDemande% )" +
//            "or (:nomMarche is null or p.bonEngagement.nomMarche like %:nomMarche%)" +
//            "or (:statutLibelle is null or p.statut.libelle like %:statutLibelle%) "
//
//    )
//    List<DemandeCession> findDemandeCessionByMultiParams(@Param("referenceBE") String referenceBE,
//                                           @Param("numeroDemande") String numeroDemande,
//                                           @Param("nomMarche") String nomMarche,
//                                           @Param("statutLibelle") String statutLibelle);

    @Query("select p from DemandeCession as p where p.dateDemandeCession between :startDate and :endDate")
    List<DemandeCession> findDemandeCessionByMyDate(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);


    @Query("select p from DemandeCession as p where" +
            " (:statutLibelle is null or p.statut.libelle like %:statutLibelle%) ")
    List<DemandeCession> findByLibelleStatutDemande(@Param("statutLibelle") String statutLibelle);



    @Query(nativeQuery = true,
            value = "select * from public.recherche_creance(:raison_social,:montant_creance,:nom_marche, :statut_libelle,:decote ,:start_date_d, :end_date_d, :start_date_m, :end_date_m)")
    List<DemandeCession> searchCreanceByMultiParams(
                                                    @Param("raison_social") String raison_social,
                                                    @Param("montant_creance") double montant_creance,
                                                    @Param("nom_marche") String nom_marche,
                                                    @Param("statut_libelle") String statut_libelle,
                                                    @Param("decote") double decote,
                                                    @Param("start_date_d") LocalDateTime start_date_d,
                                                    @Param("end_date_d") LocalDateTime end_date_d,
                                                    @Param("start_date_m") LocalDateTime start_date_m,
                                                    @Param("end_date_m") LocalDateTime end_date_m);






    @Query(nativeQuery = true,
            value = "select * from public.recherche_demande_de_cession(:reference_be, :numero_demande, :nom_marche, :statut_libelle, :start_date, :end_date)")
    List<DemandeCession> findDemandeCessionByMultiParams(@Param("reference_be") String reference_be,
                                                         @Param("numero_demande") String numero_demande,
                                                         @Param("nom_marche") String nom_marche,
                                                         @Param("statut_libelle") String statut_libelle,
                                                         @Param("start_date") LocalDateTime start_date,
                                                         @Param("end_date") LocalDateTime end_date);



//    @Query("select p from DemandeCession as p where" +
//            "(:nomMarche is null or p.bonEngagement.nomMarche like %:nomMarche%) and p.statut.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')" +
//            "or (:raisonSocial is null or p.pme.raisonSocial like %:raisonSocial% ) and p.statut.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')" +
//            "or (:montantCreance is null or p.bonEngagement.montantCreance =:montantCreance) and p.statut.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')"+
//            "or (:statutLibelle is null or p.statut.libelle like %:statutLibelle%) and p.statut.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE') "
//
//    )
//    List<DemandeCession> searchCreanceByMultiParams(@Param("nomMarche") String nomMarche,
//                                                    @Param("raisonSocial") String raisonSocial,
//                                                    @Param("montantCreance") double montantCreance,
//                                                    @Param("statutLibelle") String statutLibelle);

}
