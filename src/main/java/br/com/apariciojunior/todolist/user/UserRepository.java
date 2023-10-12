package br.com.apariciojunior.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username); // findByUsername é um método que o spring vai implementar para buscar um usuário pelo username
}
