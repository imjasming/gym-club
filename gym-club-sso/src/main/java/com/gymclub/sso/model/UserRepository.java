package com.gymclub.sso.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2018/11/22 11:30.
 * Description :This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
@Repository
@EntityScan("com.gymclub.core.domain.primary")
public interface UserRepository extends JpaRepository<UmUser, Integer> , JpaSpecificationExecutor<UmUser> {

    UmUser findByUsername(String username);

    List<UmUser> findAllByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UmUser findByEmail(String email);

    List<UmUser> findAllByEmail(String email);

    UmUser findUserByUsernameAndPassword(String username, String password);

    UmUser findUserByIdAndAndPassword(Long id, String password);

    @Modifying
    @Query("UPDATE UmUser u set u.email = :email where u.username = :username")
    void updateUmUserEmail(@Param("username") String username, @Param("email") String email);


    @Modifying
    @Query("UPDATE UmUser u set u.password = :password where u.username = :username")
    void updateUmUserPassword(@Param("username") String username, @Param("password") String password);

    @Modifying
    @Query("UPDATE UmUser u set u.intro = :intro where u.username = :username")
    void updateUmUserIntro(@Param("username") String username, @Param("intro") String intro);

}
