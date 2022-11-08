package sn.modelsis.cdmp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.BEDocuments;
import sn.modelsis.cdmp.entities.BonEngagement;
import sn.modelsis.cdmp.entities.DemandeAdhesion;
import sn.modelsis.cdmp.entities.TypeDocument;
import sn.modelsis.cdmp.exceptions.ItemExistsException;
import sn.modelsis.cdmp.exceptions.ItemNotFoundException;
import sn.modelsis.cdmp.repositories.BonEngagementRepository;
import sn.modelsis.cdmp.services.BonEngagementService;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.util.ExceptionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonEngagementImpl implements BonEngagementService {

    private final BonEngagementRepository bonEngagementRepository;

    private final DocumentService documentService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BonEngagement save(BonEngagement bonEngagement) {
        return bonEngagementRepository.saveAndFlush(bonEngagement);
    }

    @Override
    public List<BonEngagement> findAll(){
        return bonEngagementRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BonEngagement> getBonEngagement(Long id) {
        final Optional<BonEngagement> bonEngagement = bonEngagementRepository.findById(id);
        ExceptionUtils.presentOrThrow(bonEngagement, ItemNotFoundException.BONENGAGEMENT_BY_ID, id.toString());
        return bonEngagement;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        final Optional<BonEngagement> bonEngagement = bonEngagementRepository.findById(id);
        ExceptionUtils.presentOrThrow(bonEngagement, ItemNotFoundException.BONENGAGEMENT_BY_ID, id.toString());
        if(bonEngagement.isPresent()){
            bonEngagementRepository.deleteById(bonEngagement.get().getIdBonEngagement());
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
