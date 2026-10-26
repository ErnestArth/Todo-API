package To_Do.API.Project.exceptions;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException(Long id) {
        super("Todo not found with id " + id);
    }
}
