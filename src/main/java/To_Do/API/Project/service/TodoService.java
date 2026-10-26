package To_Do.API.Project.service;

import To_Do.API.Project.dto.CreateTodoRequest;
import To_Do.API.Project.dto.TodoResponse;
import To_Do.API.Project.dto.UpdateTodoRequest;
import To_Do.API.Project.entity.TodoStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoService {
    TodoResponse create(CreateTodoRequest request);

    List<TodoResponse> list(Boolean completed, String title);

    @Transactional
    List<TodoResponse> list(TodoStatus status, String title);

    TodoResponse getById(Long id);

    TodoResponse update(Long id, UpdateTodoRequest request);

    void delete(Long id);
}
