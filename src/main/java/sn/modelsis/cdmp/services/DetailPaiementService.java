package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.Demande;
import sn.modelsis.cdmp.entities.DetailPaiement;

import java.util.List;
import java.util.Optional;

public interface DetailPaiementService {

    /**
     *
     * @param detailPaiement
     * @return
     */
    DetailPaiement save(DetailPaiement detailPaiement);

    /**
     *
     * @return
     */
    List<DetailPaiement> findAll();

    /**
     *
     * @param id
     * @return
     */
    Optional<DetailPaiement> getDetailPaiement(Long id);

    /**
     *
     * @param id
     */
    void delete(Long id);

}
