package com.microsercives.userservice.filters;

import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.repositories.UserRepository;
import com.microsercives.userservice.utility.JWTUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JWTAuthSecurityFilter extends OncePerRequestFilter {
    private static final Logger LOG =
            (Logger) LoggerFactory.getLogger(JWTAuthSecurityFilter.class);
    @Autowired
    private JWTUtility jwtUtility ;
    @Autowired
    private UserRepository userRepository ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // ✅ Temporary: print ALL incoming headers to see what's arriving
        logger.info("=== ALL INCOMING HEADERS ===");
        java.util.Collections.list(request.getHeaderNames())
                .forEach(headerName ->
                        logger.info("Header: "+ headerName + " " + " = "+  request.getHeader(headerName))
                );
        logger.info("============================");

        String username =
                request.getHeader("X-USERNAME");
        String rolesHeader =
                request.getHeader("X-ROLES");

        if( username == null || rolesHeader == null || username.isBlank() ||rolesHeader.isBlank() ) {
            filterChain.doFilter(request,response);
            return ;
        }
        logger.info("UserName {}" + username);
        logger.info("Roles {}" + rolesHeader);
        User retrivedUser = (User) userRepository.findAllByEmailId(username);

        LOG.info("User ==> {} " + retrivedUser.toString());

        if(SecurityContextHolder.getContext().getAuthentication() == null ){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(retrivedUser,null,retrivedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response); ;

    }
}
