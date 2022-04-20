package hu.vizicsaba.restapidemo.service.component;

import hu.vizicsaba.restapidemo.data.model.UserRoles;
import hu.vizicsaba.restapidemo.data.repository.UserRepository;
import hu.vizicsaba.restapidemo.service.exception.RestApiDemoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        hu.vizicsaba.restapidemo.data.model.User userByUserName = userRepository
            .findByUserName(s)
            .orElseThrow(RestApiDemoNotFoundException::new);

        if (userByUserName.isDeleted() || userByUserName.isPasswordExpired()) {
            throw new RuntimeException("User is deleted or password is expired");
        }

        String password = userByUserName.isTempPasswordExpired() ? userByUserName.getPassword() : userByUserName.getTempPassword();
        List<UserRoles> userRoles = userByUserName.getUserRoles();

        return new User(userByUserName.getUserName(), password, getAuthorities(userRoles));
    }

    private Collection<GrantedAuthority> getAuthorities( List<UserRoles> userRoles){
        return userRoles.stream()
            .map(userRole -> new SimpleGrantedAuthority(userRole.getRoles()))
            .collect(Collectors.toList());
    }
}
