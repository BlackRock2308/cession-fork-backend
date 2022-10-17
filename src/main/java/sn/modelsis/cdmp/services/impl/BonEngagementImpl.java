package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.BEDocuments;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.DocumentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BonEngagementImpl implements BonEngagementService {
    
    @Autowired
    private BonEngagementRepository bonEngagementRepository;
    
    @Autowired
    private DocumentService documentService;

    @Override
    public BonEngagement save(BonEngagement bonEngagement) {
        return bonEngagementRepository.save(bonEngagement);
    }

    @Override
    public List<BonEngagement> findAll(){
        return bonEngagementRepository.findAll();
    }

    @Override
    public Optional<BonEngagement> getBonEngagement(Long id) {
        return bonEngagementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        bonEngagementRepository.deleteById(id);
    }
    
    @Override
    public Optional<BonEngagement> upload(Long id, MultipartFile file, TypeDocument type)
        throws IOException {
      Optional<BonEngagement> be = bonEngagementRepository.findById(id);
      if (be.isPresent()) {

        BEDocuments doc = (BEDocuments) documentService.upload(file, id,
            BEDocuments.PROVENANCE, type);
        be.get().getDocuments().add(doc);

        return Optional.of(bonEngagementRepository.save(be.get()));

      }
      return be;
    }
}
