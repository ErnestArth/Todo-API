package To_Do.API.Project.dto;

import To_Do.API.Project.entity.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(description = "Payload for creating a todo")
public class CreateTodoRequest {
    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 1000)
    private String description;

    @Schema(description = "Initial status (default: PENDING)",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED", "CANCELLED"})
    private TodoStatus status;
}
