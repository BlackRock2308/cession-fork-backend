package sn.modelsis.cdmp.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sn.modelsis.cdmp.entities.ParametrageDecote;
import sn.modelsis.cdmp.entities.Pme;
import sn.modelsis.cdmp.entitiesDtos.ParametrageDecoteDTO;
import sn.modelsis.cdmp.exceptions.CustomException;
import sn.modelsis.cdmp.exceptions.ItemExistsException;
import sn.modelsis.cdmp.mappers.DecodeMapper;
import sn.modelsis.cdmp.repositories.ParametrageDecoteRepository;
import sn.modelsis.cdmp.repositories.ParametrageRepository;
import sn.modelsis.cdmp.services.ParametrageDecoteService;
import sn.modelsis.cdmp.util.ExceptionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParametrageDecoteServiceImpl implements ParametrageDecoteService {

    private final ParametrageDecoteRepository repository;

    private final DecodeMapper decodeMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ParametrageDecote createNewDecote(ParametrageDecote parametrageDecote) {

        ParametrageDecote newDecote;

        try {
            log.info("ParametrageDecoteService:createNewDecote .....");

            Optional<ParametrageDecote> optional = repository.findParametrageByBorneInf(parametrageDecote.getBorneInf());
            optional = repository.findParametrageByBorneSup(parametrageDecote.getBorneSup());

            if(parametrageDecote.getBorneInf() < parametrageDecote.getBorneSup()){
                log.info("Error : Vérifiez vos bornes, borneSup > borneInf : Error message : {}" );
                throw new CustomException("Exception occured : Borne inf and Borne Sup ");

            }
            newDecote = repository.saveAndFlush(parametrageDecote);
            log.debug("ParametrageDecoteService:createNewDecote received from database : {}",newDecote);

        } catch (Exception ex){
            log.error("Exception occured while persisting new Parametrage Decote to database : {}",ex.getMessage());
            throw new CustomException("Exception occured while creating a new Decote ");
        }
        return  newDecote;
    }

    @Override
    public List<ParametrageDecote> getAllDecode() {
        return new ArrayList<>(repository
                .findAll());
    }

    @Override
    public Optional<ParametrageDecote> getDecode(Long id) {

        Optional<ParametrageDecote> optional = Optional.ofNullable(repository.findById(id).orElse(null));
        return Optional.of(optional.get());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ParametrageDecote updateDecoteParameter(Long id,
                                                      ParametrageDecote newParametrageDecote) {
        Optional <ParametrageDecote> existingDecote;
        try{
            log.info("ParametrageDecoteService:updateDecoteParameter updating ........");
            existingDecote = repository.findById(id);

            if(newParametrageDecote.getBorneInf() < newParametrageDecote.getBorneSup()){
                log.error("Error : Vérifiez vos bornes, borneSup > borneInf : Error message : {}" );
                throw new CustomException("Exception occured : Borne inf and Borne Sup ");
            }
            existingDecote.get().setBorneInf(newParametrageDecote.getBorneInf());
            existingDecote.get().setBorneSup(newParametrageDecote.getBorneSup());
            existingDecote.get().setDecoteValue(newParametrageDecote.getDecoteValue());

            repository.saveAndFlush(existingDecote.get());
            log.info("ParametrageDecoteService:updateDecoteParameter update Pme with id : {}",existingDecote.get().getIdDecote());
        }catch (Exception ex){
            log.error("Exception occured while updating Decote with id : {}",id );
            throw new CustomException("Error occured while updating this Decote param ");
        }
        return existingDecote.get();
    }

    @Override
    public void deleteDecoteParameter(Long id) {

    }

    @Override
    public Optional<ParametrageDecote> findByIdDecote(Long id) {

        return repository.findById(id);
    }

    @Override
    public Optional<ParametrageDecote> findIntervalDecote(BigDecimal montant){
        ParametrageDecote parametrageDecote = null;
        try{
            log.info("ParametrageDecoteService:findIntervalDecote .....");

            List<ParametrageDecote> decoteList = repository.findAll();

            for(ParametrageDecote decote : decoteList ){
                if(montant.toBigIntegerExact().longValueExact() > decote.getBorneInf() && montant.toBigIntegerExact().longValueExact() <= decote.getBorneSup()){
                    parametrageDecote = decote;
                    log.info("corresponding decote param : {}", parametrageDecote);
                }
//                else {
//                    log.info("ParametrageDecoteService:findIntervalDecote : aucun paramétrage ne correspond");
//                    parametrageDecote = new ParametrageDecote(null,null, montant);
//                    repository.saveAndFlush(parametrageDecote);
//                    log.info("New Created Decote Param : {}", parametrageDecote);
//
//                }
            }
        }catch (Exception ex){
            log.info("Exception occured while finding the right interval. Error message : {}",  ex.getMessage());
            throw new CustomException("Exception occured while finding the correct decote Interval");
        }

        return Optional.ofNullable(parametrageDecote);
    }
}
