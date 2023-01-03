package sn.modelsis.cdmp.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.DetailPaiement;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DetailPaiementDto;

public interface DetailPaiementService {

    /**
     *
     * @param detailPaiement
     * @return
     */
    DetailPaiement paiementPME(DetailPaiement detailPaiement);

    DetailPaiement paiementCDMP(DetailPaiement detailPaiement);


    /**
     *
     * @return
     */
    List<DetailPaiement> findAll();

    /**
     *
     * @param id
     * @return
     */
    Optional<DetailPaiement> getDetailPaiement(Long id);

    /**
     *
     * @param id
     */
    void delete(Long id);
    
    /**
     * Cette methode permet de charger un document lié à un bon d'engagement
     * 
     * @param dpId de type Long c'est l'id du be
     * @param file de type {@link MultipartFile} c'est le fichier à charger
     * @param type de type {@link TypeDocument} qui est un ENUM
     * @return le type de retour est un optional de {@link DetailPaiement}
     * @throws IOException gère l'exception
     */
    Optional<DetailPaiement> upload(Long dpId, MultipartFile file, String type) throws IOException;


    Set<DetailPaiementDto> getDetailPaiementByTypePaiement(Long id, String typePaiement);

    double getSommePaiementByTypePaiement(Long id, String typePaiement);
}
