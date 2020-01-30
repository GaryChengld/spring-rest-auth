package com.example.oauth2.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author Gary Cheng
 */
@Controller
@Slf4j
public class WebController {

    @RequestMapping("/securedPage")
    public ModelAndView securedPage(@AuthenticationPrincipal OidcUser user, Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        log.info("User:{}", user.getName());
        mav.addObject("user", user);
        mav.addObject("token", user.getIdToken().getTokenValue());
        mav.setViewName("securedPage");
        return mav;
    }

    @RequestMapping("/")
    public ModelAndView index(@AuthenticationPrincipal OidcUser user, OAuth2AuthenticationToken authentication) {
        ModelAndView mav = new ModelAndView();
        if (null != user) {
            mav.addObject("user", user);
        }
        mav.setViewName("index");
        return mav;
    }

}