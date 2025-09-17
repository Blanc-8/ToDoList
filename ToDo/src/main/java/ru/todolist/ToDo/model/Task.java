package ru.todolist.ToDo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "deadline")
    private LocalDate dateOfDeadLine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOfTask status = StatusOfTask.TODO;
}
