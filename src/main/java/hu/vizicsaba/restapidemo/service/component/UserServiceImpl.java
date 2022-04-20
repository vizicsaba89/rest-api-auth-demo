package hu.vizicsaba.restapidemo.service.component;

import hu.vizicsaba.restapidemo.data.model.User;
import hu.vizicsaba.restapidemo.data.model.UserRoles;
import hu.vizicsaba.restapidemo.data.repository.UserRepository;
import hu.vizicsaba.restapidemo.service.exception.RestApiDemoNotFoundException;
import hu.vizicsaba.restapidemo.service.model.UserRequest;
import hu.vizicsaba.restapidemo.service.model.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository
            .findAll()
            .stream()
            .map(this::getConvertedUser)
            .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(long id) {
        User user = userRepository
            .findById(id)
            .orElseThrow(RestApiDemoNotFoundException::new);

        return getConvertedUser(user);
    }

    @Override
    public UserResponse getCreatedUser(UserRequest userRequest) {
        User createdUser = userRepository.save(getUserToCreate(userRequest));

        return getConvertedUser(createdUser);
    }

    @Override
    public Map<String, String> deleteUserById(long id) {
        userRepository.deleteById(id);

        return new HashMap<String, String>() {{
            put("message", "successfully deleted user with id " + id);
        }};
    }

    private UserResponse getConvertedUser(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    private User getUserToCreate(UserRequest userRequest) {
        User user = new User();
        user.setActive(true);
        user.setAddress(userRequest.getAddress());
        user.setCreated(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail(userRequest.getEmail());
        user.setEmailToken(userRequest.getEmailToken());
        user.setLastLogin(LocalDateTime.now());
        user.setName(userRequest.getName());
        user.setPhone(userRequest.getPhone());
        user.setTempPassword(getTempPassword());
        user.setTempPasswordExpired(false);
        user.setUserType(userRequest.getUserType());
        user.setUserName(userRequest.getUserName());
        user.setSettlementId(userRequest.getSettlementId());
        user.setSettlementsBySettlementId(userRequest.getSettlementsBySettlementId());
        user.setUserRoles(getUserRolesToCreate(userRequest, user));

        return user;
    }

    private String getTempPassword() {
        return new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
    }

    private List<UserRoles> getUserRolesToCreate(UserRequest userRequest, User user) {
        return userRequest.getRoles()
            .stream()
            .map(role -> new UserRoles(role, user))
            .collect(Collectors.toList());
    }
}
