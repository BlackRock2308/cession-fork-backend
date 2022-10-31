package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.TypePaiement;
import sn.modelsis.cdmp.entitiesDtos.PaiementDto;

public interface PaiementService {

        /**
         *
         * @param paiementDto,montant,typePaiement
         * @return
         */
        Paiement save(PaiementDto paiementDto, double montant, TypePaiement typePaiement);
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
}
