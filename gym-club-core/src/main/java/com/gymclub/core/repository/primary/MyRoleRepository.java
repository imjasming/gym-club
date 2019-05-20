package com.gymclub.core.repository.primary;


import com.gymclub.core.domain.primary.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
@Repository
public interface MyRoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Role findByName(Role.RoleName rname);
}