package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import sn.modelsis.cdmp.entities.DemandeCession;
import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.TypePaiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiquePaiementCDMPDto;
import sn.modelsis.cdmp.entitiesDtos.StatistiquePaiementPMEDto;

public interface PaiementService {

        /**
         *
         * @param paiementDto,montant,typePaiement
         * @return
         */
    //    Paiement save(PaiementDto paiementDto, double montant, TypePaiement typePaiement);

        Paiement savePaiement(PaiementDto paiementDto);
        /**
         *
         * @param paiementDto,montant,typePaiement
         * @return
         */
    //    Paiement save(PaiementDto paiementDto, double montant, TypePaiement typePaiement);

        Paiement addPaiementToDemandeCession(PaiementDto paiementDto);

        /**
         *
         * @param idPaiement,montant,typePaiement
         * @return
         */
        void update(Long idPaiement, double montant, TypePaiement typePaiement);

        /**
         *
         * @return
         */
        List<Paiement> findAll();

        /**
         *
         * @param id
         * @return
         */
        Optional<Paiement> getPaiement(Long id);

        /**
         *
         * @param id
         */
        void delete(Long id);

    Paiement savePaiement(Paiement paiement);

    /**
     *
     * @param annee
     * @return
     */
    StatistiquePaiementCDMPDto getStatistiquePaiementCDMP(int annee);

    /**
     *
     * @param annee
     * @return
     */
    StatistiquePaiementPMEDto getStatistiqueAllPaiementPME(int annee);

    /**
     *
     * @param annee
     * @param  idPME
     * @return
     */
    StatistiquePaiementPMEDto getStatistiquePaiementByPME(int annee, Long idPME );
}
