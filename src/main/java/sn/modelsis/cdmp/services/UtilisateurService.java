package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.CreationComptePmeDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;

import java.util.List;

public interface UtilisateurService {

    /**
     *retourne la liste des utilisateurs
     *
     * @return  List Utilisateur
     */

    List<Utilisateur> getAll();

    /**
     ** cette methode permet de retrouver un
     * @Param email
     * @return   Utilisateur
     */

    Utilisateur findByEmail(String email);

    /**
     ** cette methode permet d'enregistrer un utilisateur
     * @Param utilisateur
     * @return   Utilisateur
     */

    Utilisateur save(Utilisateur utilisateur);
    /**
     ** cette methode permet d'enregistrer un utilisateur
     * @Param utilisateur
     * @return   Utilisateur
     */

    Utilisateur update(Utilisateur utilisateur);
    /**
     ** cette methode permet d'enregistrer un utilisateur
     * @Param utilisateur
     * @return   Utilisateur
     */

    void delete(Long utilisateurId);

    /**
     ** cette methode permet creer un utilisateur pour un pme.
     * @Param creationComptePmeDto
     * @return   creationComptePmeDto
     */

    PmeDto createComptePme(CreationComptePmeDto creationComptePmeDto) throws Exception;

}
