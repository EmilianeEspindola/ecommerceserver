package br.edu.utfpr.pb.pw44s.ecommerceserver;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.AuthRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.ecommerceserver.security.dto.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class AuthenticationTest {
    private static final String API_USER = "/users";
    private static final String API_LOGIN = "/login";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanup() { // Apaga usuários antes de cada teste
        userRepository.deleteAll();
    }

    @Test
    public void login_whenCredentialsAreValid_receiveToken() { // Testa o login do usuário
        createValidUser();

        // Dados Enviados:
        AuthRequestDTO request = AuthRequestDTO.builder()
                .email("teste@email.com").password("Teste123").build(); // Passa credenciais válidas

        // Dados Recebidos
        ResponseEntity<AuthResponseDTO> response = restTemplate.postForEntity(
                API_LOGIN, request, AuthResponseDTO.class); // POST Login Executado

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // Deve retornar 200 OK
        assertThat(response.getBody()).isNotNull(); // Deve retornar uma resposta
        assertThat(response.getBody().getToken()).isNotNull(); // Deve retornar um token
    }

    private void createValidUser() { // Cadastra um usuário válido para utilizar nos testes
        UserRequestDTO user = UserRequestDTO.builder()
                .name("Usuário Teste").email("teste@email.com").password("Teste123").build();
        restTemplate.postForEntity(API_USER, user, Object.class); // POST Executado
    }
}