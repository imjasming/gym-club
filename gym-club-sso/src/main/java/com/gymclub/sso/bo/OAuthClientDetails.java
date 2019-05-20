package com.gymclub.sso.bo;

import com.gymclub.sso.model.OauthClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Xiaoming.
 * Created on 2019/05/13 00:43.
 */
public class OAuthClientDetails implements ClientDetails {
    private final OauthClient client;

    public OAuthClientDetails(OauthClient client) {
        this.client = client;
    }

    @Override
    public String getClientId() {
        return this.client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return this.client.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        String clientSecret = this.client.getClientSecret();
        return clientSecret != null && !"".equals(clientSecret);
    }

    @Override
    public String getClientSecret() {
        return this.client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        Set<String> scope = this.client.getScope();
        return scope != null && !scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return this.client.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.client.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.client.getWebServerRedirectUri();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.client.getAuthorities();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        Integer accessTokenValidity = client.getAccessTokenValidity();
        return accessTokenValidity != null ? accessTokenValidity : 7200;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        Integer refreshTokenValidity = client.getRefreshTokenValidity();
        return refreshTokenValidity != null ? refreshTokenValidity : 7200;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        Set<String> autoApproveScopes = this.client.getAutoapprove();
        if (autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = autoApproveScopes.iterator();
            String auto;
            do {
                if (!var2.hasNext()) {
                    return false;
                }
                auto = (String) var2.next();
            } while (!auto.equals("true") && !scope.matches(auto));
            return true;
        }
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null
                //Collections.unmodifiableMap(this.client.getAdditionalInformation())
                ;
    }
}
