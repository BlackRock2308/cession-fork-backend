package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statuts;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    public List<Demande> findAllByStatut_Libelle(Statuts statut);

    //public List<Demande> findDemandeByPme(Pme pme);

    //public List<Demande> findAllByIdPME(Long id);
}
