package hu.vizicsaba.restapidemo.service.component;

import hu.vizicsaba.restapidemo.data.model.Todo;
import hu.vizicsaba.restapidemo.data.repository.TodoRepository;
import hu.vizicsaba.restapidemo.service.model.TodoRequest;
import hu.vizicsaba.restapidemo.service.model.TodoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll()
            .stream()
            .map(todo -> new TodoResponse(todo.getId(), todo.getTitle(), todo.getBody()))
            .collect(Collectors.toList());
    }

    @Override
    public TodoResponse createTodo(TodoRequest request) {
        Todo todo = todoRepository.save(new Todo(null, request.getTitle(), request.getBody()));

        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getBody());
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

}
