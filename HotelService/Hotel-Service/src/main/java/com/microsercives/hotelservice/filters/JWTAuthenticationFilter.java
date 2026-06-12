package com.microsercives.hotelservice.filters;

import com.microsercives.hotelservice.entities.AuthenticatedUser;
import com.microsercives.hotelservice.utility.JWTUtility;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if( header == null ||  header.isEmpty() ||  !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String extractedJWTToken = header.split(" ")[1];
        logger.info("JWT Token: {}", extractedJWTToken);
        Claims  claims = jwtUtility.extractAllClaims(extractedJWTToken);
        logger.info("JWT Claims: {}", claims);
        String emailId = claims.getSubject();
        String userId = claims.get("userId").toString();
        String roles = claims.get("userRoles").toString();
        logger.info("Value = {}", roles);
        logger.info("Type = {}", roles.getClass().getName());
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(userId, emailId, roles);
        logger.info("Authenticated User: {}", authenticatedUser);

        if( jwtUtility.validateToken(extractedJWTToken,authenticatedUser)  && SecurityContextHolder.getContext().getAuthentication() == null ) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roles);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticatedUser, null,List.of(simpleGrantedAuthority));
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);

    }
}
