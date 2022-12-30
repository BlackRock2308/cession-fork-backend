package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.FormeJuridique;

import java.util.List;

@Service
public interface FormeJuridiqueService {

    /**
     * 
     * @return
     */
     List<FormeJuridique> findAll();

    FormeJuridique findByCode(String code);

    FormeJuridique save(FormeJuridique formeJuridique);

     void delete(Long id);

}
