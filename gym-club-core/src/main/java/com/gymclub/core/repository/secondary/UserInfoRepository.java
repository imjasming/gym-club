package com.gymclub.core.repository.secondary;


import com.gymclub.core.domain.secondary.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Xiaoming.
 * Created on 2019/04/27 21:25.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByUsername(String username);
}
