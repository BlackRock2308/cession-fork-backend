package sn.modelsis.cdmp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sn.modelsis.cdmp.entities.*;
import sn.modelsis.cdmp.entitiesDtos.PmeDto;
import sn.modelsis.cdmp.repositories.PmeRepository;
import sn.modelsis.cdmp.services.DocumentService;
import sn.modelsis.cdmp.services.PmeService;
import sn.modelsis.cdmp.util.DtoConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PmeServiceImpl implements PmeService {

  @Autowired
  private PmeRepository pmeRepository;

  @Autowired
  private PmeRepository statutRepository;

  @Autowired
  private PmeRepository demandeRepository;
  
  @Autowired
  private DocumentService documentService;

  @Override
  public Pme save(Pme pme) {


    return  pmeRepository.save(pme);
  }


  @Override
  public List<Pme> findAll() {
    return pmeRepository.findAll();
  }

  @Override
  public Optional<Pme> getPme(Long id) {
    return pmeRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    pmeRepository.deleteById(id);
  }
  
  @Override
  public Optional<Pme> upload(Long id, MultipartFile file, TypeDocument type)
      throws IOException {
    Optional<Pme> pme = pmeRepository.findById(id);
    if (pme.isPresent()) {

      PMEDocuments doc = (PMEDocuments) documentService.upload(file, id,
              PMEDocuments.PROVENANCE, type);
      pme.get().getDocuments().add(doc);

      return Optional.of(pmeRepository.save(pme.get()));

    }
    return pme;
  }
}
