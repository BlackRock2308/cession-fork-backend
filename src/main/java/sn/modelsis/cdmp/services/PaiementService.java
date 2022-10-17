package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import sn.modelsis.cdmp.entities.Paiement;

public interface PaiementService {

        /**
         *
         * @param paiement
         * @return
         */
        Paiement save(Paiement paiement);

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
