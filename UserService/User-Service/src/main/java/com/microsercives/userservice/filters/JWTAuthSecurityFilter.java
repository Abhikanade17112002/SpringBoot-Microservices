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

        String authorisationHeader = request.getHeader("Authorization");
        LOG.info("AuthorisationHeader ==> "  + authorisationHeader);

        if( authorisationHeader == null || authorisationHeader.isBlank() || !authorisationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return ;
        }
        String jwtToken = authorisationHeader.split(" ")[1];
        LOG.info("JWT Token ==> " + jwtToken );

        String emailId =  jwtUtility.extractUsername(jwtToken);

        LOG.info("EmailId ==> " + emailId );

        User retrivedUser = (User) userRepository.findAllByEmailId(emailId);

        LOG.info("User ==> {} " + retrivedUser.toString());

        if(jwtUtility.validateToken(jwtToken,retrivedUser) && SecurityContextHolder.getContext().getAuthentication() == null ){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(retrivedUser,null,retrivedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response); ;

    }
}
