package sn.modelsis.cdmp.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ItemExistsException extends RuntimeException {

    public static final String DEFAUL_MESSAGE="Un des éléments que vous tentez d'jouter existe déjà ";
    public static final String NINEA_PME_EXIST="Le Ninea  %s  existe déjà ";
    public static final String BONENGAGEMENT_EXISTS="Le Bon d'engagement  %s  existe déjà ";
    public static final String MAIL_EXISTS="Le mail  %s  existe déjà ";
    public static final String DEMANDE_ADHESION_EXISTS="La demande d'adhesion  %s  existe déjà ";
    public static final String DEMANDE_CESSION_EXISTS="La demande de cession  %s  existe déjà ";
    public static final String PHONE_EXISTS="Les numéros de téléphone  %s  existent déjà ";
    public static final String USER_EXIST="L'utilisateur %s  existe déjà  ";
    public static final String NAMES_EXIST="Les noms  %s  existe déjà ";

    public ItemExistsException() {
        super(DEFAUL_MESSAGE);
    }
    public ItemExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public ItemExistsException(String message) {
        super(message);
    }
    public ItemExistsException(Throwable cause) {
        super(cause);
    }

    public static String format(String template, String ...args) {
        return String.format(template,args);
    }
}

