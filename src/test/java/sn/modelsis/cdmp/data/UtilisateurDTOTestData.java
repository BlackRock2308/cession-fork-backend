package sn.modelsis.cdmp.data;

import sn.modelsis.cdmp.entities.Utilisateur;
import sn.modelsis.cdmp.entitiesDtos.UtilisateurDto;

public class UtilisateurDTOTestData extends TestData{

    public static UtilisateurDto defaultDTO(){
        return UtilisateurDto
                .builder()
                .idUtilisateur(Default.id)
                .adresse(Default.adresse)
                .codePin(Default.codePin)
                .email(Default.email)
                .password(Default.password)
                .prenom(Default.prenomRepresentant)
                .build();
    }

    public static UtilisateurDto updatedDTO(){
        return UtilisateurDto
                .builder()
                .idUtilisateur(Update.id)
                .codePin(Default.codePin)
                .adresse(Update.adresse)
                .email(Update.email)
                .password(Update.password)
                .prenom(Update.prenomRepresentant)
                .build();
    }

    public static Utilisateur defaultEntity(){
        return Utilisateur
                .builder()
                .codePin(Default.codePin)
                .idUtilisateur(Default.id)
                .adresse(Default.adresse)
                .email(Default.email)
                .password(Default.password)
                .prenom(Default.prenomRepresentant)
                .build();
    }
}
