package To_Do.API.Project.service.serviceImpl;

import To_Do.API.Project.dto.CreateTodoRequest;
import To_Do.API.Project.dto.TodoResponse;
import To_Do.API.Project.dto.UpdateTodoRequest;
import To_Do.API.Project.entity.Todo;
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
        log.info("Creating todo with title='{}'", request.getTitle());
        Todo saved = repository.save(Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .build());
        log.info("Todo created successfully id={} title='{}'", saved.getId(), saved.getTitle());
        return TodoResponse.from(saved);
    }

    @Override
    @Transactional
    public List<TodoResponse> list(Boolean completed, String title) {
        log.info("Listing todos with filters completed={} title='{}'", completed, title);
        List<Todo> todos;

        if (completed != null && title != null) {
            log.debug("Applying filters: completed + title");
            todos = repository.findByCompletedAndTitleContainingIgnoreCase(completed, title);
        } else if (completed != null) {
            log.debug("Applying filter: completed={}", completed);
            todos = repository.findByCompleted(completed);
        } else if (title != null) {
            log.debug("Applying filter: title contains='{}'", title);
            todos = repository.findByTitleContainingIgnoreCase(title);
        } else {
            log.debug("No filters applied, returning all sorted by createdAt desc");
            todos = repository.findAllByOrderByCreatedAtDesc();
        }

        log.info("Fetched {} todo(s)", todos.size());
        return todos.stream().map(TodoResponse::from).toList();
    }


    @Override
    @Transactional(readOnly = true)
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
        log.info("Updating todo id={} newTitle='{}'", id, request.getTitle());
        Todo todo = repository.findById(id).orElseThrow(() -> {
            log.warn("Cannot update — todo not found id={}", id);
            return new TodoNotFoundException(id);
        });

        log.debug("Before update: {}", todo);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        Todo saved = repository.save(todo);
        log.debug("After update: {}", saved);
        log.info("Todo updated id={}", saved.getId());
        return TodoResponse.from(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting todo id={}", id);
        if (!repository.existsById(id)) {
            log.warn("Cannot delete — todo not found id={}", id);
            throw new TodoNotFoundException(id);
        }
        repository.deleteById(id);
        log.info("Todo deleted id={}", id);
    }
}
