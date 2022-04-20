package hu.vizicsaba.restapidemo.web.controller;

import hu.vizicsaba.restapidemo.service.component.UserService;
import hu.vizicsaba.restapidemo.service.model.UserRequest;
import hu.vizicsaba.restapidemo.service.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity
            .ok()
            .body(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(userService.getUserById(id));
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<UserResponse> getCreatedUser(@RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.getCreatedUser(userRequest);

        return ResponseEntity
            .created(URI.create("users/" + String.valueOf(createdUser.getId())))
            .body(createdUser);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(userService.deleteUserById(id));
    }

}
