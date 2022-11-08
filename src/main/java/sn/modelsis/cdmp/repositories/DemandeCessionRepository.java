package sn.modelsis.cdmp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.modelsis.cdmp.entities.DemandeCession;

public interface DemandeCessionRepository extends JpaRepository<DemandeCession,Long> {


    public Page<DemandeCession> findAllByStatut_Libelle(org.springframework.data.domain.Pageable pageable, String statut);

    public List<DemandeCession> findAllByPmeIdPME(Long id);

    @Query("select p from DemandeCession  p where p.idDemande=:idDemande")
    DemandeCession findByDemandeId(Long idDemande);







}
