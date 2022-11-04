package sn.modelsis.cdmp.dbPersist;

import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Roles;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
public class PersitUsers {

    private final RoleRepository roleRepository;

    private final UtilisateurRepository utilisateurRepository;

    private final PmeRepository pmeRepository;

    public PersitUsers(RoleRepository roleRepository,
                       UtilisateurRepository utilisateurRepository,
                       PmeRepository pmeRepository) {
        this.roleRepository = roleRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.pmeRepository = pmeRepository;

        Role DG = new Role();
        DG.setLibelle("DG");

        Role PME = new Role();
        PME.setLibelle("PME");

        Role CGR = new Role();
        CGR.setLibelle("CGR");

        Role ORDONNATEUR = new Role();
        ORDONNATEUR.setLibelle("ORDONNATEUR");

        Role COMPTABLE = new Role();
        COMPTABLE.setLibelle("COMPTABLE");

//        Set<Role> roleList = new HashSet<>();
//        roleList.add(COMPTABLE);
//        roleList.add(DG);
//        roleList.add(PME);
//        roleList.add(CGR);
//        roleList.add(ORDONNATEUR);

        //roleRepository.saveAllAndFlush(roleList);

        Set<Role> pmeRoles = new HashSet<>();
        pmeRoles.add(PME);

        //roleRepository.saveAllAndFlush(pmeRoles);

        Set<Role> dgRoles = new HashSet<>();
        dgRoles.add(DG);

        //roleRepository.saveAllAndFlush(dgRoles);

        Set<Role> comptableRoles = new HashSet<>();
        dgRoles.add(COMPTABLE);

        //roleRepository.saveAllAndFlush(comptableRoles);

        Set<Role> cgrRoles = new HashSet<>();
        cgrRoles.add(CGR);

        //roleRepository.saveAllAndFlush(cgrRoles);

        Set<Role> ordonnateurRoles = new HashSet<>();
        ordonnateurRoles.add(ORDONNATEUR);

        //roleRepository.saveAllAndFlush(ordonnateurRoles);

        Utilisateur dg = new Utilisateur();
        dg.setAdresse("Mermoz");
        dg.setCodePin("123456");
        dg.setPassword("passer");
        dg.setPrenom("DG");
        dg.setEmail("dg@gmail.com");
        dg.setRoles(dgRoles);

        utilisateurRepository.save(dg);

        Utilisateur pme = new Utilisateur();
        pme.setAdresse("Mermoz");
        pme.setCodePin("123456");
        pme.setPassword("passer");
        pme.setPrenom("PME");
        pme.setEmail("pme@gmail.com");
        pme.setRoles(pmeRoles);

        Utilisateur cgr = new Utilisateur();
        cgr.setAdresse("Mermoz");
        cgr.setCodePin("123456");
        cgr.setPassword("passer");
        cgr.setPrenom("CGR");
        cgr.setEmail("cgr@gmail.com");
        cgr.setRoles(cgrRoles);

        utilisateurRepository.saveAndFlush(cgr);

        Utilisateur comptable = new Utilisateur();
        comptable.setAdresse("Mermoz");
        comptable.setCodePin("123456");
        comptable.setPassword("passer");
        comptable.setPrenom("COMPTABLE");
        comptable.setEmail("comptable@gmail.com");
        comptable.setRoles(comptableRoles);

        utilisateurRepository.saveAndFlush(comptable);

        Utilisateur ordonnateur = new Utilisateur();
        ordonnateur.setAdresse("Mermoz");
        ordonnateur.setCodePin("123456");
        ordonnateur.setPassword("passer");
        ordonnateur.setPrenom("ORDONNATEUR");
        ordonnateur.setEmail("ordonnateur@gmail.com");
        ordonnateur.setRoles(ordonnateurRoles);

        utilisateurRepository.saveAndFlush(ordonnateur);


        //utilisateurRepository.saveAllAndFlush(utilisateurList);

        //LocalDateTime date = new LocalDateTime();
        Pme pme1 = new Pme();
        pme1.setFormeJuridique("Société á responsabilité limitée");
        pme1.setAdressePME("Dakar Point E");
        pme1.setEmail("pme@gmail.com");
        pme1.setActivitePrincipale("Solution Cloud and Big Data");
        //pme1.setDateAdhesion();
        pme1.setUtilisateur(pme);
        pmeRepository.save(pme1);




    }
}
