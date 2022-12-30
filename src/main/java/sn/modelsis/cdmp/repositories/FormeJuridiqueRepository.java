package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.modelsis.cdmp.entities.FormeJuridique;

@Repository
public interface FormeJuridiqueRepository extends JpaRepository<FormeJuridique, Long> {
    FormeJuridique findByCode(@Param("code") String code);
}
