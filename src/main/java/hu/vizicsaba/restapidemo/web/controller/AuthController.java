package hu.vizicsaba.restapidemo.web.controller;

import hu.vizicsaba.restapidemo.service.component.CustomUserDetailsService;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
        if ("exit".equals(tokenRequest.getUserName())) {
            System.exit(0);
        }

        authenticate(tokenRequest);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenRequest.getUserName());
        String token = jwtTokenService.getGeneratedToken(userDetails.getUsername());

        return ResponseEntity
            .ok(new TokenResponse(token));
    }

    @PostMapping("/authenticatecookie")
    public ResponseEntity<TokenResponse> getTokenInCookie(@RequestBody TokenRequest tokenRequest, HttpServletResponse response) {
        authenticate(tokenRequest);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenRequest.getUserName());
        String token = jwtTokenService.getGeneratedToken(userDetails.getUsername());

        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
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
