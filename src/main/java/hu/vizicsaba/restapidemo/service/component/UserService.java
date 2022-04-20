package hu.vizicsaba.restapidemo.service.component;

import hu.vizicsaba.restapidemo.service.model.UserRequest;
import hu.vizicsaba.restapidemo.service.model.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(long id);

    UserResponse getCreatedUser(UserRequest userRequest);

    Map<String, String> deleteUserById(long id);

}
