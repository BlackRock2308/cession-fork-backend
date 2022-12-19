package sn.modelsis.cdmp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sn.modelsis.cdmp.entities.MinistereDepensier;

@Service
public interface MinistereDepensierService {

    /**
     * 
     * @return
     */
     List<MinistereDepensier> findAll();
     
     MinistereDepensier findByCode(String code);

}
