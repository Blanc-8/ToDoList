package ru.todolist.ToDo.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.mapper.TaskMapper;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;
import ru.todolist.ToDo.repository.TaskRepository;
import ru.todolist.ToDo.service.ToDoService;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    @Override
    public List<TaskResponse> findAllTasks() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest dto) {
        Task toSave = mapper.toEntity(dto);
        Task saved = repository.save(toSave);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<TaskResponse> createTasks(List<TaskRequest> dtos) {
        List<Task> entities = dtos.stream()
                .map(mapper::toEntity)
                .toList();
        List<Task> saved = repository.saveAll(entities);
        return saved.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public TaskResponse editTask(TaskUpdateRequest dto) {
        Task existing = repository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + dto.getId()));
        mapper.updateEntity(dto, existing);
        Task saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TaskResponse> filterOfTasks(StatusOfTask status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TaskResponse> sortTasksByDateOfDedLine() {
        return repository.findAllByOrderByDateOfDeadLineAsc().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TaskResponse> sortTasksByStatus() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingInt(t -> t.getStatus().ordinal()))
                .map(mapper::toDto)
                .toList();
    }

}
