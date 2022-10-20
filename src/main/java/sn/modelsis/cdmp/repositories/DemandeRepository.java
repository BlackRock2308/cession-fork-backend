package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.Statuts;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    public List<Demande> findAllByStatut_Libelle(Statuts statut);
}
