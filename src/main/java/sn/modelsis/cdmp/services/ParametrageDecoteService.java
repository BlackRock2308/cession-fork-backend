package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public interface ParametrageDecoteService {

    ParametrageDecote createNewDecote(ParametrageDecote parametrageDecote);

    List<ParametrageDecote> getAllDecode();

    Optional<ParametrageDecote> getDecode(Long id);

    ParametrageDecote updateDecoteParameter(Long id, ParametrageDecote newParametrageDecote);

    void deleteDecoteParameter(Long id);

    Optional<ParametrageDecote> findByIdDecote (Long id);

    Optional<ParametrageDecote> findIntervalDecote(BigDecimal montant);

}
