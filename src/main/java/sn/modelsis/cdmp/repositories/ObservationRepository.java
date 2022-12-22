package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.modelsis.cdmp.entities.Observation;
import sn.modelsis.cdmp.entities.Utilisateur;

import java.util.List;

/**
 * @author SNDIAGNEF
 *
 */
public interface ObservationRepository extends JpaRepository<Observation, Long> {


    List<Observation> findAllObservationByDemandeIdDemande(Long IdDemande);

    /*@Query(
            value = "SELECT * FROM public.observation  INNER JOIN "
                    +"public.demande usRole ON usRole.utilisateur_idutilisateur =idutilisateur "
                    + "    WHERE usRole.roles_id = :idRole LIMIT 1",
            nativeQuery = true)
    Utilisateur findByRoleLibelle(Long idRole);*/
    List<Observation> findDistinctByDemandeIdDemandeAndStatut_Code(Long idDemande, String code);
}
