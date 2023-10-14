package br.com.apariciojunior.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, UUID>{

    TaskModel findByTitle(String title); // findByTitle é um método que o spring vai implementar para buscar uma tarefa pelo título

}
