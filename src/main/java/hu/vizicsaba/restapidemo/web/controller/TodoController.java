package hu.vizicsaba.restapidemo.web.controller;

import hu.vizicsaba.restapidemo.service.component.TodoService;
import hu.vizicsaba.restapidemo.service.component.UserService;
import hu.vizicsaba.restapidemo.service.model.TodoRequest;
import hu.vizicsaba.restapidemo.service.model.TodoResponse;
import hu.vizicsaba.restapidemo.service.model.UserRequest;
import hu.vizicsaba.restapidemo.service.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        return ResponseEntity
            .ok()
            .body(todoService.getAllTodos());
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<TodoResponse> getCreatedTodo(@RequestBody TodoRequest todoRequest) {
        TodoResponse todoResponse = todoService.createTodo(todoRequest);

        return ResponseEntity
            .created(URI.create("todos/" + String.valueOf(todoResponse.getId())))
            .body(todoResponse);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Void> deleteTodo(@PathVariable long id) {
        todoService.deleteTodo(id);

        return ResponseEntity
            .ok()
            .build();
    }

}
