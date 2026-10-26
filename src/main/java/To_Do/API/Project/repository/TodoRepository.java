package To_Do.API.Project.repository;

import To_Do.API.Project.entity.Todo;
import To_Do.API.Project.entity.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
    List<Todo> findByStatus(TodoStatus status);

    List<Todo> findByStatusAndTitleContainingIgnoreCase(TodoStatus status, String title);

    List<Todo> findAllByOrderByCreatedAtDesc();

    List<Todo> findByTitleContainingIgnoreCase(String title);
}
