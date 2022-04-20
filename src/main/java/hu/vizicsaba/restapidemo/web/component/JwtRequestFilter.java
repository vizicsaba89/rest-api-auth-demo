package hu.vizicsaba.restapidemo.web.component;

import hu.vizicsaba.restapidemo.service.component.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Service
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain
    ) throws ServletException, IOException {
        String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            username = getUserNameFromToken(username, jwtToken);
        } else {
            log.error("JWT Token does not exists in header, checking cookies");

            final Cookie[] cookies = httpServletRequest.getCookies();

            if (cookies != null) {
                jwtToken = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("token"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

                username = getUserNameFromToken(username, jwtToken);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (jwtTokenService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getUserNameFromToken(String username, String jwtToken) {
        try {
            username = jwtTokenService.getUserNameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            log.error("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("JWT Token has expired");
        }

        return username;
    }

}
