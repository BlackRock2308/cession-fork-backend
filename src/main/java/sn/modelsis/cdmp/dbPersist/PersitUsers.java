package sn.modelsis.cdmp.dbPersist;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.*;

@Transactional
public class PersitUsers {


    private final RoleRepository roleRepository;

    private final UtilisateurRepository utilisateurRepository;

    private final PmeRepository pmeRepository;

    final  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

        Role DRC = new Role();
        DRC.setLibelle("DRC");

        Role ORDONNATEUR = new Role();
        ORDONNATEUR.setLibelle("ORDONNATEUR");

        Role DAF = new Role();
        DAF.setLibelle("DAF");
        
        Role DSEAR = new Role();
        DSEAR.setLibelle("DSEAR");
        
        Role JURISTE = new Role();
        JURISTE.setLibelle("JURISTE");

        Set<Role> pmeRoles = new HashSet<>();
        pmeRoles.add(PME);

        Set<Role> dgRoles = new HashSet<>();
        dgRoles.add(DG);

        Set<Role> juristeRoles = new HashSet<>();
        juristeRoles.add(JURISTE);
       
        Set<Role> dsearRoles = new HashSet<>();
        dsearRoles.add(DSEAR);
        
        Set<Role> dafRoles = new HashSet<>();
        dafRoles.add(DAF);

        Set<Role> cgrRoles = new HashSet<>();
        cgrRoles.add(DRC);

        Set<Role> ordonnateurRoles = new HashSet<>();
        ordonnateurRoles.add(ORDONNATEUR);

        Utilisateur dg = new Utilisateur();
        dg.setAdresse("Mermoz");
        dg.setCodePin("123456");
        dg.setPassword(passwordEncoder.encode("passer"));
        dg.setPrenom("DG");
        dg.setEmail("dg@gmail.com");
        dg.setRoles(dgRoles);

      //  utilisateurRepository.save(dg);

        Utilisateur pme = new Utilisateur();
        pme.setAdresse("Mermoz");
        pme.setCodePin("123456");
        pme.setPassword(passwordEncoder.encode("passer"));
        pme.setPrenom("PME");
        pme.setEmail("pme@gmail.com");
        pme.setRoles(pmeRoles);

        Utilisateur drc = new Utilisateur();
        drc.setAdresse("Mermoz");
        drc.setCodePin("123456");
        drc.setPassword(passwordEncoder.encode("passer"));
        drc.setPrenom("DRC");
        drc.setEmail("drc@gmail.com");
        drc.setRoles(cgrRoles);

      //  utilisateurRepository.saveAndFlush(drc);

        Utilisateur juriste = new Utilisateur();
        juriste.setAdresse("Mermoz");
        juriste.setCodePin("123456");
        juriste.setPassword(passwordEncoder.encode("passer"));
        juriste.setPrenom("JURISTE");
        juriste.setEmail("juriste@gmail.com");
        juriste.setRoles(juristeRoles);

      //  utilisateurRepository.saveAndFlush(juriste);
        
        Utilisateur dsear = new Utilisateur();
        dsear.setAdresse("Mermoz");
        dsear.setCodePin("123456");
        dsear.setPassword(passwordEncoder.encode("passer"));
        dsear.setPrenom("DSEAR");
        dsear.setEmail("dsear@gmail.com");
        dsear.setRoles(dsearRoles);

       // utilisateurRepository.saveAndFlush(dsear);

        Utilisateur daf = new Utilisateur();
        daf.setAdresse("Mermoz");
        daf.setCodePin("123456");
        daf.setPassword(passwordEncoder.encode("passer"));
        daf.setPrenom("DAF");
        daf.setEmail("daf@gmail.com");
        daf.setRoles(dafRoles);

       // utilisateurRepository.saveAndFlush(daf);
        
        Utilisateur ordonnateur = new Utilisateur();
        ordonnateur.setAdresse("Mermoz");
        ordonnateur.setCodePin("123456");
        ordonnateur.setPassword(passwordEncoder.encode("passer"));
        ordonnateur.setPrenom("ORDONNATEUR");
        ordonnateur.setEmail("ordonnateur@gmail.com");
        ordonnateur.setRoles(ordonnateurRoles);

      //  utilisateurRepository.saveAndFlush(ordonnateur);


        LocalDateTime date = LocalDateTime.now();
        Pme pme1 = new Pme();
        pme1.setFormeJuridique("Société á responsabilité limitée");
        pme1.setAdressePME("Dakar Point E");
        pme1.setNinea("123456789088");
        pme1.setRccm("SN DK 2898 Y 9989");
        pme1.setTelephonePME("339809876");
        pme1.setEmail("pme@gmail.com");
        pme1.setActivitePrincipale("Solution Cloud and Big Data");
        pme1.setDateAdhesion(date);
        pme1.setUtilisateur(pme);
       // pmeRepository.save(pme1);




    }
}
