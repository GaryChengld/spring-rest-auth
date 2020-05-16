package com.example.oauth2.okta.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Gary Cheng
 */
@Slf4j
@Component
public class OktaAccessTokenConverter extends DefaultAccessTokenConverter {
    private static final String GROUPS_CLAIM_NAME = "groups";
    private static final String USERNAME_CLAIM_NAME = "sub";
    private final ObjectMapper mapper;

    public OktaAccessTokenConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> tokenMap) {
        log.debug("Begin extractAuthentication: tokenMap = {}", tokenMap);
        JsonNode token = mapper.convertValue(tokenMap, JsonNode.class);
        List<GrantedAuthority> authorities = extractRoles(token); // extracting client roles

        OAuth2Authentication authentication = super.extractAuthentication(tokenMap);
        OAuth2Request oAuth2Request = authentication.getOAuth2Request();

        OAuth2Request request =
                new OAuth2Request(oAuth2Request.getRequestParameters(), oAuth2Request.getClientId(), authorities, true, oAuth2Request.getScope(),
                        null, null, null, null);
        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "N/A", authorities);
        log.debug("End extractAuthentication");
        return new OAuth2Authentication(request, usernamePasswordAuthentication);
    }

    private List<GrantedAuthority> extractRoles(JsonNode token) {
    }


}
