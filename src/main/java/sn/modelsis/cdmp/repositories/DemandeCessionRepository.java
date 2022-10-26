package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Statuts;

import java.util.List;

public interface DemandeCessionRepository extends JpaRepository<DemandeCession,Long> {


    public List<DemandeCession> findAllByStatut_Libelle(Statuts statut);

    public List<DemandeCession> findAllByPmeIdPME(Long id);


}
