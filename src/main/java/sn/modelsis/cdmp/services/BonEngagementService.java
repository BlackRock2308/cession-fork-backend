package sn.modelsis.cdmp.services;

import sn.modelsis.cdmp.entities.BonEngagement;

import java.util.List;
import java.util.Optional;

public interface BonEngagementService {

    /**
     *
     * @param bonEngagement
     * @return
     */
    BonEngagement save(BonEngagement bonEngagement);

    /**
     *
     * @param pageable
     * @return
     */
    List<BonEngagement> findAll();

    /**
     *
     * @param id
     * @return
     */
    Optional<BonEngagement> getBonEngagement(Long id);

    /**
     *
     * @param id
     */
    void delete(Long id);

}
