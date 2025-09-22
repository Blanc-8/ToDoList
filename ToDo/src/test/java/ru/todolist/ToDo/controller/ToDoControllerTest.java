package ru.todolist.ToDo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.todolist.ToDo.dto.TaskRequest;
import ru.todolist.ToDo.dto.TaskResponse;
import ru.todolist.ToDo.dto.TaskUpdateRequest;
import ru.todolist.ToDo.model.StatusOfTask;
import ru.todolist.ToDo.service.ToDoService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ToDoControllerTest {

    @Mock
    ToDoService service;

    @InjectMocks
    ToDoController controller;

    private TaskRequest req1;
    private TaskRequest req2;
    private TaskUpdateRequest upd;
    private TaskResponse resp1;
    private TaskResponse resp2;

    @BeforeEach
    void setUp() {
        LocalDate d1 = LocalDate.of(2025, 9, 10);
        LocalDate d2 = LocalDate.of(2025, 9, 11);

        req1 = new TaskRequest("t1", null, d1, StatusOfTask.TODO);
        req2 = new TaskRequest("t2", null, d2, StatusOfTask.DONE);

        upd = new TaskUpdateRequest(1, "t1-upd", "desc", LocalDate.of(2025, 9, 12), StatusOfTask.IN_PROGRESS);

        resp1 = new TaskResponse(1, "t1", null, d1, StatusOfTask.TODO);
        resp2 = new TaskResponse(2, "t2", null, d2, StatusOfTask.DONE);
    }


    @Test
    void findAllTasks_delegatesAndReturns() {
        List<TaskResponse> serviceOut = List.of(resp1, resp2);
        when(service.findAllTasks()).thenReturn(serviceOut);

        List<TaskResponse> out = controller.findAllTasks();

        assertEquals(serviceOut, out);
        verify(service).findAllTasks();
    }

    @Test
    void createTask_delegatesAndReturns() {
        when(service.createTask(req1)).thenReturn(resp1);

        TaskResponse out =  controller.createTask(req1);

        assertEquals(resp1, out);
        verify(service).createTask(req1);
    }

    @Test
    void createTasks_delegatesAndReturns() {
        List<TaskRequest> in = List.of(req1, req2);
        List<TaskResponse> serviceOut = List.of(resp1, resp2);
        when(service.createTasks(in)).thenReturn(serviceOut);

        List<TaskResponse> out = controller.createTasks(in);

        assertEquals(serviceOut, out);
        verify(service).createTasks(in);
    }

    @Test
    void editTask_delegatesAndReturns() {
        TaskResponse update = new TaskResponse(1, "t1-upd", "desc", LocalDate.of(2025, 9, 12), StatusOfTask.IN_PROGRESS);
        when(service.editTask(upd)).thenReturn(update);

        TaskResponse out = controller.editTask(upd);

        assertEquals(update, out);
        verify(service).editTask(upd);
    }

    @Test
    void deleteTask_delegates() {
        controller.deleteTask(42);
        verify(service).deleteTask(42);
    }

    @Test
    void filterOfTasks_ByStatus_delegatesAndReturns() {
        List<TaskResponse> serviceOut = List.of(resp1);
        when(service.filterOfTasks(StatusOfTask.TODO)).thenReturn(serviceOut);

        List<TaskResponse> out = controller.filterOfTasks(StatusOfTask.TODO);

        assertEquals(serviceOut, out);
        verify(service).filterOfTasks(StatusOfTask.TODO);

    }

    @Test
    void sortTasksByDateOfDedLine_delegatesAndReturns() {
        List<TaskResponse> serviceOut = List.of(resp1, resp2);
        when(service.sortTasksByDateOfDedLine()).thenReturn(serviceOut);

        List<TaskResponse> out = controller.sortTasksByDateOfDedLine();

        assertEquals(serviceOut, out);
        verify(service).sortTasksByDateOfDedLine();
    }

    @Test
    void sortTasksByStatus_delegatesAndReturns() {
        List<TaskResponse> serviceOut = List.of(resp1, resp2);
        when(service.sortTasksByStatus()).thenReturn(serviceOut);

        List<TaskResponse> out = controller.sortTasksByStatus();

        assertEquals(serviceOut, out);
        verify(service).sortTasksByStatus();
    }
}