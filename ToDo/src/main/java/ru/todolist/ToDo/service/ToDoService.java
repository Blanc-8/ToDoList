package ru.todolist.ToDo.service;

import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;

import java.util.List;

public interface ToDoService {
    List<TaskResponse> findAllTasks();

    TaskResponse createTask (TaskRequest task);
    List<TaskResponse> createTasks(List<TaskRequest> tasks);

    TaskResponse editTask(TaskUpdateRequest task);
    void deleteTask(Integer id);
    List<TaskResponse> filterOfTasks(StatusOfTask status);
    List<TaskResponse> sortTasksByDateOfDedLine();
    List<TaskResponse> sortTasksByStatus();

}
