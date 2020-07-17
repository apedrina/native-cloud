package com.alissonpedrina.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    /**
     * Key for signing
     */
    private static final String SIGNING_KEY = "78sebr72umyz33i9876gc31urjgyfhgj";

    /**
     * Generate Jwt after successful user login
     * Using Hs256 algorithm
     *
     * @param exp jwt Expiration Time
     * @param claims Content stored in Payload
     * @return token Character string
     */
    public String createJWT(Date exp,
                            Map<String, Object> claims) {
        //Specify the signature algorithm to use when signing
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Time to generate JWT
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Create a JwtBuilder to set the body of jwt
        JwtBuilder builder = Jwts.builder()
                //Content stored in Payload
                .setClaims(claims)
                //Iat: Issuance time of JWT
                .setIssuedAt(now)
                //Set expiration time
                .setExpiration(exp)
                //Set the signature algorithm used by the signature and the secret key used by the signature
                .signWith(signatureAlgorithm, SIGNING_KEY);

        return builder.compact();
    }

    /**
     * Parse token to get the contents of Payload, including verifying signatures and determining whether they are out of date
     *
     * @param token
     * @return
     */
    public Claims parseJWT(String token) {
        //Get DefaultJwtParser
        Claims claims = Jwts.parser()
                //Set Signature Key
                .setSigningKey(SIGNING_KEY)
                //Set token to parse
                .parseClaimsJws(token).getBody();
        return claims;
    }

}