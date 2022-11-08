package sn.modelsis.cdmp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException  extends RuntimeException {

    public static final String DEFAUL_MESSAGE="Impossible de retrouver l'élément recherché";
    public static final String PME_BY_ID="Impossible de retrouver une PME avec l'identifiant %s";
    public static final String CREANCE_BY_ID="Impossible de retrouver une Creance avec l'identifiant %s";
    public static final String DEMANDE_CESSION_BY_ID="Impossible de retrouver une demande de cession l'identifiant %s";
    public static final String DEMANDE_ADHESION_ID="Impossible de retrouver une demande d'adhesion d'identifiants %s";
    public static final String PAIEMENT_BY_ID="Impossible de retrouver le paiement d'identifiants %s";
    public static final String  BONENGAGEMENT_BY_ID="Impossible de retrouver le bon d'engagement avec l'identifiant %s";
    public static final String OBSERVATION_BY_ID="Impossible de retrouver une observation avec l'identifiant %s";
    public static final String DOCUEMENT_BY_ID="Impossible de retrouver  une societe avec l'identifiant %s";

    public ItemNotFoundException() {
        super(DEFAUL_MESSAGE);
    }
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ItemNotFoundException(String message) {
        super(message);
    }
    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }

    public static String format(String template, String ...args) {
        return String.format(template,args);
    }


}
