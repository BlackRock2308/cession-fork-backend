package sn.modelsis.cdmp.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.TypeDocument;

public interface PmeService {

    Pme savePme(Pme pme);
    List<Pme> findAllPme();

    Optional<Pme> getPme(Long id);

    void deletePme(Long id);

    Pme updatePme(Long id, Pme pme);

    Pme findPmeByEmail(String email);


    /**
     * Cette methode permet de charger un document lié à une pme
     * 
     * @param id de type Long c'est l'id du pme
     * @param file de type {@link MultipartFile} c'est le fichier à charger
     * @param type de type {@link TypeDocument} qui est un ENUM
     * @return le type de retour est un optional de {@link Pme}
     * @throws IOException gère l'exception
     */
    Optional<Pme> upload(Long id, MultipartFile file, TypeDocument type) throws IOException;


    Optional<Pme> getPmeByUtilisateur(Long id);
}
