package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.modelsis.cdmp.entities.Documents;


/**
 * Spring Data repository for the Documents entity.
 */
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {


}
