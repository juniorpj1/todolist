package br.com.apariciojunior.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 50)
    private String title;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime endAt;
    private String priority;
    private LocalDateTime startAt;
    private UUID idUser;

    // throws Exception: caso ocorra algum erro, o método irá lançar uma exceção para quem o chamar
    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("O título não pode ter mais que 50 caracteres");
        }
        this.title = title;
    }

}
