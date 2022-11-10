package sn.modelsis.cdmp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
   private UtilisateurRepository utilisateurRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public List<Utilisateur> getAll(){return utilisateurRepository.findAll();}

    public Utilisateur createAdmin(Utilisateur utilisateur){
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setPrenom(utilisateur.getPrenom());
        utilisateur1.setNom(utilisateur.getNom());
        utilisateur1.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateur1.setTelephone(utilisateur.getTelephone());
        utilisateur1.setRoles(utilisateur.getRoles());
        utilisateur1.setEmail(utilisateur.getEmail());
        utilisateur1.setRoles(utilisateur.getRoles());
        return utilisateurRepository.save(utilisateur1);
    }

    public Utilisateur getByMail(String email){
        return utilisateurRepository.findUtilisateurByEmail(email);
    }

}
