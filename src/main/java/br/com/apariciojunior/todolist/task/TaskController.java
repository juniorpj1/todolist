package br.com.apariciojunior.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apariciojunior.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create") // http://localhost:8080/tasks/create
    public ResponseEntity createTask(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        var idUser = request.getAttribute("idUser"); // recuperar o id do usuário vindo do filtro
        taskModel.setIdUser((UUID) idUser); // atribuir o id do usuário na criação da task

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início/término não pode ser menor que a data atual");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início não pode ser maior que a data de término");
        }

        var task = this.taskRepository.save(taskModel); // salvar a task no banco de dados
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/list") // http://localhost:8080/tasks/list
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser"); // recuperar o id do usuário vindo do filtro
        var tasks = this.taskRepository.findByIdUser((UUID) idUser); // buscar as tasks do usuário

        return tasks;
    }

    @PutMapping("/update/{id}") // http://localhost:8080/tasks/update/idTask
    public ResponseEntity updateTask(@RequestBody TaskModel taskModel, @PathVariable UUID id,
            HttpServletRequest request) {

        var task = this.taskRepository.findById(id).orElse(null); // buscar a task pelo id

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task não encontrada");
        }

        var idUser = request.getAttribute("idUser"); // recuperar o id do usuário vindo do filtro

        if (!task.getIdUser().equals(idUser)) { // verificar se o id do usuário é o mesmo da task
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Você não tem permissão para alterar essa task");
        }

        Utils.copyNonNullProperties(taskModel, task); // copiar as propriedades não nulas da taskModel para a task
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated); // atualizar a task no banco de dados

    }

}