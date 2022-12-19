package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sn.modelsis.cdmp.entities.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Utilisateur findUtilisateurByEmail(String email);
    @Query(
            value = "SELECT * FROM public.utilisateur  INNER JOIN "
                    +"public.utilisateur_roles usRole ON usRole.utilisateur_idutilisateur =idutilisateur "
                   + "    WHERE usRole.roles_id = :idRole LIMIT 1",
            nativeQuery = true)
    Utilisateur findByRoleLibelle(Long idRole);

}
