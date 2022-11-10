package sn.modelsis.cdmp.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParametrageDecoteServiceImpl implements ParametrageDecoteService {

    private final ParametrageDecoteRepository repository;

    private final DecodeMapper decodeMapper;


//    @Override
//    public ParametrageDecoteDTO createNewDecote(ParametrageDecoteDTO parametrageDecoteDTO) {
//        ParametrageDecote newDecote = null;
//        try{
//            log.info("ParametrageDecoteService:createNewDecote ......");
//            Optional<ParametrageDecoteDTO> optionalInf = repository.findParametrageByBorneInf(parametrageDecoteDTO.getBorneInf());
//            Optional<ParametrageDecoteDTO> optionalSup = repository.findParametrageByBorneSup(parametrageDecoteDTO.getBorneSup());
//
//            if(optionalInf.get().getBorneInf() == parametrageDecoteDTO.getBorneInf() ){
//                throw new CustomException("Un paramétrage similaire existe déja");
//
//                newDecote = decodeMapper.asEntity(parametrageDecoteDTO);
//
//               ParametrageDecote decoteDTO = repository.saveAndFlush(decodeMapper.asEntity(parametrageDecoteDTO));
//
//                return decodeMapper.asDTO(decoteDTO);
//            }
//        }catch (Exception ex){
//            log.error("Exception occured while adding new Decote. Error message : {}", ex.getMessage());
//            throw new CustomException("Error occured while adding a new Decote");
//        }
//        return decodeMapper.asDTO(newDecote);
//    }

    @Override
    public ParametrageDecote createNewDecote(ParametrageDecote parametrageDecote) {

        ParametrageDecote newDecote;

        try {
            log.info("ParametrageDecoteService:createNewDecote .....");

            Optional<ParametrageDecote> optional = repository.findParametrageByBorneInf(parametrageDecote.getBorneInf());
            optional = repository.findParametrageByBorneSup(parametrageDecote.getBorneSup());

            newDecote = repository.saveAndFlush(parametrageDecote);
            log.debug("ParametrageDecoteService:createNewDecote received from database : {}",newDecote);

        } catch (Exception ex){
            log.error("Exception occured while persisting new Parametrage Decote to database : {}",ex.getMessage());
            throw new CustomException("Exception occured while creating a new Decote ");
        }
        return  newDecote;
    }

    @Override
    public List<ParametrageDecoteDTO> getAllDecode() {
        return null;
    }

    @Override
    public Optional<ParametrageDecoteDTO> getDecode(Long id) {
        return Optional.empty();
    }

    @Override
    public ParametrageDecoteDTO updateDecoteParameter(Long id) {
        return null;
    }

    @Override
    public void deleteDecoteParameter(Long id) {

    }
}
