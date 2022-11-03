package sn.modelsis.cdmp.services;

import org.springframework.web.multipart.MultipartFile;
import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;
import sn.modelsis.cdmp.entitiesDtos.DemandeCessionDto;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DemandeAdhesionService {

    /**
     *
     * @param demandeAdhesionDto
     * @return
     */

    DemandeAdhesion saveAdhesion(DemandeAdhesionDto demandeAdhesionDto);

    DemandeAdhesion rejetAdhesion(Long id);

    DemandeAdhesion validerAdhesion(Long id);
    List<DemandeAdhesionDto> findAll();
    Optional<DemandeAdhesion> findById(Long id);

    /**
     * Cette methode permet de charger un document lié à une demande
     *
     * @param id de type Long c'est l'id de la demande
     * @param file de type {@link MultipartFile} c'est le fichier à charger
     * @param type de type {@link TypeDocument} qui est un ENUM
     * @return le type de retour est un optional de {@link Demande}
     * @throws IOException gère l'exception
     */
    Optional<DemandeAdhesion> upload(Long id, MultipartFile file, TypeDocument type) throws IOException;


}
