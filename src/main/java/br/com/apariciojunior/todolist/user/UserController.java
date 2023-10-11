package br.com.apariciojunior.todolist.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Modificadores: public, private, protected, default
 */

@RestController
@RequestMapping("/users")
public class UserController {

    public String hello() {
        return "Hello World";
    }

    @RequestMapping("/create") // http://localhost:8080/users/create
    public void createUser(@RequestBody UserModel userModel) {

        System.out.println(userModel.getUsername());

    }

}
