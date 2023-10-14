package br.com.apariciojunior.todolist.erros;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // indica que a classe é um controlador de exceções
public class ExceptionHandlerController {

    @ExceptionHandler(HttpMessageNotReadableException.class) // indica que o método irá tratar a exceção
                                                             // HttpMessageNotReadableException
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("Erro: " + e.getMostSpecificCause().getMessage());
    }
}
