package To_Do.API.Project.config;

import To_Do.API.Project.entity.TodoStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TodoStatusConverter implements Converter<String, TodoStatus> {
    @Override
    public TodoStatus convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return TodoStatus.valueOf(source.trim().toUpperCase().replace('-', '_'));
    }
}
