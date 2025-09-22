package ru.todolist.ToDo.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.mapper.TaskMapper;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.model.Task;
import ru.todolist.ToDo.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceImplTest {

    @Mock
    TaskRepository repository;
    @Mock
    TaskMapper mapper;

    @InjectMocks
    ToDoServiceImpl service;

    private Task task1;
    private Task task2;
    private TaskResponse resp1;
    private TaskResponse resp2;
    private TaskRequest req1;
    private TaskRequest req2;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(1);
        task1.setTitle("t1");
        task1.setStatus(StatusOfTask.TODO);
        task1.setDateOfDeadLine(LocalDate.of(2025, 9, 10));

        task2 = new Task();
        task2.setId(2);
        task2.setTitle("t2");
        task2.setStatus(StatusOfTask.DONE);
        task2.setDateOfDeadLine(LocalDate.of(2025, 9, 11));

        resp1 = new TaskResponse(1, "t1", null, LocalDate.of(2025, 9, 10), StatusOfTask.TODO);
        resp2 = new TaskResponse(2, "t2", null, LocalDate.of(2025, 9, 11), StatusOfTask.DONE);

        req1 = new TaskRequest("t1", null, LocalDate.of(2025, 9, 10), StatusOfTask.TODO);
        req2 = new TaskRequest("t2", null, LocalDate.of(2025, 9, 11), StatusOfTask.DONE);
    }


    @Test
    void findAllTasks_returnDtos() {
        when(repository.findAll()).thenReturn(List.of(task1, task2));
        when(mapper.toDto(task1)).thenReturn(resp1);
        when(mapper.toDto(task2)).thenReturn(resp2);

        List<TaskResponse> result = service.findAllTasks();

        assertThat(result).containsExactlyInAnyOrder(resp1, resp2);
        verify(repository).findAll();
        verify(mapper, times(2)).toDto(any(Task.class));
    }

    @Test
    void createTask_mapsSavesReturnsDto() {
        when(mapper.toEntity(req1)).thenReturn(task1);
        when(repository.save(task1)).thenReturn(task1);
        when(mapper.toDto(task1)).thenReturn(resp1);

        TaskResponse out = service.createTask(req1);

        assertEquals(resp1, out);
        verify(mapper).toEntity(req1);
        verify(repository).save(task1);
        verify(mapper).toDto(task1);

    }

    @Test
    void createTasks_mapsSavesReturnsDtos() {
        when(mapper.toEntity(req1)).thenReturn(task1);
        when(mapper.toEntity(req2)).thenReturn(task2);
        when(repository.saveAll(List.of(task1,task2))).thenReturn(List.of(task1,task2));
        when(mapper.toDto(task1)).thenReturn(resp1);
        when(mapper.toDto(task2)).thenReturn(resp2);

        List<TaskResponse> out = service.createTasks(List.of(req1, req2));

        assertThat(out).containsExactlyInAnyOrder(resp1, resp2);
        verify(repository).saveAll(List.of(task1,task2));
    }

    @Test
    void editTask() {
        TaskUpdateRequest upd = new TaskUpdateRequest(1, "t1-upd", "desc", LocalDate.of(2025, 9, 12), StatusOfTask.IN_PROGRESS);

        when(repository.findById(1)).thenReturn(Optional.of(task1));
        when(repository.save(task1)).thenReturn(task1);
        when(mapper.toDto(task1)).thenReturn(new TaskResponse(1, "t1-upd", "desc", LocalDate.of(2025, 9, 12), StatusOfTask.IN_PROGRESS));

        TaskResponse out = service.editTask(upd);

        assertThat(out.getTitle()).isEqualTo("t1-upd");
        verify(repository).findById(1);
        verify(mapper).updateEntity(upd, task1);
        verify(repository).save(task1);
        verify(mapper).toDto(task1);
    }

    @Test
    void deleteTask_delegatesToRepository() {
        service.deleteTask(42);
        verify(repository).deleteById(42);
    }

    @Test
    void filterOfTasks_returnsMappedDtos() {
        when(repository.findByStatus(StatusOfTask.TODO)).thenReturn(List.of(task1));
        when(mapper.toDto(task1)).thenReturn(resp1);

        List<TaskResponse> out = service.filterOfTasks(StatusOfTask.TODO);

        assertThat(out).containsExactly(resp1);
        verify(repository).findByStatus(StatusOfTask.TODO);
    }

    @Test
    void sortTasksByDateOfDedLine_returnsMappedDtos() {
        when(repository.findAllByOrderByDateOfDeadLineAsc()).thenReturn(List.of(task1,task2));
        when(mapper.toDto(task1)).thenReturn(resp1);
        when(mapper.toDto(task2)).thenReturn(resp2);

        List<TaskResponse> out = service.sortTasksByDateOfDedLine();

        assertThat(out).containsExactly(resp1, resp2);
        verify(repository).findAllByOrderByDateOfDeadLineAsc();
    }

    @Test
    void sortTasksByStatus_returnsMappedDtosInOrder() {
        when(repository.findAll()).thenReturn(List.of(task2, task1));
        when(mapper.toDto(task1)).thenReturn(resp1);
        when(mapper.toDto(task2)).thenReturn(resp2);

        List<TaskResponse> out = service.sortTasksByStatus();

        assertThat(out).extracting(TaskResponse::getStatus)
                .containsExactly(StatusOfTask.TODO, StatusOfTask.DONE);
        verify(repository).findAll();
    }
}