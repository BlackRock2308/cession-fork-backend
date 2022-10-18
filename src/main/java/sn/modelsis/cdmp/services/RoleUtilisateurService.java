package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.RoleUtilisateur;

import java.util.List;
import java.util.Optional;

public interface RoleUtilisateurService {

    /**
     * @param roleUtilisateur
     * @return
     */
    RoleUtilisateur save(RoleUtilisateur roleUtilisateur);

    /**
     * @return
     */
    List<RoleUtilisateur> findAll();

    /**
     * @param id
     * @return
     */
    Optional<RoleUtilisateur> getRoleUser(Long id);

    /**
     * @param id
     */
    void delete(Long id);
}
