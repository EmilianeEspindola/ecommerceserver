package br.edu.utfpr.pb.pw44s.ecommerceserver.security;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.AuthRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.User;
import br.edu.utfpr.pb.pw44s.ecommerceserver.security.dto.AuthenticationResponse;
import br.edu.utfpr.pb.pw44s.ecommerceserver.security.dto.UserResponseDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, @NonNull HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthRequestDTO credentials = new AuthRequestDTO();
            User user = new User();
            if ((request.getInputStream() != null || request.getInputStream().available() > 0)) {
                credentials = new ObjectMapper().readValue(request.getInputStream(), AuthRequestDTO.class);
                user = (User) authService.loadUserByUsername(credentials.getEmail());
            }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),
                    credentials.getPassword(), user.getAuthorities()));
        } catch (StreamReadException | DatabindException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(@NonNull HttpServletRequest request, HttpServletResponse response,
                                            @NonNull FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        User user = (User) authService.loadUserByUsername(authResult.getName());
        String token = JWT.create().withSubject(authResult.getName()).withExpiresAt(new Date(System.currentTimeMillis()
        + SecurityConstants.EXPIRATION_TIME)).sign(Algorithm.HMAC512(SecurityConstants.SECRET));
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(new AuthenticationResponse(token, new UserResponseDTO(user))));
    }
}