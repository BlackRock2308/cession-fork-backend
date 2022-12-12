package sn.modelsis.cdmp.util;

import lombok.NoArgsConstructor;
import sn.modelsis.cdmp.entities.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@NoArgsConstructor
public class TestUtil {
    private static final Random generator = new Random();

    /**
     * Generate random double
     *
     * @return a double number
     */
    public static double getRandomDouble() {
        return generator.nextDouble();
    }

    /**
     * Generate a random string
     *
     * @param count the number of character of the string
     * @return a random string
     */
    public static String randomString(int count) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {
            int character = generator.nextInt(36);
            builder.append(characters.charAt(character));
        }

        return builder.toString();
    }

    public static DemandeAdhesion createDemandeAdhesion(){
        DemandeAdhesion dA = new DemandeAdhesion();
        //dA.setStatut(s);
        dA.setDateDemandeAdhesion(new Date());
        return  dA;
    }

    public  static Utilisateur createUtilisateur(Role role){
        Utilisateur user = new Utilisateur();
        user.getRoles().add(role);
        user.setEmail("sndiaye@modelsis.sn");
        return user;
    }

    public static Pme createPme(Utilisateur user){
        Pme pme = new Pme();
        pme.setUtilisateur(user);
        return pme;
    }

    public static BonEngagement createBonEngagement(){
        BonEngagement bon = new BonEngagement();
        bon.setObjetDepense(randomString(10));
        bon.setModeReglement(randomString(10));
        bon.setDesignationBeneficiaire(randomString(10));
        bon.setExercice("2021");
        bon.setMontantCreance(getRandomDouble());
        bon.setDatebonengagement(LocalDateTime.now());
        return bon;
    }

    public static DemandeCession createDemandeCession(BonEngagement bon){
        DemandeCession dC = new DemandeCession();
        dC.setDateDemandeCession(LocalDateTime.now());
        dC.setBonEngagement(bon);
       // dC.setStatut(s);
        return dC;
    }

    public static Convention createConvention(ParametrageDecote pD,DemandeCession dC, Utilisateur user){
        Convention c = new Convention();
        c.setActiveConvention(true);
        c.setDateConvention(LocalDateTime.now());
        c.setDecote(pD);
        c.setDemandeCession(dC);
        c.setUtilisateur(user);
        return c;
    }

    public static ParametrageDecote createParametrageDecote(){
        ParametrageDecote pD = new ParametrageDecote();
        return pD;
    }

    public static Paiement createPaiement(DemandeCession dC){
        Paiement p = new Paiement();
        p.setDemandeCession(dC);
        return p;
    }

    public static DetailPaiement createDetailPaiement(Paiement p){
        DetailPaiement dP =new DetailPaiement();
        dP.setDatePaiement(LocalDateTime.now());
        dP.setMontant(getRandomDouble());
        dP.setReference(randomString(10));
        dP.setPaiement(p);
        return dP;
    }

    public static Observation createObservation(Utilisateur user, Statut s, Demande d, String libelle){
        Observation ob = new Observation();
        ob.setUtilisateur(user);
        ob.setStatut(s);
        ob.setDateObservation(LocalDateTime.now());
        ob.setDemande(d);
        ob.setLibelle(libelle);
        return ob;
    }

}
