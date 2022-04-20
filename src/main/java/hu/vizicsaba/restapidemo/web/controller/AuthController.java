package hu.vizicsaba.restapidemo.web.controller;

import hu.vizicsaba.restapidemo.service.component.CustomUserDetailsService;
import hu.vizicsaba.restapidemo.service.component.UserService;
import hu.vizicsaba.restapidemo.service.model.UserResponse;
import hu.vizicsaba.restapidemo.web.component.JwtTokenService;
import hu.vizicsaba.restapidemo.web.model.TokenRequest;
import hu.vizicsaba.restapidemo.web.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
        authenticate(tokenRequest);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenRequest.getUserName());
        String token = jwtTokenService.getGeneratedToken(userDetails.getUsername());

        return ResponseEntity
            .ok(new TokenResponse(token));
    }

    private void authenticate(@RequestBody TokenRequest tokenRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequest.getUserName(), tokenRequest.getPassword())
            );
        } catch (BadCredentialsException exception) {
            throw new RuntimeException("Incorrect username or password", exception);
        }
    }

}
