/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Convention;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.ConventionDto;


/**
 * @author SNDIAGNEF
 *
 */
public interface ConventionService {

    /**
     * 
     * @param conventionDto
     * @return
     */
    Convention save(ConventionDto conventionDto);

    /**
     *
     * @param conventionDto
     * @return
     */
    Convention corriger(ConventionDto conventionDto) ;

    /**
    * 
    * @return
    */
    List<Convention> findAll();

    /**
     * 
     * @param id
     * @return
     */
    Optional<Convention> getConvention(Long id);

    /**
     * 
     * @param id
     */
    void delete(Long id);
    
    
    /**
     * Cette methode permet de charger un document lié à une convention
     * 
     * @param id de type Long c'est l'id de convention
     * @param file de type {@link MultipartFile} c'est le fichier à charger
     * @param type de type {@link TypeDocument} qui est un ENUM
     * @return le type de retour est un optional de {@link Convention}
     * @throws IOException gère l'exception
     */
    Optional<Convention> upload(Long id, MultipartFile file, TypeDocument type) throws IOException;

    Convention updateValeurDecote(Long idConvention, double newValue);

    Convention transmettreConvention(Long id, Convention newConvention);


    void saveDocumentConventionSigner(Convention convention) throws IOException;

    void conventionSignerParPME(Long idConvention, Long idPME) ;
    void conventionSignerParDG(Long idConvention, Long idDG) ;



}
