package To_Do.API.Project.service.serviceImpl;

import To_Do.API.Project.dto.CreateTodoRequest;
import To_Do.API.Project.dto.TodoResponse;
import To_Do.API.Project.dto.UpdateTodoRequest;
import To_Do.API.Project.entity.Todo;
import To_Do.API.Project.entity.TodoStatus;
import To_Do.API.Project.exceptions.TodoNotFoundException;
import To_Do.API.Project.repository.TodoRepository;
import To_Do.API.Project.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;

    @Override
    @Transactional
    public TodoResponse create(CreateTodoRequest request) {
        TodoStatus status = request.getStatus() != null ? request.getStatus() : TodoStatus.PENDING;
        log.info("Creating todo title='{}' status={}", request.getTitle(), status);

        Todo saved = repository.save(Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(status)
                .build());

        log.info("Todo created id={} status={}", saved.getId(), saved.getStatus());
        return TodoResponse.from(saved);
    }

    @Override
    public List<TodoResponse> list(Boolean completed, String title) {
        return List.of();
    }

    @Transactional
    @Override
    public List<TodoResponse> list(TodoStatus status, String title) {
        log.info("Listing todos status={} title='{}'", status, title);

        List<Todo> todos;
        if (status != null && title != null) {
            todos = repository.findByStatusAndTitleContainingIgnoreCase(status, title);
        } else if (status != null) {
            todos = repository.findByStatus(status);
        } else if (title != null) {
            todos = repository.findByTitleContainingIgnoreCase(title);
        } else {
            todos = repository.findAllByOrderByCreatedAtDesc();
        }

        log.info("Fetched {} todo(s)", todos.size());
        return todos.stream().map(TodoResponse::from).toList();
    }


    @Override
    @Transactional
    public TodoResponse getById(Long id) {
        log.info("Fetching todo id={}", id);
        Todo todo = repository.findById(id).orElseThrow(() -> {
            log.warn("Todo not found id={}", id);
            return new TodoNotFoundException(id);
        });
        log.info("Todo found id={} title='{}'", todo.getId(), todo.getTitle());
        return TodoResponse.from(todo);
    }

    @Override
    @Transactional
    public TodoResponse update(Long id, UpdateTodoRequest request) {
        Todo todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));

        log.debug("Before update: {}", todo);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            todo.setStatus(request.getStatus());
        }
        Todo saved = repository.save(todo);
        log.info("Todo updated id={} status={}", saved.getId(), saved.getStatus());
        return TodoResponse.from(saved);
    }

    @Override
    public void delete(Long id) {

    }
}
