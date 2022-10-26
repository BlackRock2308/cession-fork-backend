package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Utilisateur findUtilisateurByEmail(String email);
}
