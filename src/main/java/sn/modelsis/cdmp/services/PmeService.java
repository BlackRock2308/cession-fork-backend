package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entities.Statut;
import sn.modelsis.cdmp.entities.TypeDocument;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface PmeService {
    /**
     * @param pme
     * @return
     */
    Pme save(Pme pme);

    /**
     * @return
     */
    List<Pme> findAll();

    /**
     * @return
     */
    Pme findPmeByEmail(String email);

    /**
     * @param id
     * @return
     */
    Optional<Pme> getPme(Long id);

    /**
     * @param id
     */
    void delete(Long id);
    
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


}
