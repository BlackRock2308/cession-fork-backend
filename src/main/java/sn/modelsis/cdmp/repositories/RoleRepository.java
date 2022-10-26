package sn.modelsis.cdmp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.modelsis.cdmp.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByLibelle(String libelle) ;
}
