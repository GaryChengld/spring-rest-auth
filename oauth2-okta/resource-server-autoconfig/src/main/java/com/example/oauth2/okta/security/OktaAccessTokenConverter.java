package com.example.oauth2.okta.security;

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
        String username = this.extractName(token);
        log.debug("username:{}", username);
        OAuth2Authentication authentication = super.extractAuthentication(tokenMap);
        OAuth2Request oAuth2Request = authentication.getOAuth2Request();

        OAuth2Request request =
                new OAuth2Request(oAuth2Request.getRequestParameters(), username, authorities, true, oAuth2Request.getScope(),
                        null, null, null, null);

        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(username, "N/A", authorities);
        log.debug("End extractAuthentication");
        return new OAuth2Authentication(request, usernamePasswordAuthentication);
    }

    private String extractName(JsonNode jwtToken) {
        String name = jwtToken.path(USERNAME_CLAIM_NAME).asText();
        log.debug("extractName {}", name);
        return name;
    }

    private List<GrantedAuthority> extractRoles(JsonNode jwtToken) {
        log.debug("Begin extractRoles: jwt = {}", jwtToken);
        Set<String> roles = new HashSet<>();
        jwtToken.path(GROUPS_CLAIM_NAME)
                .elements()
                .forEachRemaining(r -> roles.add(r.asText()));

        final List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        log.debug("End extractRoles: roles = {}", authorityList);
        return authorityList;
    }
}
