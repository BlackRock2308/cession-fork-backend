package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.modelsis.cdmp.entities.MinistereDepensier;

@Repository
public interface MinistereDepensierRepository extends JpaRepository<MinistereDepensier, Long> {
    MinistereDepensier findByCode(@Param("code") String code);
}
