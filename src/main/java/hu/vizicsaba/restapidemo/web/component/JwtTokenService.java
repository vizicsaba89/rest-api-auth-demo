package hu.vizicsaba.restapidemo.web.component;

import hu.vizicsaba.restapidemo.service.model.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtTokenService {

    String getUserNameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    String getGeneratedToken(String userName);

    boolean isTokenValid(String token, UserDetails userDetails);

}
