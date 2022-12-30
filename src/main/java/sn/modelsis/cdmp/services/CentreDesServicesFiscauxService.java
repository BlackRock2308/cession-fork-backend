package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.CentreDesServicesFiscaux;
import sn.modelsis.cdmp.entities.FormeJuridique;

import java.util.List;

@Service
public interface CentreDesServicesFiscauxService {

    /**
     * 
     * @return
     */
     List<CentreDesServicesFiscaux> findAll();

    CentreDesServicesFiscaux findByCode(String code);

    CentreDesServicesFiscaux save(CentreDesServicesFiscaux centreDesServicesFiscaux);

     void delete(Long id);

}
