package com.medido.followup.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${cookie.jwt.token.name}")
    private String tokenName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String email = null;
        Integer companyId = null;
        String headerJwtToken = request.getHeader("authorization");
//        String jwtToken = CookieUtil.getValue(request, tokenName);

        if (headerJwtToken != null) {
            try {
                email = jwtTokenUtil.getEmailFromToken(headerJwtToken);
                companyId = jwtTokenUtil.getCompanyIdFromToken(headerJwtToken);

            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token is not found in the cookie");
        }

        if (email != null) {
            UserDetails user = new AuthUser(Long.parseLong(jwtTokenUtil.getUserIdFromToken(headerJwtToken)), email, companyId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        chain.doFilter(request, response);
    }
}
