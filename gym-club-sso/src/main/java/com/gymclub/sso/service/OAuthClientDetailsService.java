package com.gymclub.sso.service;

import com.gymclub.sso.bo.OAuthClientDetails;
import com.gymclub.sso.model.OAuthClientRepository;
import com.gymclub.sso.model.OauthClient;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author Xiaoming.
 * Created on 2019/05/13 00:41.
 */
@Service
public class OAuthClientDetailsService implements ClientDetailsService {
    //@Autowired
    private OAuthClientRepository oAuthClientRepository;

    public OAuthClientDetailsService(OAuthClientRepository oAuthClientRepository) {
        this.oAuthClientRepository = oAuthClientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClient client = oAuthClientRepository.findByClientId(clientId);
        if (client == null) {
            throw new UnapprovedClientAuthenticationException("client not found:" + clientId);
        }
        return new OAuthClientDetails(client);
    }
}
