package com.gymclub.sso.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Xiaoming.
 * Created on 2019/05/18 17:34.
 */
@Entity
@Data
@Table(name = "oauth_client_details")
public class OauthClient {
    @Id
    @Column(nullable = false)
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String autoapprove;
    private String additionalInformation;

    public String getClientId() {
        return clientId;
    }

    public Set<String> getResourceIds() {
        if (resourceIds != null) {
            // TODO: split效率较低，可优化（如：indexOf() + subString();
            // TODO: 整段代码都需优化
            String[] var = resourceIds.split(",");
            return Arrays.stream(var).collect(Collectors.toSet());
        }
        return null;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Set<String> getScope() {
        if (scope != null) {
            String[] scopes = this.scope.split(",");
            return Arrays.stream(scopes).collect(Collectors.toSet());
        }
        return null;
    }

    public Set<String> getAuthorizedGrantTypes() {
        if (authorizedGrantTypes != null) {
            String[] var = authorizedGrantTypes.split(",");
            return Arrays.stream(var).collect(Collectors.toSet());
        }
        return null;
    }

    public Set<String> getWebServerRedirectUri() {
        if (webServerRedirectUri != null) {
            String[] var = webServerRedirectUri.split(",");
            return Arrays.stream(var).collect(Collectors.toSet());
        }
        return null;
    }

    public Set<GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            String[] var = authorities.split(",");
            return Arrays.stream(var)
                    .filter(Objects::nonNull)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public Set<String> getAutoapprove() {
        if (autoapprove != null) {
            String[] var = autoapprove.split(",");
            return Arrays.stream(var).collect(Collectors.toSet());
        }
        return null;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }
}
