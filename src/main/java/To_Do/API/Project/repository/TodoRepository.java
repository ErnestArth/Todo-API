package To_Do.API.Project.repository;

import To_Do.API.Project.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompleted(boolean completed);

    List<Todo> findByTitleContainingIgnoreCase(String title);

    List<Todo> findByCompletedAndTitleContainingIgnoreCase(boolean completed, String title);

    List<Todo> findAllByOrderByCreatedAtDesc();
}
