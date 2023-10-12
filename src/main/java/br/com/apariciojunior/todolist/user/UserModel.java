package br.com.apariciojunior.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id; // UUID = Universally Unique Identifier
    // @Column(name = "nome") - faça isso caso queira mudar o nome da coluna no
    // banco de dados
    @Column(nullable = false, unique = true) // não pode ser nulo e não pode ser repetido
    private String username; 
    private String name; 
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
