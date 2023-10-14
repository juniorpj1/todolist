package br.com.apariciojunior.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.apariciojunior.todolist.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath(); // /tasks

        // Verificar se a requisição é para o endpoint de login
        if (servletPath.startsWith("/tasks/")) {

            var authorization = request.getHeader("Authorization"); // Basic YXBhcmljaW86YXBhcmljb2Rlcg==
            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecode);
            var authArray = authString.split(":");
            String username = authArray[0]; // Usuário
            String password = authArray[1]; // Senha

            // Validar se o usuário existe no banco de dados
            var user = userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
                return;
            } else {
                // Validar se a senha está correta
                var passwordHash = user.getPassword();
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);
                if (!passwordVerify.verified) {
                    response.sendError(401);
                    return;
                } else {
                    request.setAttribute("idUser", user.getId()); // esse atributo será usado no controller
                    filterChain.doFilter(request, response);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
