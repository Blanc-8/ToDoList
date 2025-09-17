package ru.todolist.ToDo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByStatus(StatusOfTask status);
    List<Task> findAllByOrderByDateOfDeadLineAsc();

}
