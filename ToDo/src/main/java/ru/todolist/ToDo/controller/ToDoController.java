package ru.todolist.ToDo.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.mapper.TaskMapper;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;
import ru.todolist.ToDo.service.impl.ToDoServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@AllArgsConstructor
public class ToDoController {

    private final ToDoServiceImpl service;
    private final TaskMapper mapper;

    @GetMapping
    public List<TaskResponse> findAllTasks() {
        return service.findAllTasks().stream()
                .map(mapper::toDto).toList();
    }

    @PostMapping("/create_task")
    public TaskResponse createTask(@RequestBody TaskRequest task) {
        Task saved = service.createTask(mapper.toEntity(task));
        return mapper.toDto(saved);
    }

    @PostMapping("/create_tasks")
    public List<TaskResponse> createTasks(@RequestBody List<TaskRequest> tasks) {
        var entities = tasks.stream()
                .map(mapper::toEntity)
                .toList();
        var saved = service.createTasks(entities);
        return saved.stream()
                .map(mapper::toDto)
                .toList();
    }

    @PutMapping("/edit_task")
    public TaskResponse editTask(@RequestBody TaskUpdateRequest task) {
        Task existing = service.getById(task.getId());
        mapper.updateEntity(task, existing);
        Task saved = service.editTask(existing);
        return mapper.toDto(saved);
    }

    @DeleteMapping("/delete_task/{id}")
    public void deleteTask(@PathVariable Integer id) {
        service.deleteTask(id);
    }

    // Фильтр задач по статусу
    @GetMapping("/filter_by_status/{status}")
    public List<TaskResponse> filterOfTasks(@PathVariable StatusOfTask status) {
        return service.filterOfTasks(status).stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/sort_by_deadline")
    public List<TaskResponse> sortTasksByDateOfDedLine() {
        return service.sortTasksByDateOfDedLine().stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/sort_by_status")
    public List<TaskResponse> sortTasksByStatus() {
        return service.sortTasksByStatus().stream()
                .map(mapper::toDto)
                .toList();
    }

}
