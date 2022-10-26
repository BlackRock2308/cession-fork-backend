package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import sn.modelsis.cdmp.entities.Paiement;
import sn.modelsis.cdmp.entities.TypePaiement;

public interface PaiementService {

        /**
         *
         * @param idDemande,montant,typePaiement
         * @return
         */
        Paiement save(Long idDemande, double montant, TypePaiement typePaiement);
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
