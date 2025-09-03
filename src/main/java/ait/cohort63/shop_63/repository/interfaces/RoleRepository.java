package ait.cohort63.shop_63.repository.interfaces;

import ait.cohort63.shop_63.model.entity.Role;
import ait.cohort63.shop_63.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByTitle(String title);


}
