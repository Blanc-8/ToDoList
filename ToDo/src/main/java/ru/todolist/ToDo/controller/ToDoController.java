package ru.todolist.ToDo.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.service.ToDoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo/tasks")
@AllArgsConstructor
public class ToDoController {

    private final ToDoService service;

    @GetMapping
    public List<TaskResponse> findAllTasks() {
        return service.findAllTasks();
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest task) {
        return service.createTask(task);
    }

    @PostMapping("/bulk")
    public List<TaskResponse> createTasks(@RequestBody List<TaskRequest> tasks) {
                return service.createTasks(tasks);
    }

    @PutMapping
    public TaskResponse editTask(@RequestBody TaskUpdateRequest task) {
        return service.editTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        service.deleteTask(id);
    }

    // Фильтр задач по статусу
    @GetMapping("/status/{status}")
    public List<TaskResponse> filterOfTasks(@PathVariable StatusOfTask status) {
        return service.filterOfTasks(status);
    }

    @GetMapping("/sort/deadline")
    public List<TaskResponse> sortTasksByDateOfDedLine() {
        return service.sortTasksByDateOfDedLine();
    }

    @GetMapping("/sort/status")
    public List<TaskResponse> sortTasksByStatus() {
        return service.sortTasksByStatus();
    }

}
