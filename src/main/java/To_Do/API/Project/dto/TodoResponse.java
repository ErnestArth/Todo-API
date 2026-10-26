package To_Do.API.Project.dto;

import To_Do.API.Project.entity.Todo;
import To_Do.API.Project.entity.TodoStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .status(todo.getStatus())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
