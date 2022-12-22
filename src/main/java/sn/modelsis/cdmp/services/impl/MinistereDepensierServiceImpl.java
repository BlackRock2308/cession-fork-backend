package sn.modelsis.cdmp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sn.modelsis.cdmp.entities.MinistereDepensier;
import sn.modelsis.cdmp.repositories.MinistereDepensierRepository;
import sn.modelsis.cdmp.services.MinistereDepensierService;

@Service
@RequiredArgsConstructor
public class MinistereDepensierServiceImpl implements MinistereDepensierService {

    @Autowired
    private MinistereDepensierRepository repository;

    @Override
    public List<MinistereDepensier> findAll() {
        return new ArrayList<>(repository
                .findAll());
    }
    
    @Override
    public MinistereDepensier findByCode(String code) {
      return repository.findByCode(code);
    }
}
