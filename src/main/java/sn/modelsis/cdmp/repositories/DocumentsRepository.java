package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sn.modelsis.cdmp.entities.Documents;


/**
 * Spring Data repository for the Documents entity.
 */
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {



    @Modifying
    @Query("DELETE FROM Documents c where c.id =:#{#id}")
    void deleteDocument(Long id);


    @Modifying
    @Query("DELETE FROM Documents c where c.idprovenance =:#{#id}")
    void deleteDocumentsByIdprovenance(Long id);


}
