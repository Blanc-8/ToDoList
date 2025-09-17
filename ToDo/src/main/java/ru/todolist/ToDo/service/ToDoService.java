package ru.todolist.ToDo.service;

import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;

import java.util.List;

public interface ToDoService {
    List<Task> findAllTasks();

    Task createTask (Task task);
    List<Task> createTasks(List<Task> tasks);

    Task editTask(Task task);
    void deleteTask(Integer id);
    List<Task> filterOfTasks(StatusOfTask status);
    List<Task> sortTasksByDateOfDedLine();
    List<Task> sortTasksByStatus();
    Task getById(Integer id);

}
