package br.com.apariciojunior.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadores: public, private, protected, default
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // o spring vai gerenciar todo ciclo de vida desse objeto (criar, destruir, etc)
    private UserRepository userRepository;

    public String hello() {
        return "Hello World";
    }

    @RequestMapping("/create") // http://localhost:8080/users/create
    public ResponseEntity createUser(@RequestBody UserModel userModel) {
        // System.out.println(userModel.getUsername());
        // ResponseEntity pode retornar tanto um status de sucesso como de erro,
        // mensagem de erro e status code

        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            // throw new RuntimeException("Usuário já existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

}
