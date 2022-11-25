package sn.modelsis.cdmp.dbPersist;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Role;
import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.repositories.RoleRepository;
import sn.modelsis.cdmp.repositories.UtilisateurRepository;

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

        Role DG = roleRepository.findByLibelle("DG");
      //  DG.setLibelle("DG");

        Role PME = roleRepository.findByLibelle("PME");
       // PME.setLibelle("PME");

        Role DRC = roleRepository.findByLibelle("DRC");
       // DRC.setLibelle("DRC");

        Role ORDONNATEUR = roleRepository.findByLibelle("ORDONNATEUR");
        //ORDONNATEUR.setLibelle("ORDONNATEUR");

        Role DAF = roleRepository.findByLibelle("DAF");
      //  DAF.setLibelle("DAF");
        
        Role DSEAR = roleRepository.findByLibelle("DSEAR");
      //  DSEAR.setLibelle("DSEAR");

        Role JURISTE =roleRepository.findByLibelle("JURISTE");
       // JURISTE.setLibelle("JURISTE");

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
        dg.setPrenom("Abdou");
        dg.setNom("MBACKE");
        dg.setEmail("dg@gmail.com");
        dg.setRoles(dgRoles);
        
        Utilisateur user = utilisateurRepository.findUtilisateurByEmail("dg@gmail.com");
        
        if(user == null) {
            utilisateurRepository.save(dg);
        }
        

        Utilisateur pme = new Utilisateur();
        pme.setAdresse("Mermoz");
        pme.setCodePin("123456");
        pme.setPassword(passwordEncoder.encode("passer"));
        pme.setPrenom("Alassane");
        pme.setNom("NDIAYE");
        pme.setEmail("andiaye@modelsis.sn");
        pme.setRoles(pmeRoles);

        Utilisateur drc = new Utilisateur();
        drc.setAdresse("Mermoz");
        drc.setCodePin("123456");
        drc.setPassword(passwordEncoder.encode("passer"));
        drc.setPrenom("Sokhna");
        drc.setNom("DIOP");
        drc.setEmail("drc@gmail.com");
        drc.setRoles(cgrRoles);

        Utilisateur user1 = utilisateurRepository.findUtilisateurByEmail("drc@gmail.com");
        
        if(user1 == null) {
            utilisateurRepository.saveAndFlush(drc);
        }
        

        Utilisateur juriste = new Utilisateur();
        juriste.setAdresse("Mermoz");
        juriste.setCodePin("123456");
        juriste.setPassword(passwordEncoder.encode("passer"));
        juriste.setPrenom("Oumar");
        juriste.setNom("NDIAYE");
        juriste.setEmail("juriste@gmail.com");
        juriste.setRoles(juristeRoles);
        Utilisateur user2 = utilisateurRepository.findUtilisateurByEmail("juriste@gmail.com");
        
        if(user2 == null) { 
        utilisateurRepository.saveAndFlush(juriste);
        }
        Utilisateur dsear = new Utilisateur();
        dsear.setAdresse("Mermoz");
        dsear.setCodePin("123456");
        dsear.setPassword(passwordEncoder.encode("passer"));
        dsear.setPrenom("Mbaye");
        dsear.setNom("SENE");
        dsear.setEmail("dsear@gmail.com");
        dsear.setRoles(dsearRoles);

        Utilisateur user3 = utilisateurRepository.findUtilisateurByEmail("dsear@gmail.com");
        
        if(user3 == null) {
        utilisateurRepository.saveAndFlush(dsear);
        }
        Utilisateur daf = new Utilisateur();
        daf.setAdresse("Mermoz");
        daf.setCodePin("123456");
        daf.setPassword(passwordEncoder.encode("passer"));
        daf.setPrenom("Soda");
        daf.setNom("NDIAYE");
        daf.setEmail("daf@gmail.com");
        daf.setRoles(dafRoles);
        Utilisateur user4 = utilisateurRepository.findUtilisateurByEmail("daf@gmail.com");
        
        if(user4 == null) {
        utilisateurRepository.saveAndFlush(daf);
        }
        Utilisateur ordonnateur = new Utilisateur();
        ordonnateur.setAdresse("Mermoz");
        ordonnateur.setCodePin("123456");
        ordonnateur.setPassword(passwordEncoder.encode("passer"));
        ordonnateur.setPrenom("Ndèye");
        ordonnateur.setNom("NGOM");
        ordonnateur.setEmail("ordonnateur@gmail.com");
        ordonnateur.setRoles(ordonnateurRoles);
        Utilisateur user5 = utilisateurRepository.findUtilisateurByEmail("ordonnateur@gmail.com");
        
        if(user5 == null) {
        utilisateurRepository.saveAndFlush(ordonnateur);
        }

        Utilisateur user6 = utilisateurRepository.findUtilisateurByEmail("andiaye@modelsis.sn");
        
        if(user6 == null) {

        LocalDateTime date = LocalDateTime.now();
        Pme pme1 = new Pme();
        pme1.setFormeJuridique("Société á responsabilité limitée");
        pme1.setAdressePME("Dakar Point E");
        pme1.setNinea("123456789088");
        pme1.setRccm("SN DK 2898 Y 9989");
        pme1.setTelephonePME("339809876");
        pme1.setRaisonSocial("Fintech");
        pme1.setEmail("pme@gmail.com");
        pme1.setActivitePrincipale("Solution Cloud and Big Data");
        pme1.setDateAdhesion(date);
        pme1.setUtilisateur(pme);
        pmeRepository.save(pme1);
        }

    }
}
