package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sn.modelsis.cdmp.entities.ParametrageDecote;

@Service
public interface ParametrageDecoteService {

    ParametrageDecote createNewDecote(ParametrageDecote parametrageDecote);

    List<ParametrageDecote> getAllDecode();

    Optional<ParametrageDecote> getDecode(Long id);

    ParametrageDecote updateDecoteParameter(ParametrageDecote newParametrageDecote);

    void deleteDecoteParameter(Long id);

    Optional<ParametrageDecote> findByIdDecote(Long id);

    Optional<ParametrageDecote> findIntervalDecote(Double montant);

}
