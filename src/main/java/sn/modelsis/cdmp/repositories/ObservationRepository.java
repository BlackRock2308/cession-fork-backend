package sn.modelsis.cdmp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.modelsis.cdmp.entities.Observation;

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
    Observation findDistinctFirstByDemandeIdDemandeAndStatut_Code(Long idDemande, String code);
}
