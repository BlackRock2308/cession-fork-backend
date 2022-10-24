package sn.modelsis.cdmp.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.entitiesDtos.DemandeDto;

public interface DemandeService {
  /**
   *
   * @param demande
   * @return
   */
  Demande saveAdhesion(Demande demande);





  /**
   *
   * @return
   */
  List<Demande> findAll();

  List<Demande> findAllDemandesAdhesion();


  List<Demande> findAllNouvellesDemandes();

  List<Demande> findAllAnalyseRisque();

  List<Demande> findAllConventionsComptable();

  List<Demande> findAllPaiements();

  List<Demande> findAllConventionsOrdonnateur();

  List<Demande> findAllConventionsDG();

  List<Demande> findAllCreances();

  //demandes relatives à une pme
  List<Demande> findAllPMEDemandes();

  List<Demande> findAllConventionsPME();

  List<Demande> findAllPaiementsPME();



  /**
   *
   * @param id
   * @return
   */
  Optional<Demande> getDemande(Long id);

  /**
   *
   * @param id
   */
  void delete(Long id);
  
  /**
   * Cette methode permet de charger un document lié à une demande
   * 
   * @param id de type Long c'est l'id de la demande
   * @param file de type {@link MultipartFile} c'est le fichier à charger
   * @param type de type {@link TypeDocument} qui est un ENUM
   * @return le type de retour est un optional de {@link Demande}
   * @throws IOException gère l'exception
   */
  Optional<Demande> upload(Long id, MultipartFile file, TypeDocument type) throws IOException;



}
