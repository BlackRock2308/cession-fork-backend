package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DemandeDocuments;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.repositories.DemandeRepository;
import sn.modelsis.cdmp.services.DemandeService;
import sn.modelsis.cdmp.services.DocumentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeServiceImpl implements DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;
    
    @Autowired
    private DocumentService documentService;

    @Override
    public Demande save(Demande demande){
        return demandeRepository.save(demande);
    }

    @Override
    public List<Demande> findAll(){
        return demandeRepository.findAll();
    }

    @Override
    public Optional<Demande> getDemande(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        demandeRepository.deleteById(id);
    }
    
    @Override
    public Optional<Demande> upload(Long demandeId, MultipartFile file, TypeDocument type)
        throws IOException {
      Optional<Demande> demande = demandeRepository.findById(demandeId);
      if (demande.isPresent()) {

        DemandeDocuments doc = (DemandeDocuments) documentService.upload(file, demandeId,
                DemandeDocuments.PROVENANCE, type);
        demande.get().getDocuments().add(doc);

        return Optional.of(demandeRepository.save(demande.get()));

      }
      return demande;
    }
}
