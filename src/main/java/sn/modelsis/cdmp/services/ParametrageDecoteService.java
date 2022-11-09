package sn.modelsis.cdmp.services;

import org.springframework.stereotype.Service;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;

import java.util.List;
import java.util.Optional;

@Service
public interface ParametrageDecoteService {

    ParametrageDecoteDTO createNewDecote(ParametrageDecoteDTO parametrageDecoteDTO);

    List<ParametrageDecoteDTO> getAllDecode();

    Optional<ParametrageDecoteDTO> getDecode(Long id);

    ParametrageDecoteDTO  updateDecoteParameter(Long id);

    void deleteDecoteParameter(Long id);
}
