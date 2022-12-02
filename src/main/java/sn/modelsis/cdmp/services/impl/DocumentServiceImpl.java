/**
 * 
 */
package sn.modelsis.cdmp.services.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.BEDocuments;
import sn.modelsis.cdmp.entities.ConventionDocuments;
import sn.modelsis.cdmp.entities.DPaiementDocuments;
import sn.modelsis.cdmp.entities.DemandeDocuments;
import sn.modelsis.cdmp.entities.Documents;
import sn.modelsis.cdmp.entities.PMEDocuments;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.repositories.DocumentsRepository;
import sn.modelsis.cdmp.services.DocumentService;

/**
 * @author SNDIAGNEF
 *
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

  @Value("${server.document_folder}")
  private String documentFolder;

  @Autowired
  private DocumentsRepository documentRepository;

  @Autowired
  private FileHandlerService fileService;

  @Override
  public Documents save(Documents document) {
    return documentRepository.save(document);
  }

  @Override
  public List<Documents> findAll() {

    return documentRepository.findAll();
  }

  @Override
  public Optional<Documents> getDocument(Long id) {
    return documentRepository.findById(id);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    documentRepository.deleteById(id);

  }

  @Override
  public void deleteDocument(Long id) {
    documentRepository.deleteDocument(id);
  }

  @Override
  public Documents upload(MultipartFile file, Long provenanceId, String provenance,
      TypeDocument type) throws IOException {
    Documents document = null;
    switch (provenance) {
      case BEDocuments.PROVENANCE:
        document = new BEDocuments();
        document.setDateSauvegarde(LocalDateTime.now());
        document.setTypeDocument(type);
        document.setProvenance(provenance);
        document.setNom(file.getOriginalFilename());
        document.setIdprovenance(provenanceId);
        String fileUrl = fileService.fileUpload(file,
            String.format(BEDocuments.FOLDER_PATH, provenanceId), file.getOriginalFilename());
        document.setUrlFile(fileUrl);
        break;
      case DemandeDocuments.PROVENANCE:
        document = new DemandeDocuments();
        document.setDateSauvegarde(LocalDateTime.now());
        document.setTypeDocument(type);
        document.setProvenance(provenance);
        document.setNom(file.getOriginalFilename());
        document.setIdprovenance(provenanceId);
        String esFileUrl = fileService.fileUpload(file,
            String.format(DemandeDocuments.FOLDER_PATH, provenanceId), file.getOriginalFilename());
        document.setUrlFile(esFileUrl);
        break;
      case ConventionDocuments.PROVENANCE:
        document = new ConventionDocuments();
        document.setDateSauvegarde(LocalDateTime.now());
        document.setTypeDocument(type);
        document.setProvenance(provenance);
        document.setNom(file.getOriginalFilename());
        document.setIdprovenance(provenanceId);
        String moudharabaFileUrl = fileService.fileUpload(file,
            String.format(ConventionDocuments.FOLDER_PATH, provenanceId),
            file.getOriginalFilename());
        document.setUrlFile(moudharabaFileUrl);
        break;
      case PMEDocuments.PROVENANCE:
        document = new PMEDocuments();
        document.setDateSauvegarde(LocalDateTime.now());
        document.setTypeDocument(type);
        document.setProvenance(provenance);
        document.setNom(file.getOriginalFilename());
        document.setIdprovenance(provenanceId);
        String raFileUrl = fileService.fileUpload(file,
            String.format(PMEDocuments.FOLDER_PATH, provenanceId), file.getOriginalFilename());
        document.setUrlFile(raFileUrl);
        break;
      case DPaiementDocuments.PROVENANCE:
          document = new DPaiementDocuments();
          document.setDateSauvegarde(LocalDateTime.now());
          document.setTypeDocument(type);
          document.setProvenance(provenance);
          document.setNom(file.getOriginalFilename());
          document.setIdprovenance(provenanceId);
          String dpFileUrl = fileService.fileUpload(file,
              String.format(DPaiementDocuments.FOLDER_PATH, provenanceId), file.getOriginalFilename());
          document.setUrlFile(dpFileUrl);
          break;
      default:
        break;
    }

    return document;
  }

  @Override
  public Resource loadFile(Long id) {
    Documents document = documentRepository.getReferenceById(id);
    try {
      Path filePath = Path.of(document.getUrlFile());
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new CustomException("File not found either deleted or moved");
      }
    } catch (MalformedURLException ex) {
      throw new CustomException("File not found either deleted or moved");
    }
  }

  @Override
  public Resource loadFile(String path) {
    try {
      Path filePath = Path.of(path);
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new CustomException("File not found either deleted or moved");
      }
    } catch (MalformedURLException ex) {
      throw new CustomException("File not found either deleted or moved");
    }
  }

}
