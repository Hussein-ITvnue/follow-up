package com.medido.followup.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Gets the token value from the bearer token
     *
     * @param bearerToken The bearer token
     * @return token
     */
    public String getTokenFromBearerToken(String bearerToken) {
        if (bearerToken != null && bearerToken.toUpperCase().startsWith("BEARER ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

    /**
     * Gets the user id from the token
     *
     * @param token The JWT token
     * @return Email
     */
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    /**
     * Gets the email from the token
     *
     * @param token The JWT token
     * @return Email
     */
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Gets the expiration date from the token
     *
     * @param token The JWT token
     * @return The expiration date of the token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieves a claim from the JWT token
     *
     * @param token          The JWT token
     * @param claimsResolver A claim resolver function
     * @return Object
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        String processedToken = getTokenFromBearerToken(token);
        final Claims claims = getAllClaimsFromToken(processedToken);
        return claimsResolver.apply(claims);
    }


    /**
     * Retrieves all the claims from the JWT token
     *
     * @param token The JWT token
     * @return All the token's claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the token is expired
     *
     * @param token The JWT token
     * @return True in case of the token is expired, else false
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        final String userId = getUserIdFromToken(token);
        return !(userId == null || isTokenExpired(token));
    }

    public Integer getCompanyIdFromToken(String token) {
        try {
            String processedToken = getTokenFromBearerToken(token);
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(processedToken)
                    .getBody();
            Integer companyId = (Integer) body.get("company_id");
            return companyId;
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

}
