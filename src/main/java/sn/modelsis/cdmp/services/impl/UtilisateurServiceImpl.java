package sn.modelsis.cdmp.services.impl;


import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;
import sn.modelsis.cdmp.services.UtilisateurService;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    final private UtilisateurRepository utilisateurRepository ;

    final private RoleRepository roleRepository ;
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur findByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        return utilisateur;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        var roles = utilisateur.getRoles();
        var newRoles = utilisateur.getRoles();
        roles.forEach(role -> newRoles.add(roleRepository.save(role)));
        utilisateur.setRoles(newRoles);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void delete(Long utilisateurId) {
     utilisateurRepository.deleteById(utilisateurId);
    }
}
