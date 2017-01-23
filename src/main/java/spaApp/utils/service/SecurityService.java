package spaApp.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private TokenStore tokenStore;

    public void removeToken() {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            String tokenValue = ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
