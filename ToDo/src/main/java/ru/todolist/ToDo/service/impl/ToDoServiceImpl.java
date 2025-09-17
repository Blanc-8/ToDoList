package ru.todolist.ToDo.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;
import ru.todolist.ToDo.repository.TaskRepository;
import ru.todolist.ToDo.service.ToDoService;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final TaskRepository repository;

    @Override
    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Task createTask(Task task) {
        return repository.save(task);
    }

    @Override
    @Transactional
    public List<Task> createTasks(List<Task> tasks) {
        return repository.saveAll(tasks);
    }

    @Override
    @Transactional
    public Task editTask(Task task) {
        return repository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Task> filterOfTasks(StatusOfTask status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Task> sortTasksByDateOfDedLine() {
        return repository.findAllByOrderByDateOfDeadLineAsc();
    }

    @Override
    public List<Task> sortTasksByStatus() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingInt(t -> t.getStatus().ordinal()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Task getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    }
}
