package ru.todolist.ToDo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.todolist.ToDo.model.StatusOfTask;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequest {
    private Integer id;
    private String title;
    private String description;
    private LocalDate dateOfDeadLine;
    private StatusOfTask status;
}
