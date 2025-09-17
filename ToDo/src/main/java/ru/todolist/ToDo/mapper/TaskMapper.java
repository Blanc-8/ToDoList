package ru.todolist.ToDo.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task toEntity(TaskRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(TaskUpdateRequest dto, @MappingTarget Task target);

    TaskResponse toDto(Task entity);

    @AfterMapping
    default void setDefaultStatus(TaskRequest dto, @MappingTarget Task target) {
        if (target.getStatus() == null) {
            target.setStatus(StatusOfTask.TODO);
        }
    }
}
