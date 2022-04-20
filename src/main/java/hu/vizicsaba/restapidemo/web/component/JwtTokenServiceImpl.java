package hu.vizicsaba.restapidemo.web.component;

import hu.vizicsaba.restapidemo.service.model.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String getUserNameFromToken(String token) {
        return getExtractedClaim(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        return getExtractedClaim(token, Claims::getExpiration);
    }

    @Override
    public String getGeneratedToken(String userName) {
        Map<String, Object> claims = new HashMap<>();

        return getToken(claims, userName);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return getUserNameFromToken(token).equalsIgnoreCase(userDetails.getUsername())
            && !getExpirationDateFromToken(token).before(new Date());
    }

    private <T> T getExtractedClaim(String token, Function<Claims, T> resolver) {
        Claims claims = getAllClaims(token);

        return resolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String getToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

}
