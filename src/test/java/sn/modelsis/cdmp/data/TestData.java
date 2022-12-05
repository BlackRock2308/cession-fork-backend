package sn.modelsis.cdmp.data;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import sn.modelsis.cdmp.entities.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TestData {

    public TestData() {
    }

    public static final class Default {

        private Default() {
        }

        public static final Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        public static final int version = 1;
        public static final boolean enabled = true;
        public static final boolean deleted = false;
        public static final boolean atd = false;
        public static final boolean nantissement = false;
        public static final boolean hasninea = false;
        public static final boolean isactive = false;
        public static final String cniRepresentant = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String adressePME = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String telephonePME = RandomStringUtils.randomNumeric((0 + 30) / 2);
        public static final String email = RandomStringUtils.randomNumeric((0 + 30) / 2).concat("@ept.sn");
        public static final String prenomRepresentant = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String raisonSocial = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String nomRepresentant = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String rccm = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String ninea = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String activitePrincipale = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final int codepin = RandomUtils.nextInt();
        public static final String centreFiscal = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);


        public static final String formeJuridique = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String enseigne = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String location = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String registre = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String hash = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);

        public static final double montantCreance = RandomUtils.nextDouble();
        public static final String reference = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String naturePrestation = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String nomMarche = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);

        public static final String typeDepense = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final LocalDateTime datebonengagement = LocalDateTime.now();


        public static final LocalDateTime dateImmatriculation = LocalDateTime.now();
        public static final LocalDateTime dateCreation = LocalDateTime.now();
        public static final double valeurDecote = RandomUtils.nextDouble();
        public static final float valeurDecoteDG = RandomUtils.nextFloat();
        public static final Pme pme = new Pme();
        public static final Paiement paiement = new Paiement();
        public static final BonEngagement bonEngagement = new BonEngagement();
        public static final DetailPaiement detailPaiement = new DetailPaiement();
        public static final DemandeAdhesion demandeAdhesion = new DemandeAdhesion();
        public static final Observation observation = new Observation();
        private static final Set<Role> roles = new HashSet<>();
        private static final Statut statut = new Statut();
        public static final Demande demande = new Demande();
        private static final Set<Demande> demandes = new HashSet<>();
        private static final DemandeCession demandeCession = new DemandeCession();
    }


    public static final class Update {
        private Update() {
        }

        public static final Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        public static final int version = 1;
        public static final boolean enabled = true;
        public static final boolean deleted = false;
        public static final boolean atd = true;
        public static final boolean nantissement = true;
        public static final boolean hasninea = true;
        public static final boolean isactive = true;
        public static final String cniRepresentant = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String adressePME = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String telephonePME = RandomStringUtils.randomNumeric((0 + 50) / 2);
        public static final String email = RandomStringUtils.randomNumeric((0 + 50) / 2).concat("@gmail.com");
        public static final String prenomRepresentant = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String raisonSocial = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String nomRepresentant = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String rccm = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String ninea = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final int codepin = RandomUtils.nextInt();

        public static final String formeJuridique = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String enseigne = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String activitePrincipale = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String location = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final String registre = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);

        public static final String hash = RandomStringUtils.randomAlphanumeric((0 + 50) / 2);
        public static final LocalDateTime dateImmatriculation = LocalDateTime.now();


        public static final double montantCreance = RandomUtils.nextDouble();
        public static final String reference = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String naturePrestation = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String nomMarche = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final String typeDepense = RandomStringUtils.randomAlphanumeric((0 + 30) / 2);
        public static final LocalDateTime datebonengagement = LocalDateTime.now();

        public static final LocalDateTime dateCreation = LocalDateTime.now();

        public static final double valeurDecote = RandomUtils.nextDouble();
        public static final float valeurDecoteDG = RandomUtils.nextFloat();
        public static final Pme pme = new Pme();
        public static final Paiement paiement = new Paiement();
        public static final BonEngagement bonEngagement = new BonEngagement();
        public static final DetailPaiement detailPaiement = new DetailPaiement();
        public static final DemandeAdhesion demandeAdhesion = new DemandeAdhesion();
        public static final Observation observation = new Observation();
        private static final Set<Role> roles = new HashSet<>();
        private static final Statut statut = new Statut();
        public static final Demande demande = new Demande();
        private static final Set<Demande> demandes = new HashSet<>();
        private static final DemandeCession demandeCession = new DemandeCession();
    }
}
