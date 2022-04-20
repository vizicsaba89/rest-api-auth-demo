package hu.vizicsaba.restapidemo.data.repository;

import hu.vizicsaba.restapidemo.data.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
