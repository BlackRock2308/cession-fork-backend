package sn.modelsis.cdmp.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeAdhesionDto;

public interface DemandeAdhesionService {


    //DemandeAdhesion saveAdhesion(DemandeAdhesion demandeAdhesion);

    DemandeAdhesion saveAdhesion(DemandeAdhesionDto demandeAdhesionDto);

//    List<DemandeAdhesionDto> findAll();

    Page<DemandeAdhesionDto> findAll(Pageable pageable);

    Optional<DemandeAdhesionDto> findById(Long id);

    DemandeAdhesion rejetAdhesion(Long id);

    DemandeAdhesion validerAdhesion(Long id);


    /**
     * Cette methode permet de charger un document lié à une demande
     *
     * @param id de type Long c'est l'id de la demande
     * @param file de type {@link MultipartFile} c'est le fichier à charger
     * @param type de type {@link TypeDocument} qui est un ENUM
     * @return le type de retour est un optional de {@link Demande}
     * @throws IOException gère l'exception
     */
    Optional<DemandeAdhesion> upload(Long id, MultipartFile file, String type) throws IOException;


}
