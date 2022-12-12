/**
 * 
 */
package sn.modelsis.cdmp.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Documents;
import sn.modelsis.cdmp.entities.TypeDocument;

/**
 * @author SNDIAGNEF Ce service permet d'ajouter un document
 */

public interface DocumentService {

  /**
   * Cette methode permet d'enregistrer un document
   * 
   * @param document est un objet de l'entité {@link Documents}
   * @return le type de retour est un optional de {@link Documents}
   */ 
  Documents save(Documents document);

  /**
   * Cette methode permet la récupération de tous les Documents
   * 
   * 
   * @return une liste de Documents
   */
  List<Documents> findAll();

  /**
   * Cette methode permet la récupération d'un document par son id
   * 
   * @param id de type Long
   * @return un document s'il existe
   */
  Optional<Documents> getDocument(Long id);

  /**
   * Cette methode permet de supprimer un document
   * 
   * @param id de type Long
   */
  void delete(Long id);

  void deleteDocument(Long id);

  /**
   * Cette methode permet de charger un document
   * 
   * @param file de type {@link MultipartFile} c'est le fichier à charger
   * @param provenanceId l'id de la classe où appartient le document
   * @param provenance le nom de la classe où appartient le document
   * @param type de type {@link TypeDocument} qui est un ENUM
   * @return le type de retour est un optional de {@link Documents}
   * @throws IOException gère l'exception
   */
  Documents upload(MultipartFile file, Long provenanceId, String provenance, TypeDocument type)
      throws IOException;

  /**
   * Renvoie le fichier par id de la classe
   * 
   * @param id de type Long
   * @return retourne un fichier
   */
  Resource loadFile(Long id);

  /**
   * Renvoie le fichier à partir de son lien
   * 
   * @param path de type string désigne le lien vers le fichier
   * @return retourne un fichier
   */
  Resource loadFile(String path);

}
