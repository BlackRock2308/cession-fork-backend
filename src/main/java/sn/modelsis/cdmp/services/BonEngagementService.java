package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.TypeDocument;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface BonEngagementService {

    /**
     *
     * @param bonEngagement
     * @return
     */
    BonEngagement save(BonEngagement bonEngagement);

    /**
     *
     * @param pageable
     * @return
     */
    List<BonEngagement> findAll();

    /**
     *
     * @param id
     * @return
     */
    Optional<BonEngagement> getBonEngagement(Long id);

    /**
     *
     * @param id
     */
    void delete(Long id);
    
    /**
     * Cette methode permet de charger un document lié à un bon d'engagement
     * 
     * @param beId de type Long c'est l'id du be
     * @param file de type {@link MultipartFile} c'est le fichier à charger
     * @param type de type {@link TypeDocument} qui est un ENUM
     * @return le type de retour est un optional de {@link BonEngagement}
     * @throws IOException gère l'exception
     */
    Optional<BonEngagement> upload(Long beId, MultipartFile file, TypeDocument type) throws IOException;


}
