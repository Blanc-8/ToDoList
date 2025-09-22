package ru.todolist.ToDo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository repository;

    @BeforeEach
    void setUp() {
        List<Task> tasks = List.of(
                task("todo1", StatusOfTask.TODO, LocalDate.of(2025, 9, 10)),
                task("done1", StatusOfTask.DONE, LocalDate.of(2025, 9, 11)),
                task("todo2", StatusOfTask.TODO, LocalDate.of(2025, 9, 20))
        );
        repository.saveAll(tasks);
    }

    @Test
    void findByStatus_returnsOnlyTodo() {
        List<Task> result = repository.findByStatus(StatusOfTask.TODO);

        assertEquals(2, result.size());

        for (Task t : result) {
            assertEquals(StatusOfTask.TODO, t.getStatus());
        }

        assertTrue(result.stream().allMatch(t -> t.getStatus() == StatusOfTask.TODO));

    }

    @Test
    void findAllByOrderByDateOfDeadLineAsc_returnsSortedAscending() {
        List<Task> result = repository.findAllByOrderByDateOfDeadLineAsc();

        assertEquals(3, result.size());

        assertThat(result)
                .extracting(Task::getDateOfDeadLine)
                .containsExactly(
                        LocalDate.of(2025,9,10),
                        LocalDate.of(2025,9,11),
                        LocalDate.of(2025,9,20)
                );

    }



    private static Task task(String title, StatusOfTask status, LocalDate deadline) {
        Task t = new Task();
        t.setTitle(title);
        t.setStatus(status);
        t.setDateOfDeadLine(deadline);
        return t;
    }

}
