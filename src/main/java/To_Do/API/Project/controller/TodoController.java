package To_Do.API.Project.controller;

import To_Do.API.Project.dto.CreateTodoRequest;
import To_Do.API.Project.dto.TodoResponse;
import To_Do.API.Project.dto.UpdateTodoRequest;
import To_Do.API.Project.entity.TodoStatus;
import To_Do.API.Project.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Todo API v1")
public class TodoController {

    private final TodoService service;

    // Creating a todo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a todo")
    public TodoResponse create(@Valid @RequestBody CreateTodoRequest request) {
        log.info("POST /api/v1/todos body={}", request);
        return service.create(request);
    }


    // Getting a list of todos
    @GetMapping
    @Operation(summary = "List todos with optional status/title filters")
    public List<TodoResponse> list(
            @Parameter(description = "Filter by status",
                    schema = @Schema(allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED", "CANCELLED"}))
            @RequestParam(required = false) TodoStatus status,
            @Parameter(description = "Case-insensitive title substring")
            @RequestParam(required = false) String title) {

        log.info("GET /api/v1/todos status={} title='{}'", status, title);
        return service.list(status, title);
    }

    // Getting a todo
    @GetMapping("/{id}")
    @Operation(summary = "Get a todo by id")
    public TodoResponse getById(@PathVariable Long id) {
        log.info("GET /api/v1/todos/{}", id);
        return service.getById(id);
    }

    // Updating a todo
    @PutMapping("/{id}")
    @Operation(summary = "Update a todo")
    public TodoResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTodoRequest request) {
        log.info("PUT /api/v1/todos/{} body={}", id, request);
        return service.update(id, request);
    }

    // Deleting a todo
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a todo")
    public Map<String, Object> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/todos/{}", id);
        service.delete(id);
        return Map.of(
                "status", 200,
                "message", "Todo deleted successfully",
                "id", id
        );
    }
}
