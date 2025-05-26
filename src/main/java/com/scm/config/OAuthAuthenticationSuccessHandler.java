package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{


    @Autowired
    private UserRepo userRepo;


    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                logger.info("OAuthAuthenticationSuccessHandler");

                var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
                String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);

                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
user.getAttributes().forEach((key,value)->{
                        logger.info(key + " " + value);
                    });


                User user1 = new User();
                user1.setUserId(UUID.randomUUID().toString());
                user1.setRoleList(List.of("ROLE_USER"));
                user1.setEnabled(true);
                user1.setPassword("password");

                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    user1.setEmail(user.getAttribute("email").toString());
                    user1.setProfilePic(user.getAttribute("picture").toString());
                    user1.setName(user.getAttribute("name").toString());
                    user1.setProviderUserId(user.getName());
                    user1.setProvider(Providers.GOOGLE);


                }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    user1.setEmail(user.getAttribute("email")!=null ? user.getAttribute("email").toString(): user.getAttribute("login").toString()+"@gmail.com");
                    user1.setProfilePic(user.getAttribute("avatar_url").toString());
                    user1.setName(user.getAttribute("login").toString());
                    user1.setProviderUserId(user.getName());
                    user1.setProvider(Providers.GITHUB);

                }else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin")){
                    

                }else{
                    logger.info(")AuthAuthentivationSuccessHandler:  Unknown Provider"); 
                }
                // String email = user.getAttribute("email").toString();
                // String name = user.getAttribute("name").toString();
                // String picture = user.getAttribute("picture").toString();


                // User user1 = new User();
                // user1.setEmail(email);
                // user1.setName(name);
                // user1.setProfilePic(picture);
                // user1.setPassword("password");
                // user1.setUserId(UUID.randomUUID().toString());
                // user1.setProvider(Providers.GOOGLE);
                // user1.setEnabled(true);
                // user1.setEmailVerified(true);
                // user1.setProviderUserId(user.getName());
                // user1.setRoleList(List.of("ROLE_USER"));
                // user1.setAbout("This account is created using google ");


                User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);

                if(user2 == null){
                    userRepo.save(user1);
                    logger.info("User saved: "+ user1.getEmail());
                }
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
            }

}
