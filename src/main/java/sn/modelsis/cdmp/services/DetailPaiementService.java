package sn.modelsis.cdmp.services;

import java.util.List;
import java.util.Optional;

import sn.modelsis.cdmp.entities.DetailPaiement;

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
