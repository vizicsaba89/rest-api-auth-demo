package hu.vizicsaba.restapidemo.service.component;

import hu.vizicsaba.restapidemo.service.model.TodoRequest;
import hu.vizicsaba.restapidemo.service.model.TodoResponse;

import java.util.List;

public interface TodoService {

    List<TodoResponse> getAllTodos();

    TodoResponse createTodo(TodoRequest request);

    void deleteTodo(Long id);
}
