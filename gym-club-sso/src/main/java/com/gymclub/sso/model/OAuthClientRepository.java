package com.gymclub.sso.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Xiaoming.
 * Created on 2019/05/18 19:27.
 * Description :
 */
@Repository
public interface OAuthClientRepository extends JpaRepository<OauthClient, String> {
    OauthClient findByClientId(String clientId);
}
