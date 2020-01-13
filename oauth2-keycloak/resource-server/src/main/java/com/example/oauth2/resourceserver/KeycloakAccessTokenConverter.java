package com.example.oauth2.resourceserver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Gary Cheng
 */
@Slf4j
@Component
public class KeycloakAccessTokenConverter extends DefaultAccessTokenConverter {
    private final ObjectMapper mapper;
    private static final String REALM_ELEMENT = "realm_access";
    private static final String ROLE_ELEMENT = "roles";

    public KeycloakAccessTokenConverter(ObjectMapper mapper) {
        log.info("Initialized {}", KeycloakAccessTokenConverter.class.getSimpleName());
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

    private List<GrantedAuthority> extractRoles(JsonNode jwt) {
        log.debug("Begin extractRoles: jwt = {}", jwt);
        Set<String> roles = new HashSet<>();
        jwt.path(REALM_ELEMENT)
                .path(ROLE_ELEMENT)
                .elements()
                .forEachRemaining(r -> roles.add(r.asText()));

        final List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        log.debug("End extractRoles: roles = {}", authorityList);
        return authorityList;
    }
}
