package com.arah.cwa.backend.security;

import com.arah.cwa.backend.security.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AppAuthFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDataService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURI().contains("/token")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = httpServletRequest.getHeader(JwtTokenUtils.AUTHORIZATION_HEADER);

        String username = null;
        if (JwtTokenUtils.isValid(token)) {
            token = JwtTokenUtils.removePrefix(token);
            try {
                username = JwtTokenUtils.getUsername(token);
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred during getting login from token", e);
            } catch (ExpiredJwtException e) {
                logger.error("The token is expired and not valid anymore", e);
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Login or Password not valid");
            }
        } else {
            log.warn("Couldn't find bearer string, will ignore the header: {}", token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDataService.loadUserByUsername(username);

            if (JwtTokenUtils.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));

                log.info("Authenticated user with login = {}", username);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
