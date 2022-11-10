package sn.modelsis.cdmp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.Statuts;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    List<Demande> findAllByStatut_Libelle(Statuts statut);
    Optional<Demande> findFirstByOrderByIdDemandeDesc();

    //public List<Demande> findDemandeByPme(Pme pme);

    //public List<Demande> findAllByIdPME(Long id);
}
