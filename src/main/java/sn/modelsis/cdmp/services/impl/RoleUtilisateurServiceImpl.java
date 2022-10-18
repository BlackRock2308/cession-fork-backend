package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import sn.modelsis.cdmp.entities.RoleUtilisateur;
import sn.modelsis.cdmp.repositories.RoleUtilisateurRepository;
import sn.modelsis.cdmp.services.RoleUtilisateurService;

import java.util.List;
import java.util.Optional;

public class RoleUtilisateurServiceImpl implements RoleUtilisateurService {

    @Autowired
    private RoleUtilisateurRepository roleUtilisateurRepository;

    @Override
    public RoleUtilisateur save(RoleUtilisateur roleUtilisateur) {
        return roleUtilisateurRepository.save(roleUtilisateur);
    }

    @Override
    public List<RoleUtilisateur> findAll(){
        return roleUtilisateurRepository.findAll();
    }

    @Override
    public Optional<RoleUtilisateur> getRoleUser(Long id) {
        return roleUtilisateurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        roleUtilisateurRepository.deleteById(id);

    }

}
