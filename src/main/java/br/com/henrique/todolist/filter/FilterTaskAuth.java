package br.com.henrique.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.henrique.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        var serveletPath = request.getServletPath();
                //startsWith contempla todos paths
        if (serveletPath.startsWith("/tasks/")) {

            // pegar a autenticação
            var auth = request.getHeader("Authorization");

            var authEncoded = auth.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecoded);

            System.out.println(authString);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validar user

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);

                } else {
                    response.sendError(401);
                }

            }

            throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /*
     * @Override
     * public void doFilter(ServletRequest request, ServletResponse response,
     * FilterChain chain)
     * throws IOException, ServletException {
     * 
     * //Executar uma ação
     * System.out.println("Cheogu no filtro");
     * chain.doFilter(request, response);
     * }
     */

}
